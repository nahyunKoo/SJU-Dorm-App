package com.example.sjudormbackend.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "reward_penalty_point")
public class RewardPenaltyPoint {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reward_penalty_point_id", nullable = false)
    private long rewardPenaltyPointId;

    @Column(name = "type", nullable = false)
    private boolean type;   //true 면 상점, false면 벌점

    @Column(name = "cause", nullable = false)
    private String cause;   //상,벌점 사유

    @Column(name = "point", nullable = false)
    private int point;      //점수(절대값)

    @Column(name = "grant_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date grantDate; //부여 일자

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
