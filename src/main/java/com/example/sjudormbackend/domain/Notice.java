package com.example.sjudormbackend.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity @Data
@Table(name = "notice")
public class Notice {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private int NoticeId;

    @Column(name = "notice_title")
    private String NoticeTitle;

    @Column(name = "notice_writer")
    private String noticeWriter;

    @Column(name = "notice_created_at")
    private Date noticeCreatedAt;

    @Column(name = "notice_img_url")
    private String noticeImgUrl;

    @Column(name = "notic_view_count")
    private int viewCount;

    @Column(name = "notice_pinned")
    private boolean pinned;
}
