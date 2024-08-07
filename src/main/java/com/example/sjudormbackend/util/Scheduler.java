package com.example.sjudormbackend.util;

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

    @Scheduled(cron = "0 0 1 * * MON")
    public void schedulerCrawlMenu(){
        try {
            menuService.crawlMenu();
            log.info("Menu data has been successfully saved.");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("An error occurred while saving menu data.");
        }
    }
}
