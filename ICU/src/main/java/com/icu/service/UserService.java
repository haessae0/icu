package com.icu.service;

import com.icu.web.dto.LoginDto;
import com.icu.web.dto.UserDto;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface UserService {

    public Boolean insertUser(UserDto userDto, MultipartFile multipartFile);

    public Boolean insertUserWithoutImage(UserDto userDto);

    public ResponseEntity<UserDto> login(LoginDto loginDto, HttpServletResponse httpServletResponse);

    public Boolean logout(HttpServletRequest httpServletRequest);

    public UserDto getUser(String username);

    public UserDto getStudent(String username);

    public List<UserDto> getStudentList();

    public List<UserDto> getStudentListNotInTest(long examNumber);

    public Boolean updateUser(UserDto userDto, MultipartFile multipartFile);

    public Boolean updateUserWithoutImage(UserDto userDto);

    public Boolean deleteUser(String username);
}
