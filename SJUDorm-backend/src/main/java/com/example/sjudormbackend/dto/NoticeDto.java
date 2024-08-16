package com.example.sjudormbackend.dto;

import com.example.sjudormbackend.domain.AttachedFile;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Data
public class NoticeDTO {
        private long noticeId;
        private String noticeTitle;
        private String noticeContent;
        private String noticeWriter;
        private Date notoceCreatedAt;
        private Optional<List<AttachedFileDTO>> attachedFiles;
}
