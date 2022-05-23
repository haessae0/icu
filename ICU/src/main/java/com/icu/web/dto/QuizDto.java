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
    private long quizId;

    @NotNull
    @Size(max = 20)
    private int quizNum;

    @NotNull
    @Size(min = 5)
    private String quizDescribe;

    @NotNull
    @Size(min = 2)
    private List<String> quizSelection;

    @NotNull
    @Size(min = 2, max = 100)
    private String quizImage;

    @Size(min = 1, max = 50)
    private String quizAnswer;
}
