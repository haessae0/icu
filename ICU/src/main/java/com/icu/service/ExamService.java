package com.icu.service;

import com.icu.web.dto.ExamDto;

import java.util.List;

public interface ExamService {

    Long createExam(String username, ExamDto examDto);

    List<ExamDto> getExamByUsername(String username);

    ExamDto getExam(Long examNumber);

    Boolean updateExam(ExamDto examDto);

    Boolean deleteExam(String username, Long examNumber);

}
