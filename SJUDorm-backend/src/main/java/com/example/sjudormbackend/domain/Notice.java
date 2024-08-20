package com.example.sjudormbackend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity @Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "notice")
public class Notice {
    @Id
    @Column(name = "notice_id") //id값은 저절로 증가하는 것이 아닌 크롤링 시 글 번호를 사용하도록 변경하였습니다.
    private Long noticeId;

    @Column(name = "notice_title")
    private String noticeTitle;

    @Column(name = "notice_content", length = 10000)    //게시글 내용 글자 수 제한을 10000자로 늘렸습니다.
    private String noticeContent;

    @Column(name = "notice_writer")
    private String noticeWriter;

    @Column(name = "notice_created_at")
    private Date noticeCreatedAt;

    /*@Column(name = "notice_img_url")
    private String noticeImgUrl;*/


    @Column(name = "notic_view_count")
    private int viewCount;

    @Column(name = "notice_pinned", columnDefinition = "TINYINT(1)")
    private boolean pinned;

    @Column(name = "notice_page_located")       //공지가 몇 번째 페이지에 있는지 필드를 추가했습니다.
    private int pageLocated;

/*    @Column(name = "notice_url")
    private String noticeUrl;       //각 게시글들의 url을 db에 저장(크롤링 시 필요)*/

    @OneToMany(mappedBy = "notice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AttachedFile> attachedFiles = new ArrayList<>();

    public void addAttachedFile(AttachedFile attachedFile) {
        attachedFile.setNotice(this);
        this.attachedFiles.add(attachedFile);
    }
}
