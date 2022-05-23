package com.icu.domain.quizforstudent;

import com.icu.domain.exam.Exam;
import com.icu.domain.user.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizForStudent {

    @Id
    @Column(name = "stu_quiz_num")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long studentQuizNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stu_id")
    private Student studentId;

    @Column(name = "quiz_result")
    private String quizResult;

    @Column(name = "lier")
    private String lier;

    @Column(name = "cheating_time")
    private String cheatingTime;

    @Column(name = "video_name")
    private String videoName;

    @Column(name = "stu_answer")
    private String studentAnswer;

    @Column(name = "status")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_num")
    private Exam examNumber;
}
