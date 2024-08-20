package com.example.sjudormbackend.controller;

import com.example.sjudormbackend.domain.Menu;
import com.example.sjudormbackend.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    //menu 날짜에 맞춰 db 에서 가져오기
    @GetMapping("/{date}")
    public ResponseEntity<Menu> getMenu(@PathVariable String date) {
        Menu menu = menuService.getMenuByDate(date);
        log.info("menu " + menu.toString());
        return ResponseEntity.ok(menu);
    }

    //menu 전체 갖고오기
    @GetMapping("")
    public ResponseEntity<List<Menu>> getAllMenu() {
        List<Menu> menu = menuService.getMenu();
        log.info("menu " + menu.toString());
        return ResponseEntity.ok(menu);
    }

        /*
        //menu 크롤링 해서 db 에 save
        @PostMapping("/save")
        public ResponseEntity<String> saveMenu() {
            try {
                menuService.crawlMenu();
                return ResponseEntity.ok("Menu data has been successfully saved.");
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(500).body("An error occurred while saving menu data.");
            }
        }
        */

}
