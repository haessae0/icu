package com.icu.domain.examforstudent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface eStudentRepository extends JpaRepository<eStudent, Long> {

    @Query(value = "SELECT * FROM estudent WHERE s_id=? and exam_num=?", nativeQuery = true)
    Optional<eStudent> findeStudentByUserIdAndExamNumber(String userId, long examNumber);

    @Query(value = "SELECT * FROM estudent WHERE s_id=?", nativeQuery = true)
    Optional<List<eStudent>> findAlleStudentByUserId(String userId);

    @Query(value = "SELECT * FROM estudent WHERE exam_num=?", nativeQuery = true)
    Optional<List<eStudent>> findAlleStudentByExamNumber(long examNumber);
}
