package com.icu.domain.user;

import com.icu.domain.quizforstudent.QuizForStudent;
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
    List<QuizForStudent> quizList = new ArrayList<QuizForStudent>();

    @Builder
    public Student() {
        super();
    }

    @Builder
    public Student(String username, String password, String fullname, String phoneNumber,
                   List<QuizForStudent> quizList) {
        super(username, password, fullname, phoneNumber, "rSTUDENT");
        this.quizList = quizList;
    }
}
