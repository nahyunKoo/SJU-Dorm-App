package com.example.sjudormbackend.util;

import com.example.sjudormbackend.repository.MenuRepository;
import com.example.sjudormbackend.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Scheduler {

    @Autowired
    private MenuService menuService;
    @Autowired
    private MenuRepository menuRepository;

    //식단 크롤링 매주 오전 1시 월요일마다
    @Scheduled(cron = "0 0 1 * * MON")
    public void schedulerCrawlMenu(){
        try {
            menuRepository.deleteAll(); //기존 db 내용 모두 삭제
            menuService.crawlMenu();    //크롤링
            log.info("Menu data has been successfully saved.");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("An error occurred while saving menu data.");
        }
    }
}
