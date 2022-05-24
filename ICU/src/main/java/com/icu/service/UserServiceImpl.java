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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
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
    public Boolean insertUser(UserDto userDto, MultipartFile multipartFile) {
        Optional<User> findUser = userRepository.findById(userDto.getUsername());

        try {
            if (!findUser.isPresent()) {
                String userimg = null;

                try {
                    userimg = System.currentTimeMillis() + multipartFile.getOriginalFilename();
                    multipartFile.transferTo(
                            new File(System.getProperty("user.dir") + "\\src\\main\\webapp\\userimg\\" + userimg));
                    logger.info("{} 이미지 등록 성공", userDto.getUsername());
                } catch (IllegalStateException | IOException exception) {
                    exception.printStackTrace();
                    logger.error("{} 이미지 등록 실패", userDto.getUsername());
                }

                if (userDto.getRole().equals("instructor")) {
                    Instructor inst = new Instructor();
                    inst.setUsername((userDto.getUsername()));
                    inst.setPassword(passwordEncoder.encode(userDto.getPassword()));
                    inst.setFullname(userDto.getFullname());
                    inst.setPhoneNumber(userDto.getPhoneNumber());
                    inst.setUserImage(userDto.getUserImage());

                    userRepository.save(inst);
                    logger.info("{} 환영합니다 강사님", userDto.getUsername());
                    return true;
                } else if (userDto.getRole().equals("student")) {
                    Student stu = new Student();
                    stu.setUsername((userDto.getUsername()));
                    stu.setPassword(passwordEncoder.encode(userDto.getPassword()));
                    stu.setFullname(userDto.getFullname());
                    stu.setPhoneNumber(userDto.getPhoneNumber());
                    stu.setUserImage(userDto.getUserImage());

                    userRepository.save(stu);
                    logger.info("{} 환영합니다 학생님", userDto.getUsername());
                    return true;
                } else {
                    logger.info("{} 강사인지 학생인지 선택해주세요", userDto.getUsername());
                    return false;
                }
            } else {
                logger.info("{} 회원가입 실패", userDto.getUsername());
                return false;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            logger.info("{} 회원가입 실패", userDto.getUsername());
            return false;
        }
    }

    @Transactional
    public Boolean updateUser(UserDto userDto, MultipartFile multipartFile) {
        Optional<User> findUser = userRepository.findById(userDto.getUsername());

        try {
            if (findUser.isPresent()) {
                String userimg = null;

                try {
                    userimg = System.currentTimeMillis() + multipartFile.getOriginalFilename();
                    multipartFile.transferTo(
                            new File(System.getProperty("user.dir") + "\\src\\main\\webapp\\userimg\\" + userimg));

                    String filename = findUser.get().getUserImage();
                    File file = new File(System.getProperty("user.dir") + "\\src\\main\\webapp\\userimg\\" + filename);

                    if (file.exists() && !filename.equals("img.png")) {
                        if (file.delete()) {
                            logger.info("{} 이미지 삭제 완료", userDto.getUsername());
                        } else {
                            logger.info("{} 이미지 삭제 실패", userDto.getUsername());
                        }
                    }
                } catch (IllegalStateException | IOException exception) {
                    exception.printStackTrace();
                    logger.error("{} 회원 이미지 갱신 오류 발생", userDto.getUsername());
                    return false;
                }
                User finduser = findUser.get();
                finduser.setUsername(finduser.getUsername());
                finduser.setPassword(passwordEncoder.encode(userDto.getPassword()));
                finduser.setFullname(userDto.getFullname());
                finduser.setPhoneNumber(userDto.getPhoneNumber());
                finduser.setUserImage(userimg);

                userRepository.save(finduser);
                logger.info("{} 수정 성공", userDto.getUsername());
                return true;
            } else {
                logger.info("{} 수정 실패", userDto.getUsername());
                return false;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            logger.error("{} 수정 실패", userDto.getUsername());
            return false;
        }
    }

    @Transactional
    public Boolean insertUserWithoutImage(UserDto userDto) {
        Optional<User> findUser = userRepository.findById(userDto.getUsername());

        try {
            if (!findUser.isPresent()) {
                if (userDto.getRole().equals("instructor")) {
                    Instructor inst = new Instructor();
                    inst.setUsername((userDto.getUsername()));
                    inst.setPassword(passwordEncoder.encode(userDto.getPassword()));
                    inst.setFullname(userDto.getFullname());
                    inst.setPhoneNumber(userDto.getPhoneNumber());
                    inst.setUserImage("img.png");

                    userRepository.save(inst);
                    logger.info("{} 환영합니다 강사님", userDto.getUsername());
                    return true;
                } else if (userDto.getRole().equals("student")) {
                    Student stu = new Student();
                    stu.setUsername((userDto.getUsername()));
                    stu.setPassword(passwordEncoder.encode(userDto.getPassword()));
                    stu.setFullname(userDto.getFullname());
                    stu.setPhoneNumber(userDto.getPhoneNumber());
                    stu.setUserImage("img.png");

                    userRepository.save(stu);
                    logger.info("{} 환영합니다 학생님", userDto.getUsername());
                    return true;
                } else {
                    logger.info("{} 강사인지 학생인지 선택해주세요", userDto.getUsername());
                    return false;
                }
            } else {
                logger.info("{} 회원가입 실패", userDto.getUsername());
                return false;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            logger.info("{} 회원가입 실패", userDto.getUsername());
            return false;
        }
    }

    @Transactional
    public Boolean updateUserWithoutImage(UserDto userDto) {
        Optional<User> findUser = userRepository.findById(userDto.getUsername());

        try {
            if (findUser.isPresent()) {
                User finduser = findUser.get();
                finduser.setUsername(finduser.getUsername());
                finduser.setPassword(passwordEncoder.encode(userDto.getPassword()));
                finduser.setFullname(userDto.getFullname());
                finduser.setPhoneNumber(userDto.getPhoneNumber());

                userRepository.save(finduser);
                logger.info("{} 수정 성공", userDto.getUsername());
                return true;
            } else {
                logger.info("{} 수정 실패", userDto.getUsername());
                return false;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            logger.error("{} 수정 실패", userDto.getUsername());
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
                    .phoneNumber(user.getPhoneNumber()).userImage(user.getUserImage()).role(user.getRole()).build();

            logger.info("로그인 성공");
            return new ResponseEntity<>(userDto, httpHeaders, HttpStatus.OK);
        } else {
            logger.info("로그인 실패");
            return null;
        }
    }

    public Boolean logout(HttpServletRequest httpServletRequest) {
        try {
            logger.info("로그아웃");
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            logger.error("실패");
            return false;
        }
    }

    public UserDto getUser(String username) {
        Optional<User> findUser = userRepository.findById(username);

        if (findUser.isPresent()) {
            User user = findUser.get();
            UserDto userDto = UserDto.builder().username(user.getUsername()).fullname(user.getFullname())
                    .phoneNumber(user.getPhoneNumber()).userImage(user.getUserImage()).role(user.getRole()).build();
            logger.info("{} 조회 성공", username);
            return userDto;
        } else {
            logger.info("{} 조회 실패", username);
            return null;
        }
    }

    public UserDto getStudent(String username) {
        Optional<User> findUser = userRepository.findById(username);

        if (findUser.isPresent()) {
            User user = findUser.get();
            UserDto userDto = UserDto.builder().username(user.getUsername()).fullname(user.getFullname())
                    .phoneNumber(user.getPhoneNumber()).userImage(user.getUserImage()).role(user.getRole()).build();
            logger.info("{} 조회 성공", username);
            return userDto;
        } else {
            logger.info("{} 조회 실패", username);
            return null;
        }
    }

    public List<UserDto> getStudentList() {
        List<User> userList = userRepository.findAllUserByRole("rSTUDENT");
        List<UserDto> uDtoList = userList.stream().map(u -> new UserDto(u.getUsername(), null, u.getFullname(),
                u.getPhoneNumber(), u.getUserImage(), u.getRole())).collect(Collectors.toList());

        logger.info("전체 학생 회원 조회");
        return uDtoList;
    }

    public List<UserDto> getStudentListNotInTest(long examNumber) {
        List<User> userList = userRepository.findAllUserNotInTest(examNumber, "rSTUDENT");
        List<UserDto> uDtoList = userList.stream().map(u -> new UserDto(u.getUsername(), null, u.getFullname(),
                u.getPhoneNumber(), u.getUserImage(), u.getRole())).collect(Collectors.toList());

        logger.info("전체 학생 회원 조회");
        return uDtoList;
    }

    @Transactional
    public Boolean deleteUser(String username) {
        Optional<User> findUser = userRepository.findById(username);

        try {
            if (findUser.isPresent()) {
                String filename = findUser.get().getUserImage();
                File file = new File(System.getProperty("user.dir") + "\\src\\main\\webapp\\userimg\\" + filename);

                if (file.exists() && !filename.equals("img.png")) {
                    if (file.delete()) {
                        logger.info("{} 이미지 삭제 완료", findUser.get().getUsername());
                    } else {
                        logger.info("{} 이미지 삭제 실패", findUser.get().getUsername());
                    }
                }
                userRepository.delete(findUser.get());
                logger.info("{} 탈퇴 완료", username);
                return true;
            } else {
                logger.info("{} 탈퇴 실패", username);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("{} 탈퇴 실패", username);
            return false;
        }
    }

}
