package com.icu.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {

    @Query(value = "SELECT * FROM user WHERE role=?", nativeQuery = true)
    List<User> findAllUserByRole(String student);

    @Query(value = "select * from user where user_id not in (select stu_id from quizforstudent where exam_num=?) and role=?", nativeQuery = true)
    List<User> findAllUserNotInTest(long examNumber, String role);

}
