package com.icu.domain.quizforstudent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface QuizForStudentRepository extends JpaRepository<QuizForStudent, Long> {

    @Query(value = "SELECT * FROM quizforstudent WHERE stu_id=? and exam_num=?", nativeQuery = true)
    Optional<QuizForStudent> findeStudentByUserNameAndExamNumber(String username, long examNumber);

    @Query(value = "SELECT * FROM quizforstudent WHERE stu_id=?", nativeQuery = true)
    Optional<List<QuizForStudent>> findAlleStudentByUserName(String username);

    @Query(value = "SELECT * FROM quizforstudent WHERE exam_num=?", nativeQuery = true)
    Optional<List<QuizForStudent>> findAlleStudentByExamNumber(long examNumber);
}
