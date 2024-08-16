package com.example.sjudormbackend.controller;

import com.example.sjudormbackend.domain.AttachedFile;
import com.example.sjudormbackend.domain.Notice;
import com.example.sjudormbackend.dto.AttachedFileDTO;
import com.example.sjudormbackend.dto.NoticeDTO;
import com.example.sjudormbackend.dto.NoticeListDTO;
import com.example.sjudormbackend.service.FileService;
import com.example.sjudormbackend.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private final NoticeService noticeService;
    @Autowired
    private final FileService fileService;

    @GetMapping("")
    public List<NoticeListDTO> getNoticeList(){
        List<Notice> notices = noticeService.getNotices();
        List<NoticeListDTO> noticeListDTOS = new ArrayList<>();
        for (Notice notice : notices) {
            NoticeListDTO noticeListDTO = new NoticeListDTO();
            noticeListDTO.setNoticeId(notice.getNoticeId());
            noticeListDTO.setNoticeTitle(notice.getNoticeTitle());
            noticeListDTO.setNoticeWriter(notice.getNoticeWriter());
            noticeListDTO.setNoticeCreatedAt(notice.getNoticeCreatedAt());
            noticeListDTO.setViewCount(notice.getViewCount());
            noticeListDTO.setPinned(notice.isPinned());
            noticeListDTOS.add(noticeListDTO);
        }
        return noticeListDTOS;
    }

    @GetMapping("/{notice_id}")
    public Optional<NoticeDTO> getNotice(@PathVariable Long notice_id){
        Optional<Notice> notice = noticeService.getNotice(notice_id);
        NoticeDTO noticeDTO = new NoticeDTO();
        noticeDTO.setNoticeId(notice.get().getNoticeId());
        noticeDTO.setNoticeTitle(notice.get().getNoticeTitle());
        noticeDTO.setNoticeContent(notice.get().getNoticeContent());
        noticeDTO.setNoticeWriter(notice.get().getNoticeWriter());
        noticeDTO.setNotoceCreatedAt(notice.get().getNoticeCreatedAt());

        Optional<List<AttachedFile>> attachedFiles = fileService.getFilesByNoticeId(notice_id);

        Optional<List<AttachedFileDTO>> attachedFileDTOs = fileService.convertToAttachedFileDTOs(attachedFiles);

        noticeDTO.setAttachedFiles(attachedFileDTOs);
        return Optional.of(noticeDTO);
    }
}
