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
public class ExamInfoDto {
    @NotNull
    @Size(min = 8, max = 50)
    private long examNumber;

    @NotNull
    @Size(min = 8, max = 50)
    private String examName;

    @NotNull
    @Size(min = 2, max = 50)
    private LocalDateTime startTime;

    @NotNull
    @Size(min = 2, max = 50)
    private LocalDateTime endTime;

    @Size(min = 2, max = 255)
    private String examDescribe;
}