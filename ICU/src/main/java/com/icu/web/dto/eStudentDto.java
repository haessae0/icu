package com.icu.web.dto;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class eStudentDto {
    private String userId;

    private String name;

    private String examName;

    private String result;

    private String cheating;

    private List<String> cheatingTime;

    private String studentVideo;

    private List<String> examAnswer;

    private String examState;

    private long examNumber;
}
