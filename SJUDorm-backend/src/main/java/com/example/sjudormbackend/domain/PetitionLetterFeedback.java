package com.example.sjudormbackend.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "petition_letter_feedback")
public class PetitionLetterFeedback {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "petition_letter_feedback_id", nullable = false)
    private long petitionLetterFeedbackId;

    @Column(name = "content", nullable = false)
    private String content; //피드백 내용

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt; //피드백 일자

    @Column(name = "status", nullable = false)
    private boolean statue; //처리상태

    @OneToOne(mappedBy = "petitionLetterFeedback")
    private PetitionLetter petitionLetter;
}
