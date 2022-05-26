package com.icu.service;

import com.icu.web.dto.LoginDto;
import com.icu.web.dto.UserDto;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface UserService {

    Boolean insertUser(UserDto userDto);

    ResponseEntity<UserDto> login(LoginDto loginDto, HttpServletResponse httpServletResponse);

    Boolean logout(HttpServletRequest httpServletRequest);

    UserDto getUser(String username);

    UserDto getStudent(String username);

    List<UserDto> getStudentList();

    List<UserDto> getStudentListNotInTest(long examNumber);

    Boolean updateUser(UserDto userDto);

    Boolean deleteUser(String username);
}
