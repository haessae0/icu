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
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(min = 8, max = 50)
    private String password;

    @NotNull
    @Size(min = 2, max = 50)
    private String fullname;

    @NotNull
    @Size(min = 2, max = 50)
    private String phoneNumber;

    @NotNull
    @Size(min = 2, max = 50)
    private String userImage;

    @NotNull
    @Size(min = 2, max = 50)
    private String role;

}
