package com.example.sjudormbackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "login_record")
public class LoginRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "login_record_id", nullable = false) // 프라이머리 키 컬럼
    private Long loginRecordId;
    @Column(name = "cookies")
    private String cookies;
    @Column(name = "login_time")
    private Timestamp loginTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude  // 무한 루프 방지를 위해 추가
    @JsonIgnore
    private User user;

    public LoginRecord() {
    }

    public LoginRecord( User user, String cookies, Timestamp loginTime) {
        this.cookies = cookies;
        this.loginTime = loginTime;
        this.user = user;
    }


}
