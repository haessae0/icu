package com.icu.web.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    @NotNull
    @Size(min = 3, max = 50)
    private String userId;

    @NotNull
    @Size(min = 3, max = 100)
    private String password;
}
