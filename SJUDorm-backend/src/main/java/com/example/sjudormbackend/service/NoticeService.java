package com.example.sjudormbackend.service;

import com.example.sjudormbackend.domain.AttachedFile;
import com.example.sjudormbackend.domain.Notice;
import com.example.sjudormbackend.repository.NoticeRepository;
import jakarta.annotation.PostConstruct;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NoticeService {

    private final FileService fileService;
    private final NoticeRepository noticeRepository;
    private WebDriver driver;

    public NoticeService(FileService fileService, NoticeRepository noticeRepository) {
        this.fileService = fileService;
        this.noticeRepository = noticeRepository;
    }

    public List<Notice> getNotices(){
        return noticeRepository.findAll();
    }

    public Optional<Notice> getNotice(Long id){
        return noticeRepository.findById(id);
    }

    @PostConstruct      //의존성 주입 완료 후 실행되는 메소드에 적용하는 annotation
    public void setup() {
        // WebDriver 경로 설정 (크롬 드라이버 예시)
        System.setProperty("webdriver.chrome.driver", "C:/Users/00kwh/SJU-Dorm-App/SJUDorm-backend/src/main/resources/static/chromedriver-win64/chromedriver.exe");
        /*
        우측에 chromedriver 경로가 첨부되어야 크롤링이 가능합니다.
         git에 driver가 올라간다면 해당 경로 각자 붙여 넣어주셔야 하고
         git에 올라가지 않는다면 직접 크롬 버젼 확인 후 chromedriver 다운로드 받아 해당 경로를 붙여넣어야 합니다.
         */

        // ChromeOptions 설정
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // 브라우저를 열지 않고 실행

        // WebDriver 생성
        driver = new ChromeDriver(options);
    }

    public List<String> crawlUrls(String noticeListUrl) throws Exception{
        driver.get(noticeListUrl);

        Thread.sleep(5000);

        List<WebElement> urls = driver.findElements(By.cssSelector("a"));

        List<String> urlList = new ArrayList<>();

        for(WebElement url : urls) {
            urlList.add(url.getAttribute("href"));
        }

        return urlList;
    }

    public Notice fetchNotice(String noticeUrl) throws Exception {
        driver.get(noticeUrl);
        String title = driver.findElement(By.id("subject")).getText();
        String writer = driver.findElement(By.id("regname")).getText();
        String dateText = driver.findElement(By.id("regdate")).getText();
        String viewCountText = driver.findElement(By.id("visitcnt")).getText();
        String content = driver.findElement(By.id("contents")).getText();
        List<WebElement> fileLinkElements = driver.findElements(By.id("attFile"));

        Date createdAt = parseDate(dateText);
        int viewCount = Integer.parseInt(viewCountText);

        List<AttachedFile> attFiles = new ArrayList<>();

        for (WebElement fileLinkElement : fileLinkElements) {
            String fileUrl = fileLinkElement.getAttribute("href");

            // 파일 이름과 저장 경로 설정
            String fileName = fileUrl.substring(fileUrl.lastIndexOf('/') + 1);
            String savePath = "C:/Users/00kwh/SJU-Dorm-App/SJUDorm-backend/src/main/java/com/example/sjudormbackend/resources/attachedfiles" + fileName;

            // 파일 다운로드
            downloadFile(fileUrl, savePath);

            // 파일 정보 데이터베이스에 저장
            AttachedFile attachedFile = new AttachedFile(fileName, savePath, new File(savePath).length());
            attFiles.add(attachedFile);
            fileService.saveFile(attachedFile);
        }
        return new Notice(title, writer, createdAt,false, content,viewCount, attFiles);
    }

    public List<Notice> fetchNotices() throws Exception {
        String noticeListUrl = "https://happydorm.sejong.ac.kr/60/6010.kmc#";

        List<String> noticeUrls = crawlUrls(noticeListUrl);

        List<Notice> notices = new ArrayList<>();

        for(String noticeUlr: noticeUrls) {
            notices.add(fetchNotice(noticeUlr));
        }

        return notices;
    }

    public Date parseDate(String dateText){         //텍스트 날짜를 Date 자료형으로 변환
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // 날짜 포맷 예: "2024-08-07"
        try {
            Date date = dateFormat.parse(dateText);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static File downloadFile(String fileUrl, String destinationPath) throws Exception {
        try (InputStream in = new URL(fileUrl).openStream();
             FileOutputStream out = new FileOutputStream(destinationPath)) {
            byte[] buffer = new byte[1024];
            int n;
            while ((n = in.read(buffer)) != -1) {
                out.write(buffer, 0, n);
            }
        }
        return new File(destinationPath);
    }
}