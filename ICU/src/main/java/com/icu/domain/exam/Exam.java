package com.icu.domain.exam;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.icu.domain.quiz.Quiz;
import com.icu.domain.quizforstudent.QuizForStudent;
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
public class Exam {

    @OneToMany(mappedBy = "examNumber", cascade = CascadeType.REMOVE)
    List<QuizForStudent> studentList = new ArrayList<QuizForStudent>();
    @OneToMany(mappedBy = "examNumber", cascade = CascadeType.REMOVE)
    List<Quiz> problemList = new ArrayList<Quiz>();

    @Id
    @Column(name = "exam_num")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long examNumber;

    @Column(name = "exam_name")
    private String examName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @Column(name = "open_time")
    private LocalDateTime openTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @Column(name = "close_time")
    private LocalDateTime closeTime;

    @Column(name = "exam_describe")
    private String examDescribe;

    @ManyToOne
    @JoinColumn(name = "ins_id")
    private Instructor instructorId;
}
