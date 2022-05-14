package com.icu.domain.examinfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.icu.domain.exam.Exam;
import com.icu.domain.examforstudent.eStudent;
import com.icu.domain.user.Instructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ExamInfo {

    @OneToMany(mappedBy = "examNumber", cascade = CascadeType.REMOVE)
    List<eStudent> studentList = new ArrayList<eStudent>();
    @OneToMany(mappedBy = "examNumber", cascade = CascadeType.REMOVE)
    List<Exam> problemList = new ArrayList<Exam>();

    @Id
    @Column(name = "exam_num")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long examNumber;

    @Column(name = "exam_name")
    private String examName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @Column(name = "start_time")
    private LocalDateTime startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "exam_describe")
    private String examDescribe;

    @ManyToOne
    @JoinColumn(name = "ins_id")
    private Instructor instructorId;
}
