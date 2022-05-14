package com.icu.domain.examforstudent;

import com.icu.domain.examinfo.ExamInfo;
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
public class eStudent {

    @Id
    @Column(name = "s_exam_num")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long studentExamNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "s_id")
    private Student studentId;

    @Column(name = "result")
    private String result;

    @Column(name = "cheating")
    private String cheating;

    @Column(name = "cheating_time")
    private String cheatingTime;

    @Column(name = "s_video")
    private String studentVideo;

    @Column(name = "e_answer")
    private String examAnswer;

    @Column(name = "e_state")
    private String examState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_num")
    private ExamInfo examNumber;
}
