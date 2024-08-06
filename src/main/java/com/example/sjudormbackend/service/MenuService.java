package com.example.sjudormbackend.service;

import com.example.sjudormbackend.domain.Menu;
import com.example.sjudormbackend.repository.MenuRepository;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
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


    //메뉴 크롤링
    public void crawlMenu(){
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\angel\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

        // WebDriver 인스턴스 생성
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // 30초 대기

        try {
            // 웹 페이지 열기
            driver.get("https://happydorm.sejong.ac.kr/60/6050.kmc");
            //페이지 로딩 대기
            wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'"));
            WebElement tabDayA = driver.findElement(By.xpath("//*[@id='tabDayA']"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", tabDayA);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", tabDayA);

            // 메뉴 정보 크롤링
            for (int day = 1; day <= 7; day++) {
                String lunchId = "fo_menu_lun" + day;
                String dinnerId = "fo_menu_eve" + day;
                String dateId = "vDate" + day;

                //아이디로 요소 찾기
                WebElement lunchElement = driver.findElement(By.id(lunchId));
                WebElement dinnerElement = driver.findElement(By.id(dinnerId));
                WebElement dateElement = driver.findElement(By.id(dateId));
                //string 으로 저장
                String dateText = dateElement.getText();
                String lunchText = lunchElement.getText();
                String dinnerText = dinnerElement.getText();
                log.info("lunchText" + lunchText);
                log.info("dinnerText" + dinnerText);
                log.info("dateText" + dateText);

                //menu 객체 만들기
                Menu menu = new Menu();
                menu.setDate(dateText);
                menu.setLunch(lunchText);
                menu.setDinner(dinnerText);

                menuRepository.save(menu);  //저장
            }
        } finally {
            driver.quit();
        }
    }
}
