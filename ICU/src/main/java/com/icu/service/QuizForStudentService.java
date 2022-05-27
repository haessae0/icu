package com.icu.service;

import com.icu.web.dto.QuizForStudentDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface QuizForStudentService {

    Boolean insertQuizForStudent(String username, long examNumber);

    QuizForStudentDto getQuizForStudent(String username, long examNumber);

    List<QuizForStudentDto> getQuizForStudentListByUsername(String username);

    List<QuizForStudentDto> getQuizForStudentListByExamNumber(long examNumber);

    Boolean updateQuizForStudent(QuizForStudentDto quizForStudentDto, MultipartFile multipartFile);

    Boolean updateQuizScore(String username, long examNumber, String quizResult);

    Boolean deleteQuizForStudentByUsername(String username, long examNumber);

}
