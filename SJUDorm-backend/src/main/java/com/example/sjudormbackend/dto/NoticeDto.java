package com.example.sjudormbackend.dto;

import com.example.sjudormbackend.domain.Notice;
import lombok.Data;

import java.util.List;

@Data
public class NoticeDto {
        private int page;
        private int pageSize = 20;
        private List<Notice> notices;
}
