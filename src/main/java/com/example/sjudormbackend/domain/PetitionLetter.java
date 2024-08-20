package com.example.sjudormbackend.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "petition_letter")
public class PetitionLetter {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "petiton_letter_id", nullable = false)
    private Long petitionLetterId;

    @Column(name = "title", nullable = false)
    private String title;   //제목

    /*@Column(name = "writer", nullable = false)
    private String writer;    //erd 상 name이라고 되어 있는데 writer가 조금 다 나을 듯 하여 변경*/
    //user와 매핑되어 있기 때문에 작성자 정보는 매핑된 것에서 가져 올 수 있을 것이라고 판단하여 작성자 필드를 주석 처리함git

    @Column(name = "created_at", nullable = false)
    private Date createdAt; //작성일

    @Column(name = "is_secret", nullable = false)
    private boolean isSecret;       //비밀글 여부

    @Column(name = "content", nullable = false)
    private String content;         //내용

    @Column(name = "room_number", nullable = false)
    private int roomNumber; //방 번호

    @Column(name = "status", nullable = false)
    private boolean status; //접수 상태

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "petiton_letter_feedback_id")
    private PetitionLetterFeedback petitionLetterFeedback;
}
