package com.icu.domain.quiz;

import com.icu.domain.exam.Exam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Quiz {

    @Id
    @Column(name = "quiz_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long quizId;

    @Column(name = "quiz_num")
    private int quizNum;

    @Column(name = "quiz_describe")
    private String quizDescribe;

    @Column(name = "quiz_selection")
    private String quizSelection;

    @Column(name = "quiz_answer")
    private String quizAnswer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_num")
    private Exam examNumber;
}
