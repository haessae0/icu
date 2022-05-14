package com.icu.domain.user;

import com.icu.domain.examforstudent.eStudent;
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
@DiscriminatorValue("rSTUDENT")
public class Student extends User {

    @OneToMany(mappedBy = "studentId", cascade = CascadeType.REMOVE)
    List<eStudent> examList = new ArrayList<eStudent>();

    @Builder
    public Student() {
        super();
    }

    @Builder
    public Student(String userId, String password, String name, String phoneNumber, String profileImage, List<eStudent> examList) {
        super(userId, password, name, phoneNumber, profileImage, "rSTUDENT");
        this.examList = examList;
    }
}
