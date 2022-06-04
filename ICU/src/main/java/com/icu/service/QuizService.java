package com.icu.service;

import com.icu.web.dto.QuizDto;

import java.util.List;

public interface QuizService {
    Boolean insertQuiz(long examNumber, QuizDto quizDto);

    List<QuizDto> getQuizByExamNumber(long examNumber);

    QuizDto getQuiz(long quizId);

    Boolean updateQuiz(QuizDto quizDto);

    Boolean deleteQuiz(long quizId, long examNumber);
}
