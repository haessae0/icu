package com.icu.service;

import com.icu.domain.user.Instructor;
import com.icu.domain.user.Student;
import com.icu.domain.user.User;
import com.icu.domain.user.UserRepository;
import com.icu.util.token.TokenProvider;
import com.icu.web.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @Transactional
    public Boolean insertUser(UserDto userDto, MultipartFile multipartFile) {
        Optional<User> findUser = userRepository.findById(userDto.getUserId());

        try {
            if (!findUser.isPresent()) {
                String userimg = null;

                try {
                    userimg = System.currentTimeMillis() + multipartFile.getOriginalFilename();
                    multipartFile.transferTo(new File(System.getProperty("user.dir") + "\\src\\main\\webapp\\userimg\\" + userimg));
                    logger.info("{} 이미지 등록 성공", userDto.getUserId());
                } catch (IllegalStateException | IOException exception) {
                    exception.printStackTrace();
                    logger.error("{} 이미지 등록 실패", userDto.getUserId());
                }

                if (userDto.getRole().equals("instructor")) {
                    Instructor inst = new Instructor();
                    inst.setUserId((userDto.getUserId()));
                    inst.setPassword(passwordEncoder.encode(userDto.getPassword()));
                    inst.setName(userDto.getName());
                    inst.setPhoneNumber(userDto.getPhoneNumber());
                    inst.setProfileImage(userDto.getProfileImage());

                    userRepository.save(inst);
                    logger.info("{} 환영합니다 강사님", userDto.getUserId());
                    return true;
                } else if (userDto.getRole().equals("student")) {
                    Student stu = new Student();
                    stu.setUserId((userDto.getUserId()));
                    stu.setPassword(passwordEncoder.encode(userDto.getPassword()));
                    stu.setName(userDto.getName());
                    stu.setPhoneNumber(userDto.getPhoneNumber());
                    stu.setProfileImage(userDto.getProfileImage());

                    userRepository.save(stu);
                    logger.info("{} 환영합니다 학생님", userDto.getUserId());
                    return true;
                } else {
                    logger.info("{} 강사인지 학생인지 선택해주세요", userDto.getUserId());
                    return false;
                }
            } else {
                logger.info("{} 회원가입 실패", userDto.getUserId());
                return false;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            logger.info("{} 회원가입 실패", userDto.getUserId());
            return false;
        }
    }

    @Transactional
    public Boolean insertUserWithoutImage(UserDto userDto) {
        Optional<User> findUser = userRepository.findById(userDto.getUserId());

        try {
            if (!findUser.isPresent()) {
                if (userDto.getRole().equals("instructor")) {
                    Instructor inst = new Instructor();
                    inst.setUserId((userDto.getUserId()));
                    inst.setPassword(passwordEncoder.encode(userDto.getPassword()));
                    inst.setName(userDto.getName());
                    inst.setPhoneNumber(userDto.getPhoneNumber());
                    inst.setProfileImage("img.png");

                    userRepository.save(inst);
                    logger.info("{} 환영합니다 강사님", userDto.getUserId());
                    return true;
                } else if (userDto.getRole().equals("student")) {
                    Student stu = new Student();
                    stu.setUserId((userDto.getUserId()));
                    stu.setPassword(passwordEncoder.encode(userDto.getPassword()));
                    stu.setName(userDto.getName());
                    stu.setPhoneNumber(userDto.getPhoneNumber());
                    stu.setProfileImage("img.png");

                    userRepository.save(stu);
                    logger.info("{} 환영합니다 학생님", userDto.getUserId());
                    return true;
                } else {
                    logger.info("{} 강사인지 학생인지 선택해주세요", userDto.getUserId());
                    return false;
                }
            } else {
                logger.info("{} 회원가입 실패", userDto.getUserId());
                return false;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            logger.info("{} 회원가입 실패", userDto.getUserId());
            return false;
        }
    }
}
