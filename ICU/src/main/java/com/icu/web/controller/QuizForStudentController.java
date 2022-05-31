package com.icu.web.controller;

import com.icu.service.QuizForStudentService;
import com.icu.web.dto.QuizForStudentDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/quizforstudent")
public class QuizForStudentController {
    private final QuizForStudentService quizForStudentService;

    public QuizForStudentController(QuizForStudentService quizForStudentService){
        this.quizForStudentService = quizForStudentService;
    }

    @PostMapping("/insert/{username}/{examNumber}")
    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    public ResponseEntity<String> insertQuizForStudent(@PathVariable String username, @PathVariable long examNumber) {
        if (quizForStudentService.insertQuizForStudent(username,examNumber)) {
            return new ResponseEntity<>("시험 매칭 성공", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("시험 매칭 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/{username}/{examNumber}")
    @PreAuthorize("hasAnyRole('INSTRUCTOR','STUDENT')")
    public ResponseEntity<QuizForStudentDto> getQuizForStudent(@PathVariable String username, @PathVariable long examNumber) {
        return new ResponseEntity<>(quizForStudentService.getQuizForStudent(username,examNumber), HttpStatus.OK) ;
    }

    @GetMapping("/get/{username}")
    @PreAuthorize("hasAnyRole('INSTRUCTOR','STUDENT')")
    public ResponseEntity<List<QuizForStudentDto>> getQuizForStudentListByUsername(@PathVariable String username) {
        return new ResponseEntity<>(quizForStudentService.getQuizForStudentListByUsername(username), HttpStatus.OK) ;
    }

    @GetMapping("/getqfs/{examNumber}")
    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    public ResponseEntity<List<QuizForStudentDto>> getQuizForStudentListByExamNumber(@PathVariable long examNumber) {
        return new ResponseEntity<>(quizForStudentService.getQuizForStudentListByExamNumber(examNumber), HttpStatus.OK) ;
    }

    @PutMapping("/update-quiz")
    public ResponseEntity<String> updateStudentTest(QuizForStudentDto quizForStudentDto, @RequestParam(value = "file", required = false) MultipartFile multipartFile){
        System.out.println(quizForStudentDto.getUsername());
        System.out.println(multipartFile);

        if (multipartFile != null && quizForStudentService.updateQuizForStudent(quizForStudentDto, multipartFile)) {
            return new ResponseEntity<>("답안 작성, 녹화파일 저장 성공", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("답안 작성, 녹화파일 저장 실패", HttpStatus.NOT_MODIFIED);
        }
    }

    @PutMapping("/update-score/{username}/{examNumber}/{quizResult}")
    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    public ResponseEntity<String> updateQuizScore(@PathVariable String username,@PathVariable Long examNumber,@PathVariable String quizResult) {

        if (quizForStudentService.updateQuizScore(username, examNumber, quizResult)){
            return  new ResponseEntity<>("채점 저장 성공",  HttpStatus.OK);
        } else {
            return new ResponseEntity<>("채점 저장 실패",  HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping("/delete/{username}/{examNumber}")
    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    public ResponseEntity<String> deleteQuizForStudentByUsername(@PathVariable String username, @PathVariable long examNumber) {

        if (quizForStudentService.deleteQuizForStudentByUsername(username, examNumber)) {
            return  new ResponseEntity<>("시험정보 삭제 성공",  HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("시험정보 삭제 실패",  HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
