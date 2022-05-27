package com.icu.web.dto;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuizForStudentDto {
    private String username; // 학번

    private String fullname; // 이름

    private String examName; // 과목 이름

    private String quizResult; // 퀴즈 결과

    private String lier; // 일정 시간 지속으로 부정이라 칭함

    private List<String> cheatingTime; // 특이 사항 반응 시간

    private String videoName; // 학생 영상

    private List<String> studentAnswer; // 학생이 고른 답

    private String status; // 응시 여부

    private long examNumber; // 시험 아이디
}
