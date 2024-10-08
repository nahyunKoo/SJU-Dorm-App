package com.example.sjudormbackend.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity @Data
@Table(name = "facility_petition_letter_feedback")
public class FacilityPetitionLetterFeedback {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "facility_petition_letter_feedback_id")
    private Long facilityPetitionLetterFeedbackId;

    @OneToOne
    @JoinColumn(name = "facility_petition_letter_id")
    private FacilityPetitionLetter facilityPetitionLetter;

    @Column(name = "feedback")
    private String feedback;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "status")
    private boolean status;

}
