package com.icu.service;

import com.icu.domain.exam.Exam;
import com.icu.domain.exam.ExamRepository;
import com.icu.domain.quiz.Quiz;
import com.icu.domain.quiz.QuizRepository;
import com.icu.web.dto.QuizDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuizServiceImpl implements QuizService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final QuizRepository quizRepository;
    private final ExamRepository examRepository;

    public QuizServiceImpl(QuizRepository quizRepository, ExamRepository examRepository) {
        this.quizRepository = quizRepository;
        this.examRepository = examRepository;
    }

    @Transactional
    public Boolean insertQuiz(long examNumber, QuizDto quizDto) {
        Optional<Exam> examOptional = examRepository.findById(examNumber);

        try {
            if (examOptional.isPresent()) {
                String quizSelection = String.join("/", quizDto.getQuizSelection());
                Quiz madeQuiz = new Quiz();
                madeQuiz.setQuizNum(quizDto.getQuizNum());
                madeQuiz.setQuizDescribe(quizDto.getQuizDescribe());
                madeQuiz.setQuizSelection(quizSelection);
                madeQuiz.setQuizAnswer(quizDto.getQuizAnswer());
                madeQuiz.setExamNumber(examOptional.get());

                Quiz quiz = quizRepository.save(madeQuiz);
                examOptional.get().getProblemList().add(quiz);

                logger.info("등록 완료", examNumber);
                return true;
            } else {
                logger.info("등록 실패", examNumber);
                return false;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            logger.info("등록 실패", examNumber);
            return false;
        }
    }

    public List<QuizDto> getQuizByExamNumber(long examNumber) {
        List<Quiz> quizList = quizRepository.findAllQuizByExamNumber(examNumber);
        List<QuizDto> quizDto = quizList.stream().map(examination -> new QuizDto(examination.getQuizId(), examination.getQuizNum(), examination.getQuizDescribe(), Arrays.asList(examination.getQuizSelection().split("/")), examination.getQuizAnswer())).collect(Collectors.toList());

        logger.info("조회 완료", examNumber);
        return quizDto;
    }

    public QuizDto getQuiz(long quizId) {
        Optional<Quiz> quizOptional = quizRepository.findById(quizId);

        if (quizOptional.isPresent()) {
            Quiz quiz = quizOptional.get();
            List<String> quizSelectionList = Arrays.asList(quiz.getQuizSelection().split("/"));
            QuizDto quizDto = QuizDto.builder().quizId(quiz.getQuizId()).quizNum(quiz.getQuizNum()).quizDescribe(quiz.getQuizDescribe()).quizSelection(quizSelectionList).quizAnswer(quiz.getQuizAnswer()).build();

            logger.info("조회 완료", quizId);
            return quizDto;
        } else {
            logger.info("조회 실패", quizId);
            return null;
        }
    }

    @Transactional
    public Boolean updateQuiz(QuizDto quizDto) {
        Optional<Quiz> findQuiz = quizRepository.findById(quizDto.getQuizId());

        try {
            if (findQuiz.isPresent()) {
                Quiz quiz = findQuiz.get();
                String quizSelection = String.join("/", quizDto.getQuizSelection());

                quiz.setQuizNum(quizDto.getQuizNum());
                quiz.setQuizDescribe(quizDto.getQuizDescribe());
                quiz.setQuizSelection(quizSelection);
                quiz.setQuizAnswer(quizDto.getQuizAnswer());

                quizRepository.save(quiz);
                return true;
            } else {
                logger.info("존재 하지 않음", quizDto.getQuizId());
                return false;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            logger.error("수정 실패", quizDto.getQuizId());
            return false;
        }
    }

    @Transactional
    public Boolean deleteQuiz(long quizId, long examNumber) {
        Optional<Quiz> quizOptional = quizRepository.findById(quizId);
        Optional<Exam> examOptional = examRepository.findById(examNumber);

        try {
            if (quizOptional.isPresent() && examOptional.isPresent()) {
                List<Quiz> quizList = examOptional.get().getProblemList();

                for (int index = 0; index < quizList.size(); index++) {
                    if (quizList.get(index).getQuizId() == quizId) {
                        quizList.remove(index);
                        break;
                    }
                }
                quizRepository.delete(quizOptional.get());
                logger.info("삭제 완료", quizId);
                return true;
            } else {
                logger.info("삭제 실패", quizId);
                return false;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            logger.info("삭제 실패", quizId);
            return false;
        }
    }
}
