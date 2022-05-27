package com.icu.web.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuizDto {

    @NotNull
    @Size(max = 50)
    private long quizId; // 과목 시험 번호

    @NotNull
    @Size(max = 20)
    private int quizNum; // 1번 문제 2번 문제 등등

    @NotNull
    @Size(min = 5)
    private String quizDescribe; // 과목 설명

    @NotNull
    @Size(min = 2)
    private List<String> quizSelection; // 정답 -> 1/2/3/4 -> 1번의 보기 1 2번의 보기 2 3번의 보기 3 4번의 보기 4

    @Size(min = 1, max = 50)
    private String quizAnswer; // 문제 별 올바른 정답 1번의 답 1 2번의 답 2 3번의 답 3 4번의 답 3
}
