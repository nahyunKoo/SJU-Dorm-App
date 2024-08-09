package com.example.sjudormbackend.controller;

import com.example.sjudormbackend.domain.Notice;
import com.example.sjudormbackend.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Slf4j
@RestController
@RequiredArgsConstructor
public class NoticeController {

    @Autowired
    private final NoticeService noticeService;

    @GetMapping("/notice")
    public List<Notice> getNotices(){
        return noticeService.getNotices();
    }

    @GetMapping("/{notice_id}")
    public Optional<Notice> getNotice(@PathVariable Long notice_id){
        Optional<Notice> notice = noticeService.getNotice(notice_id);
        return notice;
    }
}
