package com.example.sjudormbackend.dto;

import lombok.Data;

import java.util.Date;

@Data
public class NoticeListDTO {
    private long noticeId;
    private String noticeTitle;
    private String noticeWriter;
    private Date noticeCreatedAt;
    private int viewCount;
    private boolean pinned;
}
