package com.icu.domain.examinfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExamInfoRepository extends JpaRepository<ExamInfo, Long> {

    @Query(value = "SELECT * FROM examinfo WHERE ins_id=? ORDER BY exam_num", nativeQuery = true)
    List<ExamInfo> findAllExamByuserId(String userId);
}
