package com.icu.domain.exam;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, Long> {

    @Query(value = "SELECT * FROM exam WHERE exam_num=? ORDER BY e_num", nativeQuery = true)
    List<Exam> findAllExamByExamNumber(Long examNumber);

}
