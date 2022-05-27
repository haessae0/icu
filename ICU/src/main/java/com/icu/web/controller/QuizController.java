package com.icu.web.controller;

import com.icu.service.QuizService;
import com.icu.web.dto.QuizDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {
    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping("/create/{examNumber}")
    @PreAuthorize("hasAnyRole('rINSTRUCTOR')")
    public ResponseEntity<String> insertQuiz(@PathVariable long examNumber, QuizDto quizDto) {
        Boolean check = quizService.insertQuiz(examNumber, quizDto);

        if (check) {
            return new ResponseEntity<>("문제 성공", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("문제 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get")
    @PreAuthorize("hasAnyRole('rINSTRUCTOR')")
    public ResponseEntity<List<QuizDto>> getQuizByExamNumber(@RequestParam long examNumber) {
        return new ResponseEntity<>(quizService.getQuizByExamNumber(examNumber), HttpStatus.OK);
    }

    @GetMapping("/get/{quizId}")
    @PreAuthorize("hasAnyRole('rINSTRUCTOR')")
    public ResponseEntity<QuizDto> getQuiz(@PathVariable long quizId) {
        return new ResponseEntity<>(quizService.getQuiz(quizId), HttpStatus.OK);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('rINSTRUCTOR')")
    public ResponseEntity<String> updateQuiz(QuizDto quizDto) {
        Boolean check = quizService.updateQuiz(quizDto);

        if (check) {
            return new ResponseEntity<>("시험 문제 수정 성공", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("시험 문제 수정 실패", HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyRole('rINSTRUCTOR')")
    public ResponseEntity<String> deleteQuiz(@RequestParam long quizId, @RequestParam long examNumber) {

        if (quizService.deleteQuiz(quizId, examNumber)) {
            return new ResponseEntity<>("시험 문제 삭제 성공", HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("시험 문제 삭제 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
