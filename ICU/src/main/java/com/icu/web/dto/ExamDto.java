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
public class ExamDto {

    @NotNull
    @Size(max = 50)
    private long examId;

    @NotNull
    @Size(max = 20)
    private int examNum;

    @NotNull
    @Size(min = 5)
    private String examQuestion;

    @NotNull
    @Size(min = 2)
    private List<String> examSelection;

    @NotNull
    @Size(min = 2, max = 100)
    private String examDesImage;

    @Size(min = 1, max = 50)
    private String examAnswer;
}
