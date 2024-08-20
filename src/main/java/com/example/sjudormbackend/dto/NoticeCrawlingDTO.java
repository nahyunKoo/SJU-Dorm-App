package com.example.sjudormbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NoticeCrawlingDTO {
    private long noticeId;
    private int bbsId;
    private boolean isPinned;
    private int pageIndex;
    private String noticeUrl;
}
