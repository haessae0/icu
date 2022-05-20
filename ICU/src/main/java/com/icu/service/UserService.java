package com.icu.service;

import com.icu.web.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    Boolean insertUser(UserDto userDto, MultipartFile multipartFile);

    Boolean insertUserWithoutImage(UserDto userDto);
//
//    ResponseEntity<UserDto> login(LoginDto loginDto, HttpServletResponse httpServletResponse);
//
//    Boolean logout(HttpServletResponse httpServletResponse);
//
//    UserDto getUser(String userId);
//
//    UserDto getStudent(String userId);
//
//    List<UserDto> getStudentList();
//
//    List<UserDto> getStudentListNotInTest(long examNumber);
//
//    Boolean updateUser(UserDto userDto, MultipartFile multipartFile);
//
//    Boolean updateUserWithoutimg(UserDto userDto);
//
//    Boolean deleteUser(String userId);
}
