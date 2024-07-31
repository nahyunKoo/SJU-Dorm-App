package com.example.sjudormbackend.controller;

import com.example.sjudormbackend.domain.Menu;
import com.example.sjudormbackend.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/{date}")
    public ResponseEntity<Menu> getMenu(@PathVariable String date) {
        Menu menu = menuService.getMenuByDate(date);
        return ResponseEntity.ok(menu);
    }
}
