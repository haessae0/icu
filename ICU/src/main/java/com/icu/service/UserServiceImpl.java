package com.icu.service;

import com.icu.domain.user.Instructor;
import com.icu.domain.user.Student;
import com.icu.domain.user.User;
import com.icu.domain.user.UserRepository;
import com.icu.util.token.JwtFilter;
import com.icu.util.token.TokenProvider;
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
    public Boolean updateUser(UserDto userDto, MultipartFile mfile) {
        Optional<User> findUser = userRepository.findById(userDto.getUserId());

        try {
            if (findUser.isPresent()) {
                String userimg = null;

                try {
                    userimg = System.currentTimeMillis() + mfile.getOriginalFilename();
                    mfile.transferTo(
                            new File(System.getProperty("user.dir") + "\\src\\main\\webapp\\userimg\\" + userimg));

                    String filename = findUser.get().getProfileImage();
                    File file = new File(System.getProperty("user.dir") + "\\src\\main\\webapp\\userimg\\" + filename);

                    if (file.exists() && !filename.equals("img.png")) {
                        if (file.delete()) {
                            logger.info("{} 이미지 삭제 완료", userDto.getUserId());
                        } else {
                            logger.info("{} 이미지 삭제 실패", userDto.getUserId());
                        }
                    }
                } catch (IllegalStateException | IOException exception) {
                    exception.printStackTrace();
                    logger.error("{} 회원 이미지 갱신 오류 발생", userDto.getUserId());
                    return false;
                }
                User finduser = findUser.get();
                finduser.setUserId(finduser.getUserId());
                finduser.setPassword(passwordEncoder.encode(userDto.getPassword()));
                finduser.setName(userDto.getName());
                finduser.setPhoneNumber(userDto.getPhoneNumber());
                finduser.setProfileImage(userimg);

                userRepository.save(finduser);
                logger.info("{} 수정 성공", userDto.getUserId());
                return true;
            } else {
                logger.info("{} 수정 실패", userDto.getUserId());
                return false;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            logger.error("{} 수정 실패", userDto.getUserId());
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

    @Transactional
    public Boolean updateUserWithoutimg(UserDto userDto) {
        Optional<User> findUser = userRepository.findById(userDto.getUserId());

        try {
            if (findUser.isPresent()) {
                User finduser = findUser.get();
                finduser.setUserId(finduser.getUserId());
                finduser.setPassword(passwordEncoder.encode(userDto.getPassword()));
                finduser.setName(userDto.getName());
                finduser.setPhoneNumber(userDto.getPhoneNumber());

                userRepository.save(finduser);
                logger.info("{} 수정 성공", userDto.getUserId());
                return true;
            } else {
                logger.info("{} 수정 실패", userDto.getUserId());
                return false;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            logger.error("{} 수정 실패", userDto.getUserId());
            return false;
        }
    }

    public ResponseEntity<UserDto> login(LoginDto loginDto, HttpServletResponse httpServletResponse) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                loginDto.getUserId(), loginDto.getPassword());
        Authentication auth = authenticationManagerBuilder.getObject().authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt = tokenProvider.createToken(auth);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        Optional<User> userOpt = userRepository.findById(auth.getName());

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            UserDto userDto = UserDto.builder().userId(user.getUserId()).name(user.getName())
                    .phoneNumber(user.getPhoneNumber()).profileImage(user.getProfileImage()).role(user.getRole()).build();

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

    public UserDto getUser(String userId) {
        Optional<User> findUser = userRepository.findById(userId);

        if (findUser.isPresent()) {
            User user = findUser.get();
            UserDto userDto = UserDto.builder().userId(user.getUserId()).name(user.getName())
                    .phoneNumber(user.getPhoneNumber()).profileImage(user.getProfileImage()).role(user.getRole()).build();
            logger.info("{} 조회 성공", userId);
            return userDto;
        } else {
            logger.info("{} 조회 실패", userId);
            return null;
        }
    }

    public UserDto getStudent(String userId) {
        Optional<User> findUser = userRepository.findById(userId);

        if (findUser.isPresent()) {
            User user = findUser.get();
            UserDto userDto = UserDto.builder().userId(user.getUserId()).name(user.getName())
                    .phoneNumber(user.getPhoneNumber()).profileImage(user.getProfileImage()).role(user.getRole()).build();

            logger.info("{} 조회 성공", userId);
            return userDto;
        } else {
            logger.info("{} 조회 실패", userId);
            return null;
        }
    }

    public List<UserDto> getStudentList() {
        List<User> userList = userRepository.findAllUserByRole("rSTUDENT");
        List<UserDto> uDtoList = userList.stream().map(u -> new UserDto(u.getUserId(), null, u.getName(),
                u.getPhoneNumber(), u.getProfileImage(), u.getRole())).collect(Collectors.toList());

        logger.info("전체 학생 회원 조회");
        return uDtoList;
    }

    public List<UserDto> getStudentListNotInTest(long examNumber) {
        List<User> userList = userRepository.findAllUserNotInTest(examNumber, "rSTUDENT");
        List<UserDto> uDtoList = userList.stream().map(u -> new UserDto(u.getUserId(), null, u.getName(),
                u.getPhoneNumber(), u.getProfileImage(), u.getRole())).collect(Collectors.toList());

        logger.info("전체 학생 회원 조회");
        return uDtoList;
    }

    @Transactional
    public Boolean deleteUser(String userId) {
        Optional<User> findUser = userRepository.findById(userId);

        try {
            if (findUser.isPresent()) {
                String filename = findUser.get().getProfileImage();
                File file = new File(System.getProperty("user.dir") + "\\src\\main\\webapp\\userimg\\" + filename);

                if (file.exists() && !filename.equals("img.png")) {
                    if (file.delete()) {
                        logger.info("{} 이미지 삭제 완료", findUser.get().getUserId());
                    } else {
                        logger.info("{} 이미지 삭제 실패", findUser.get().getUserId());
                    }
                }
                userRepository.delete(findUser.get());
                logger.info("{} 탈퇴 완료", userId);
                return true;
            } else {
                logger.info("{} 탈퇴 실패", userId);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("{} 탈퇴 실패", userId);
            return false;
        }
    }


}
