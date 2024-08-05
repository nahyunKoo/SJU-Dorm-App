package com.example.sjudormbackend.service;

import com.example.sjudormbackend.domain.Menu;
import com.example.sjudormbackend.repository.MenuRepository;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    public Menu getMenuByDate(String date){
        return menuRepository.findByDate(date);
    }

    public List<Menu> getMenu(){return menuRepository.findAll();}

    public void saveMenu(Menu menu){
        menuRepository.save(menu);
    }

    public void fetchAndSaveMenu(String url) throws Exception{
        Document doc = Jsoup.connect(url).get();
        List<Element> days = doc.select("ul.vpanel > li.openli");
        for (Element day : days) {
            String date = day.select("th.te_left").text(); // 식단 날짜
            String launch = day.select("td#fo_menu_lun" + day.attr("id").substring(3)).text(); // 중식
            String dinner = day.select("td#fo_menu_eve" + day.attr("id").substring(3)).text(); // 석식

            log.info("Fetched menu for date: {}", date);
            log.info("Launch: {}", launch);
            log.info("Dinner: {}", dinner);
            Menu menu = new Menu();
            // date 문자열을 Date 객체로 변환
            menu.setDate(date);
            menu.setLaunch(launch);
            menu.setDinner(dinner);

            saveMenu(menu);

            log.info(menu.toString());
        }
    }
}
