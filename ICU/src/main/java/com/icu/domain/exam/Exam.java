package com.icu.domain.exam;

import com.icu.domain.examinfo.ExamInfo;
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
public class Exam {

    @Id
    @Column(name = "e_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long examId;

    @Column(name = "e_num")
    private int examNum;

    @Column(name = "e_question")
    private String examQuestion;

    @Column(name = "e_selection")
    private String examSelection;

    @Column(name = "e_desimage")
    private String examDesImage;

    @Column(name = "e_answer")
    private String examAnswer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_num")
    private ExamInfo examNumber;
}
