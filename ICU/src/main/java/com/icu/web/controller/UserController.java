package com.icu.web.controller;

import com.icu.service.UserService;
import com.icu.web.dto.LoginDto;
import com.icu.web.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> insertUser(UserDto userDto) {
        Boolean check = userService.insertUser(userDto);

        if (check) {
            return new ResponseEntity<String>("회원 가입 성공", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<String>("회원 가입 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<UserDto> login(@RequestBody LoginDto loginDto, HttpServletResponse httpServletResponse) {
        return userService.login(loginDto, httpServletResponse);
    }

    @PostMapping("/logout")
    @PreAuthorize("hasAnyRole('STUDENT','INSTRUCTOR')")
    public ResponseEntity<String> logout(HttpServletRequest httpServletRequest) {

        if (userService.logout(httpServletRequest)) {
            return new ResponseEntity<String>("회원 로그아웃 성공", HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<String>("회원 로그아웃 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/myinfo")
    @PreAuthorize("hasAnyRole('STUDENT','INSTRUCTOR')")
    public ResponseEntity<UserDto> getUser(@RequestParam String username) {
        return new ResponseEntity<UserDto>(userService.getUser(username), HttpStatus.OK);
    }

    @GetMapping("/stuinfo")
    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    public ResponseEntity<UserDto> getStudent(@RequestParam String username) {
        return new ResponseEntity<UserDto>(userService.getStudent(username), HttpStatus.OK);
    }

    @GetMapping("/allusers")
    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    public ResponseEntity<List<UserDto>> getStudentList() {
        List<UserDto> userDtoList = userService.getStudentList();
        return new ResponseEntity<List<UserDto>>(userDtoList, HttpStatus.OK);
    }

    @GetMapping("/notest/{examNumber}")
    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    public ResponseEntity<List<UserDto>> getStudentListNotInTest(@PathVariable long examNumber) {
        List<UserDto> userDtoList = userService.getStudentListNotInTest(examNumber);
        return new ResponseEntity<List<UserDto>>(userDtoList, HttpStatus.OK);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('STUDENT','INSTRUCTOR')")
    public ResponseEntity<String> updateUser(UserDto userDto) {
        Boolean check = userService.updateUser(userDto);

        if (check) {
            return new ResponseEntity<String>("정보 수정 성공", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("정보 수정 실패", HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyRole('STUDENT','INSTRUCTOR')")
    public ResponseEntity<String> deleteUser(@RequestParam String username) {

        if (userService.deleteUser(username)) {
            return new ResponseEntity<String>("탈퇴 성공", HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<String>("탈퇴 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
