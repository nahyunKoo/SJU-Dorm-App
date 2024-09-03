package com.example.sjudormbackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@Table(name = "user")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long userId;        //key

    @Column(name = "student_name")
    private String studentName;    //학생 이름

    @Column(name = "student_id")
    private Long studentId;  //학번

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RewardPenaltyPoint> rewardPenaltyPoints;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PetitionLetter> petitionLetters;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @JsonIgnore
    private List<LoginRecord> loginRecords; // 로그인 기록

    public User(){

    }

    public User(Long studentId){
        this.studentId = studentId;
    }
}
