package com.icu.domain.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.icu.domain.exam.Exam;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@DiscriminatorValue("ROLE_INSTRUCTOR")
public class Instructor extends User {

    @JsonManagedReference
    @OneToMany(mappedBy = "instructorId", cascade = CascadeType.REMOVE)
    List<Exam> examList = new ArrayList<Exam>();

    @Builder
    public Instructor() {
        super();
    }

    @Builder
    public Instructor(String username, String password, String fullname, String phoneNumber, List<Exam> examList) {
        super(username, password, fullname, phoneNumber, "ROLE_INSTRUCTOR");
        this.examList = examList;
    }

}
