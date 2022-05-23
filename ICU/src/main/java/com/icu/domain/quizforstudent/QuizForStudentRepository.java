package com.icu.domain.quizforstudent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface QuizForStudentRepository extends JpaRepository<QuizForStudent, Long> {

    @Query(value = "SELECT * FROM estudent WHERE s_id=? and exam_num=?", nativeQuery = true)
    Optional<QuizForStudent> findeStudentByUserIdAndExamNumber(String userId, long examNumber);

    @Query(value = "SELECT * FROM estudent WHERE s_id=?", nativeQuery = true)
    Optional<List<QuizForStudent>> findAlleStudentByUserId(String userId);

    @Query(value = "SELECT * FROM estudent WHERE exam_num=?", nativeQuery = true)
    Optional<List<QuizForStudent>> findAlleStudentByExamNumber(long examNumber);
}
