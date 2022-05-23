package com.icu.web.controller;

import com.icu.service.UserService;
import com.icu.web.dto.LoginDto;
import com.icu.web.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<String> insertUser(UserDto userDto,
            @RequestParam(value = "file", required = false) MultipartFile multipartFile) {
        Boolean check = null;
        if (multipartFile != null) {
            check = userService.insertUser(userDto, multipartFile);
        } else {
            check = userService.insertUserWithoutImage(userDto);
        }

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
    @PreAuthorize("hasAnyRole('rSTUDENT','rINSTRUCTOR')")
    public ResponseEntity<String> logout(HttpServletRequest httpServletRequest) {

        if (userService.logout(httpServletRequest)) {
            return new ResponseEntity<String>("회원 로그아웃 성공", HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<String>("회원 로그아웃 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/myinfo") // 프론트에서 로그인한 유저의 username 보내 조회
    @PreAuthorize("hasAnyRole('rSTUDENT','rINSTRUCTOR')")
    public ResponseEntity<UserDto> getUser(@RequestParam String userId) {
        return new ResponseEntity<UserDto>(userService.getUser(userId), HttpStatus.OK);
    }

    @GetMapping("/stuinfo") // 강사가 학생들 조회 @PathVariable 방식
    @PreAuthorize("hasAnyRole('rINSTRUCTOR')")
    public ResponseEntity<UserDto> getStudent(@RequestParam String userId) {
        return new ResponseEntity<UserDto>(userService.getStudent(userId), HttpStatus.OK);
    }

    @GetMapping("/allusers") // 강사가 전체 학생 리스트 조회 가능
    @PreAuthorize("hasAnyRole('rINSTRUCTOR')")
    public ResponseEntity<List<UserDto>> getStudentList() {
        List<UserDto> userDtoList = userService.getStudentList();
        return new ResponseEntity<List<UserDto>>(userDtoList, HttpStatus.OK);
    }

    @GetMapping("/notest/{testnum}")
    @PreAuthorize("hasAnyRole('rINSTRUCTOR')")
    public ResponseEntity<List<UserDto>> getStudentListNotInTest(@PathVariable long examNumber) {
        List<UserDto> userDtoList = userService.getStudentListNotInTest(examNumber);
        return new ResponseEntity<List<UserDto>>(userDtoList, HttpStatus.OK);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('SrTUDENT','rINSTRUCTOR')")
    public ResponseEntity<String> updateUser(UserDto userDto,
            @RequestParam(value = "file", required = false) MultipartFile multipartFile) {
        Boolean check = null;
        if (multipartFile != null) {
            check = userService.updateUser(userDto, multipartFile);
        } else {
            check = userService.updateUserWithoutImage(userDto);
        }

        if (check) {
            return new ResponseEntity<String>("정보 수정 성공", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("정보 수정 실패", HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyRole('rSTUDENT','rINSTRUCTOR')")
    public ResponseEntity<String> deleteUser(@RequestParam String userId) {

        if (userService.deleteUser(userId)) {
            return new ResponseEntity<String>("탈퇴 성공", HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<String>("탈퇴 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
