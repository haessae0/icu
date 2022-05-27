package com.icu.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    @NotNull
    @Size(min = 8, max = 50)
    private String username; // 학번

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(min = 8, max = 50)
    private String password; // 비밀번호

    @NotNull
    @Size(min = 2, max = 50)
    private String fullname; // 학생 이름

    @NotNull
    @Size(min = 2, max = 50)
    private String phoneNumber; // 전화번호

    @NotNull
    @Size(min = 2, max = 50)
    private String role; // 학생 또는 교수

}
