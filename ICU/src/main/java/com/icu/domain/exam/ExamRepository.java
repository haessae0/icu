package com.icu.domain.exam;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, Long> {

    @Query(value = "SELECT * FROM examinfo WHERE ins_id=? ORDER BY exam_num", nativeQuery = true)
    List<Exam> findAllExamByuserId(String userId);
}
