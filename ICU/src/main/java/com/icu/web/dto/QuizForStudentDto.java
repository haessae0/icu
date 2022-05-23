package com.icu.web.dto;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuizForStudentDto {
    private String username;

    private String fullname;

    private String examName;

    private String quizResult;

    private String lier;

    private List<String> cheatingTime;

    private String videoName;

    private List<String> studentAnswer;

    private String status;

    private long examNumber;
}
