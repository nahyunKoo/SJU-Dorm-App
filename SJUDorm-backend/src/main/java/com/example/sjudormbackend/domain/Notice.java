package com.example.sjudormbackend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity @Data
@NoArgsConstructor
@Table(name = "notice")
public class Notice {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private int noticeId;

    @Column(name = "notice_title")
    private String noticeTitle;

    @Column(name = "notice_writer")
    private String noticeWriter;

    @Column(name = "notice_created_at")
    private Date noticeCreatedAt;

    /*@Column(name = "notice_img_url")
    private String noticeImgUrl;*/

    @Column(name = "notice_content")
    private String noticeContent;

    @Column(name = "notic_view_count")
    private int viewCount;

    @Column(name = "notice_pinned")
    private boolean pinned;

    @Column(name = "notice_url")
    private String noticeUrl;       //각 게시글들의 url을 db에 저장(크롤링 시 필요)

    @OneToMany(mappedBy = "notice")
    private List<AttachedFile> attachedFiles;

    public Notice(String noticeTitle, String noticeWriter, Date noticeCreatedAt, boolean pinned, String noticeContent, int viewCount, List<AttachedFile> attachedFiles) {
        this.noticeTitle = noticeTitle;
        this.noticeWriter = noticeWriter;
        this.noticeCreatedAt = noticeCreatedAt;
        this.pinned = pinned;
        this.noticeContent = noticeContent;
        this.viewCount = viewCount;
        this.attachedFiles = attachedFiles;
    }
}
