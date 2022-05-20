package com.icu.web.controller;

import com.icu.service.UserService;
import com.icu.web.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> insertUser(UserDto userDto, @RequestParam(value = "file", required = false) MultipartFile multipartFilefile) {
        Boolean check = null;
        if (multipartFilefile != null) {
            check = userService.insertUser(userDto, multipartFilefile);
        } else {
            check = userService.insertUserWithoutImage(userDto);
        }

        if (check) {
            return new ResponseEntity<String>("회원 가입 성공", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<String>("회원 가입 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
