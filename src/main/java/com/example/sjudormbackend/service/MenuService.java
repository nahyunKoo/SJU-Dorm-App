package com.example.sjudormbackend.service;

import com.example.sjudormbackend.domain.Menu;
import com.example.sjudormbackend.repository.MenuRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    public Menu getMenuByDate(String date){
        return menuRepository.findByDate(date);
    }

    public void saveMenu(Menu menu){
        menuRepository.save(menu);
    }
}
