package com.icu.web.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExamDto {
    @NotNull
    @Size(min = 8, max = 50)
    private long examNumber; // 과목 번호

    @NotNull
    @Size(min = 8, max = 50)
    private String examName; // 과목 이름

    @NotNull
    @Size(min = 2, max = 50)
    private LocalDateTime openTime; // 시험 열리는 시간

    @NotNull
    @Size(min = 2, max = 50)
    private LocalDateTime closeTime; // 시험 닫히는 시간

    @Size(min = 2, max = 255)
    private String examDescribe; // 시험 설명
}
