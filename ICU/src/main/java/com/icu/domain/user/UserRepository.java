package com.icu.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {

    @Query(value = "SELECT * FROM user WHERE role=?", nativeQuery = true)
    List<User> findAllUserByRole(String student);

    @Query(value = "select * from user where u_id not in (select stu_id from studenttest where test_num=?) and role=?", nativeQuery = true)
    List<User> findAllUserNotInTest(long testnum, String role);

}
