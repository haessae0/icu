package com.icu.domain.quiz;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    @Query(value = "SELECT * FROM exam WHERE exam_num=? ORDER BY e_num", nativeQuery = true)
    List<Quiz> findAllExamByExamNumber(Long examNumber);

}
