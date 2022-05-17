package com.icu.service;

import com.icu.web.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    Boolean insertUser(UserDto userDto, MultipartFile multipartFile);

    Boolean insertUserWithoutImage(UserDto userDto);
}
