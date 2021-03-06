package com.icu.service;

import com.icu.domain.exam.Exam;
import com.icu.domain.exam.ExamRepository;
import com.icu.domain.quizforstudent.QuizForStudent;
import com.icu.domain.quizforstudent.QuizForStudentRepository;
import com.icu.domain.user.Student;
import com.icu.domain.user.StudentRepository;
import com.icu.web.dto.QuizForStudentDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class QuizForStudentServiceImpl implements QuizForStudentService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ExamRepository examRepository;
    private final StudentRepository studentRepository;
    private final QuizForStudentRepository quizForStudentRepository;

    public QuizForStudentServiceImpl(ExamRepository examRepository, StudentRepository studentRepository,
            QuizForStudentRepository quizForStudentRepository) {
        this.examRepository = examRepository;
        this.studentRepository = studentRepository;
        this.quizForStudentRepository = quizForStudentRepository;
    }

    @Transactional
    public Boolean insertQuizForStudent(String username, long examNumber) {
        Optional<Student> studentOptional;
        Optional<Exam> examOptional;

        try {

            studentOptional = studentRepository.findById(username);
            examOptional = examRepository.findById(examNumber);

            if (studentOptional.isPresent() && examOptional.isPresent()) {
                QuizForStudent quizForStudent = new QuizForStudent();
                Student student = studentOptional.get();
                Exam exam = examOptional.get();

                quizForStudent.setStudentId(student);
                quizForStudent.setExamNumber(exam);
                quizForStudent.setStatus("True");

                if (quizForStudentRepository.save(quizForStudent) == null) {
                    logger.info("?????? ??????");
                    return false;
                } else {
                    logger.info("?????? ??????", quizForStudent.getExamNumber());
                    return true;
                }
            } else {
                logger.info("?????? ??????");
                return false;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            logger.info("?????? ??????");
            return false;
        }
    }

    public QuizForStudentDto getQuizForStudent(String username, long examNumber) {
        Optional<QuizForStudent> quizForStudentOptional = quizForStudentRepository
                .findQuizForStudentByUserNameAndExamNumber(username, examNumber);

        try {
            if (quizForStudentOptional.isPresent()) {
                QuizForStudent quizForStudent = quizForStudentOptional.get();

                List<String> cheatingTime = Arrays.asList(quizForStudent.getCheatingTime().split("/"));
                List<String> studentAnswer = Arrays.asList(quizForStudent.getStudentAnswer().split("/"));

                QuizForStudentDto quizForStudentDto = QuizForStudentDto.builder()
                        .fullname(studentRepository.findById(username).get().getFullname()).username(username)
                        .examName(examRepository.findById(examNumber).get().getExamName()).examNumber(examNumber)
                        .lier(quizForStudent.getLier()).cheatingTime(cheatingTime)
                        .videoName(quizForStudent.getVideoName()).studentAnswer(studentAnswer)
                        .status(quizForStudent.getStatus()).quizResult(quizForStudent.getQuizResult()).build();

                logger.info("?????? ?????? ??????", username, examNumber);
                return quizForStudentDto;
            } else {
                logger.info("?????? ?????? ??????", username, examNumber);
                return null;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            logger.info("?????? ?????? ??????", username, examNumber);
            return null;
        }
    }

    public List<QuizForStudentDto> getQuizForStudentListByUsername(String username) {
        Optional<List<QuizForStudent>> quizForStudentList = quizForStudentRepository
                .findAllQuizForStudentByUserName(username);

        Stream<QuizForStudent> before = quizForStudentList.get().stream()
                .filter(student -> student.getStudentAnswer() == null);
        Stream<QuizForStudent> after = quizForStudentList.get().stream()
                .filter(student -> student.getStudentAnswer() != null);

        List<QuizForStudentDto> dtoBefore = before
                .map(student -> new QuizForStudentDto(username, null,
                        examRepository.findById(student.getExamNumber().getExamNumber()).get().getExamName(), null,
                        null, null, null, null, student.getStatus(), student.getExamNumber().getExamNumber()))
                .collect(Collectors.toList());
        List<QuizForStudentDto> dtoAfter = after.map(student -> new QuizForStudentDto(username, null,
                examRepository.findById(student.getExamNumber().getExamNumber()).get().getExamName(),
                student.getQuizResult(), student.getLier(), Arrays.asList(student.getCheatingTime().split("/")),
                student.getVideoName(), Arrays.asList(student.getStudentAnswer().split("/")), student.getStatus(),
                student.getExamNumber().getExamNumber())).collect(Collectors.toList());

        List<QuizForStudentDto> joinList = new ArrayList<QuizForStudentDto>();
        joinList.addAll(dtoBefore);
        joinList.addAll(dtoAfter);

        logger.info("?????? ?????? ??????", username);
        return joinList;
    }

    public List<QuizForStudentDto> getQuizForStudentListByExamNumber(long examNumber) {
        Optional<List<QuizForStudent>> quizForStudentList = quizForStudentRepository
                .findAllQuizForStudentByExamNumber(examNumber);

        List<QuizForStudentDto> dtoList = quizForStudentList.get().stream()
                .map(student -> new QuizForStudentDto(student.getStudentId().getUsername(),
                        studentRepository.findById(student.getStudentId().getUsername()).get().getFullname(), null,
                        student.getQuizResult(), student.getLier(), null, null, null, student.getStatus(),
                        student.getExamNumber().getExamNumber()))
                .collect(Collectors.toList());
        logger.info("?????? ??????", examNumber);
        return dtoList;
    }

    @Transactional
    public Boolean updateQuizForStudent(QuizForStudentDto quizForStudentDto, MultipartFile multipartFile) {
        Optional<QuizForStudent> quizForStudentOptional = quizForStudentRepository
                .findQuizForStudentByUserNameAndExamNumber(quizForStudentDto.getUsername(),
                        quizForStudentDto.getExamNumber());

        if (quizForStudentOptional.isPresent()) {
            QuizForStudent quizForStudent = quizForStudentOptional.get();
            String videoname;

            try {
                videoname = quizForStudentDto.getExamNumber() + "_" + quizForStudentDto.getUsername() + "_"
                        + "video.mp4";
                multipartFile.transferTo(new File(System.getProperty("user.dir")
                        + "\\ICU\\src\\main\\webapp\\userVideo\\" + videoname));
                logger.info("{}??? ?????? ???????????? ?????? ??????", quizForStudentDto.getExamNumber());
                quizForStudent.setVideoName(videoname);
            } catch (IllegalStateException | IOException exception) {
                exception.printStackTrace();
                logger.error("{}??? ?????? ???????????? ?????? ??????", quizForStudentDto.getExamNumber());
                return false;
            }

            quizForStudent.setStatus(quizForStudent.getStatus());

            String answer = "";
            for (String c : quizForStudentDto.getStudentAnswer()) {
                answer += c + "/";
            }
            quizForStudent.setStudentAnswer(answer);

            if (quizForStudentRepository.save(quizForStudent) != null) {
                logger.info("{} ?????? ?????? ??????, ???????????? ?????? ??????", quizForStudentDto.getUsername());
                return true;
            } else {
                logger.info("{} ?????? ?????? ??????, ???????????? ?????? ??????", quizForStudentDto.getUsername());
                return false;
            }
        } else {
            logger.info("{} ?????? ?????? ??????, ???????????? ?????? ??????", quizForStudentDto.getUsername());
            return false;
        }
    }

    @Transactional
    public Boolean updateQuizScore(String username, long examNumber, String quizResult) {
        Optional<QuizForStudent> quizForStudentOptional = quizForStudentRepository
                .findQuizForStudentByUserNameAndExamNumber(username, examNumber);

        if (quizForStudentOptional.isPresent()) {
            try {
                QuizForStudent quizForStudent = quizForStudentOptional.get();
                quizForStudent.setQuizResult(quizResult);

                if (quizForStudentRepository.save(quizForStudent) != null) {
                    logger.info("{} ?????? {} ???????????? update??????", username, examNumber);
                    return true;
                } else {
                    logger.info("{} ?????? {} ???????????? update??????", username, examNumber);
                    return false;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                logger.error("{} ?????? {} ???????????? update??????", username, examNumber);
                return false;
            }
        } else {
            logger.info("{} ?????? {} ???????????? update??????", username, examNumber);
            return false;
        }
    }

    @Transactional
    public Boolean deleteQuizForStudentByUsername(String username, long examNumber) {
        Optional<QuizForStudent> quizForStudentOptional = quizForStudentRepository
                .findQuizForStudentByUserNameAndExamNumber(username, examNumber);

        try {
            if (quizForStudentOptional.isPresent()) {
                quizForStudentRepository.delete(quizForStudentOptional.get());
                logger.info("?????? ??????", username, examNumber);
                return true;
            } else {
                logger.info("?????? ??????", username, examNumber);
                return false;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            logger.error("?????? ??????", username, examNumber);
            return false;
        }
    }

}
