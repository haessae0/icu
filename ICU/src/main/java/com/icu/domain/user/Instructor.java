package com.icu.domain.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.icu.domain.examinfo.ExamInfo;
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
@DiscriminatorValue("rINSTUCTOR")
public class Instructor extends User {

    @JsonManagedReference
    @OneToMany(mappedBy = "instructorId", cascade = CascadeType.REMOVE)
    List<ExamInfo> examList = new ArrayList<ExamInfo>();

    @Builder
    public Instructor() {
        super();
    }

    @Builder
    public Instructor(String userId, String password, String name, String phoneNumber, String profileImage, List<ExamInfo> examList) {
        super(userId, password, name, phoneNumber, profileImage, "rINSTUCTOR");
        this.examList = examList;
    }


}
