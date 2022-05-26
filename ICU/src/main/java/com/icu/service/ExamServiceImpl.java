package com.icu.service;

import com.icu.domain.exam.Exam;
import com.icu.domain.exam.ExamRepository;
import com.icu.domain.user.Instructor;
import com.icu.domain.user.User;
import com.icu.domain.user.UserRepository;
import com.icu.web.dto.ExamDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExamServiceImpl implements ExamService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ExamRepository examRepository;
    private final UserRepository userRepository;

    public ExamServiceImpl(ExamRepository examRepository, UserRepository userRepository) {
        this.examRepository = examRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Long createExam(String username, ExamDto examDto) {
        Optional<User> userOptional = userRepository.findById(username);

        try {
            if (userOptional.isPresent()) {
                Instructor instructor = (Instructor) userOptional.get();
                Exam exam = new Exam();
                exam.setExamName(examDto.getExamName());
                exam.setOpenTime(examDto.getOpenTime());
                exam.setCloseTime(examDto.getCloseTime());
                exam.setExamDescribe(examDto.getExamDescribe());
                exam.setInstructorId(instructor);

                Exam madeExam = examRepository.save(exam);
                instructor.getExamList().add(madeExam);

                logger.info("시험 등록 완료", username);
                return madeExam.getExamNumber();
            } else {
                logger.info("시험 등록 실패", username);
                return null;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            logger.error("시험 등록 실패", username);
            return null;
        }
    }

    public List<ExamDto> getExamByUsername(String username) {
        List<Exam> list = examRepository.findAllExamByUsername(username);
        List<ExamDto> examGroup = list.stream().map(examination -> new ExamDto(examination.getExamNumber(), examination.getExamName(), examination.getOpenTime(), examination.getCloseTime(), examination.getExamDescribe())).collect(Collectors.toList());

        logger.info("시험 조회", username);
        return examGroup;
    }

    public ExamDto getExam(Long examNumber) {
        Optional<Exam> examOptional = examRepository.findById(examNumber);

        if (examOptional.isPresent()) {
            Exam exam = examOptional.get();
            ExamDto examDto = ExamDto.builder().examNumber(exam.getExamNumber()).examName(exam.getExamName()).openTime(exam.getOpenTime()).closeTime(exam.getCloseTime()).examDescribe(exam.getExamDescribe()).build();

            logger.info("조회 성공", examNumber);
            return examDto;
        } else {
            logger.info("조회 실패", examNumber);
            return null;
        }
    }

    @Transactional
    public Boolean updateExam(ExamDto examDto) {
        Optional<Exam> examOptional = examRepository.findById(examDto.getExamNumber());

        try {
            if (examOptional.isPresent()) {
                Exam exam = examOptional.get();
                exam.setOpenTime(examDto.getOpenTime());
                exam.setCloseTime(examDto.getCloseTime());
                exam.setExamDescribe(examDto.getExamDescribe());
                exam.setExamName(examDto.getExamName());

                examRepository.save(exam);
                logger.info("수정 완료", examDto.getExamNumber());
                return true;
            } else {
                logger.info("수정 실패", examDto.getExamNumber());
                return false;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            logger.info("수정 실패", examDto.getExamNumber());
            return false;
        }
    }

    @Transactional
    public Boolean deleteExam(String username, Long examNumber) {
        Optional<Exam> examOptional = examRepository.findById(examNumber);
        Optional<User> userOptional = userRepository.findById(username);

        try {
            if (examOptional.isPresent() && userOptional.isPresent()) {
                Instructor instructor = (Instructor) userOptional.get();
                List<Exam> examGroup = instructor.getExamList();

                for (int index = 0; index < examGroup.size(); index++) {
                    if (examGroup.get(index).getExamNumber() == examNumber) {
                        examGroup.remove(index);
                        break;
                    }
                }
                examRepository.delete(examOptional.get());
                logger.info("삭제 완료", examNumber);
                return true;
            } else {
                logger.info("삭제 실패", examNumber);
                return false;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            logger.info("삭제 실패", examNumber);
            return false;
        }
    }
}
