package com.icu.service;

import com.icu.domain.user.Instructor;
import com.icu.domain.user.Student;
import com.icu.domain.user.User;
import com.icu.domain.user.UserRepository;
import com.icu.security.token.JwtFilter;
import com.icu.security.token.TokenProvider;
import com.icu.web.dto.LoginDto;
import com.icu.web.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenProvider tokenProvider,
                           AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @Transactional
    public Boolean insertUser(UserDto userDto) {
        Optional<User> findUser = userRepository.findById(userDto.getUsername());

        try {
            if (!findUser.isPresent()) {

                if (userDto.getRole().equals("instructor")) {
                    Instructor inst = new Instructor();
                    inst.setUsername((userDto.getUsername()));
                    inst.setPassword(passwordEncoder.encode(userDto.getPassword()));
                    inst.setFullname(userDto.getFullname());
                    inst.setPhoneNumber(userDto.getPhoneNumber());

                    userRepository.save(inst);
                    logger.info("{} ??????????????? ?????????", userDto.getUsername());
                    return true;
                } else if (userDto.getRole().equals("student")) {
                    Student stu = new Student();
                    stu.setUsername((userDto.getUsername()));
                    stu.setPassword(passwordEncoder.encode(userDto.getPassword()));
                    stu.setFullname(userDto.getFullname());
                    stu.setPhoneNumber(userDto.getPhoneNumber());

                    userRepository.save(stu);
                    logger.info("{} ??????????????? ?????????", userDto.getUsername());
                    return true;
                } else {
                    logger.info("{} ???????????? ???????????? ??????????????????", userDto.getUsername());
                    return false;
                }
            } else {
                logger.info("{} ???????????? ??????", userDto.getUsername());
                return false;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            logger.info("{} ???????????? ??????", userDto.getUsername());
            return false;
        }
    }

    @Transactional
    public Boolean updateUser(UserDto userDto) {
        Optional<User> findUser = userRepository.findById(userDto.getUsername());

        try {
            if (findUser.isPresent()) {

                User finduser = findUser.get();
                finduser.setUsername(finduser.getUsername());
                finduser.setPassword(passwordEncoder.encode(userDto.getPassword()));
                finduser.setFullname(userDto.getFullname());
                finduser.setPhoneNumber(userDto.getPhoneNumber());

                userRepository.save(finduser);
                logger.info("{} ?????? ??????", userDto.getUsername());
                return true;
            } else {
                logger.info("{} ?????? ??????", userDto.getUsername());
                return false;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            logger.error("{} ?????? ??????", userDto.getUsername());
            return false;
        }
    }

    public ResponseEntity<UserDto> login(LoginDto loginDto, HttpServletResponse httpServletResponse) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(), loginDto.getPassword());
        Authentication auth = authenticationManagerBuilder.getObject().authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt = tokenProvider.createToken(auth);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        Optional<User> userOpt = userRepository.findById(auth.getName());

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            UserDto userDto = UserDto.builder().username(user.getUsername()).fullname(user.getFullname())
                    .phoneNumber(user.getPhoneNumber()).role(user.getRole()).build();

            logger.info("????????? ??????");
            return new ResponseEntity<>(userDto, httpHeaders, HttpStatus.OK);
        } else {
            logger.info("????????? ??????");
            return null;
        }
    }

    // ?????? ??? ?????? ???????????? ????????? ???
    public Boolean logout(HttpServletRequest httpServletRequest) {
        try {
            logger.info("????????????");
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            logger.error("??????");
            return false;
        }
    }

    public UserDto getUser(String username) {
        Optional<User> findUser = userRepository.findById(username);

        if (findUser.isPresent()) {
            User user = findUser.get();
            UserDto userDto = UserDto.builder().username(user.getUsername()).fullname(user.getFullname())
                    .phoneNumber(user.getPhoneNumber()).role(user.getRole()).build();
            logger.info("{} ?????? ??????", username);
            return userDto;
        } else {
            logger.info("{} ?????? ??????", username);
            return null;
        }
    }

    public UserDto getStudent(String username) {
        Optional<User> findUser = userRepository.findById(username);

        if (findUser.isPresent()) {
            User user = findUser.get();
            UserDto userDto = UserDto.builder().username(user.getUsername()).fullname(user.getFullname())
                    .phoneNumber(user.getPhoneNumber()).role(user.getRole()).build();
            logger.info("{} ?????? ??????", username);
            return userDto;
        } else {
            logger.info("{} ?????? ??????", username);
            return null;
        }
    }

    public List<UserDto> getStudentList() {
        List<User> userList = userRepository.findAllUserByRole("ROLE_STUDENT");
        List<UserDto> uDtoList = userList.stream().map(u -> new UserDto(u.getUsername(), null, u.getFullname(),
                u.getPhoneNumber(), u.getRole())).collect(Collectors.toList());

        logger.info("?????? ?????? ?????? ??????");
        return uDtoList;
    }

    public List<UserDto> getStudentListNotInTest(long examNumber) {
        List<User> userList = userRepository.findAllUserNotInTest(examNumber, "ROLE_STUDENT");
        List<UserDto> uDtoList = userList.stream().map(u -> new UserDto(u.getUsername(), null, u.getFullname(),
                u.getPhoneNumber(), u.getRole())).collect(Collectors.toList());

        logger.info("?????? ?????? ?????? ??????");
        return uDtoList;
    }

    @Transactional
    public Boolean deleteUser(String username) {
        Optional<User> findUser = userRepository.findById(username);

        try {
            if (findUser.isPresent()) {

                userRepository.delete(findUser.get());
                logger.info("{} ?????? ??????", username);
                return true;
            } else {
                logger.info("{} ?????? ??????", username);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("{} ?????? ??????", username);
            return false;
        }
    }

}
