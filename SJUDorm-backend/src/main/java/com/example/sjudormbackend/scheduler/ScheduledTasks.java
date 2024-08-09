package com.example.sjudormbackend.scheduler;

import com.example.sjudormbackend.domain.Notice;
import com.example.sjudormbackend.repository.NoticeRepository;
import com.example.sjudormbackend.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScheduledTasks {

    @Autowired
    private NoticeService noticeCrawler;

    @Autowired
    private NoticeRepository noticeRepository;

    @Scheduled(fixedRate = 60000)
    public void fetchAndSaveNotices() throws Exception {
        List<Notice> notices = noticeCrawler.fetchNotices();
        for(Notice notice: notices){
            noticeRepository.save(notice);
        }
    }
}
