package com.icu.web.controller;

import com.icu.service.ExamService;
import com.icu.web.dto.ExamDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exam")
public class ExamController {
    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('rINSTRUCTOR')")
    public ResponseEntity<Long> createExam(@RequestParam String username, @RequestBody ExamDto examDto) {
        return new ResponseEntity<>(examService.createExam(username, examDto), HttpStatus.CREATED);
    }

    @GetMapping("/get")
    @PreAuthorize("hasAnyRole('rINSTRUCTOR')")
    public ResponseEntity<List<ExamDto>> getExamByUsername(@RequestParam String username) {
        return new ResponseEntity<>(examService.getExamByUsername(username), HttpStatus.OK);
    }

    @GetMapping("/get/{examNumber}")
    @PreAuthorize("hasAnyRole('rSTUDENT', 'rINSTRUCTOR')")
    public ResponseEntity<ExamDto> getExam(@PathVariable Long examNumber) {
        return new ResponseEntity<>(examService.getExam(examNumber), HttpStatus.OK);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('rINSTRUCTOR')")
    public ResponseEntity<String> updateExam(@RequestBody ExamDto examDto) {
        if (examService.updateExam(examDto)) {
            return new ResponseEntity<>("수정 성공", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("수정 실패", HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyRole('rINSTRUCTOR')")
    public ResponseEntity<String> deleteExam(@RequestParam String username, @RequestParam Long examNumber) {
        if (examService.deleteExam(username, examNumber)) {
            return new ResponseEntity<>("삭제 성공", HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("삭제 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
