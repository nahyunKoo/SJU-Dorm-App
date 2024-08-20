package com.example.sjudormbackend.service;

import com.example.sjudormbackend.domain.AttachedFile;
import com.example.sjudormbackend.domain.Notice;
import com.example.sjudormbackend.dto.NoticeCrawlingDTO;
import com.example.sjudormbackend.repository.NoticeRepository;
import jakarta.annotation.PostConstruct;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.springframework.stereotype.Service;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

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

    public List<NoticeCrawlingDTO> crawlUrls(String noticeListUrl) throws Exception{
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // 30초 대기
        driver.get(noticeListUrl);
        wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'"));

        /*List<WebElement> urls = driver.findElements(By.cssSelector("a"));

        List<String> urlList = new ArrayList<>();

        for(WebElement url : urls) {
            urlList.add(url.getAttribute("href"));
        }

        for(String url : urlList) {
            System.out.println(url);
        }

        return urlList;*/

        // 페이지 정보 크롤링
        List<WebElement> rows = driver.findElements(By.cssSelector("tbody#list tr"));

        List<NoticeCrawlingDTO> noticeCrawlingDTOS = new ArrayList<>();

        int pageIndex = 0;

        for (WebElement row : rows) {
            pageIndex++;
            // getBbs ID 추출
            WebElement linkElement = row.findElement(By.cssSelector("a[onclick^='getBbs']"));
            String onclickValue = linkElement.getAttribute("onclick");
            int bbsId = Integer.parseInt(onclickValue.replaceAll("getBbs\\('", "").replaceAll("'\\)", ""));

            // span class가 "notice"인지 확인
            boolean isPinned = Optional.ofNullable(row.findElements(By.cssSelector("span.notice")))
                    .map(list -> !list.isEmpty())
                    .orElse(false);

            int noticeId;
            if(isPinned){
                noticeId = bbsId * 10000;     //게시글이 고정 글일 경우 게시물 번호가 없어 BbsId * 10000를 id값으로 사용합니다.(기존 id와 중복 방지를 위함)
            }
            else{
                WebElement postNumberElement = row.findElement(By.xpath("td[2]"));
                noticeId = Integer.parseInt(postNumberElement.getText());
            }
            // 결과 출력
            System.out.println("NoticeId: " + noticeId);
            System.out.println("Bbs ID: " + bbsId);
            System.out.println("Is Pinned: " + isPinned);
            System.out.println("Page Index: " + (pageIndex/ 23 + 1));
            System.out.println("---------------");

            String url = "https://happydorm.sejong.ac.kr/bbs/getBbsWriteView.kmc?seq=" + bbsId + "&bbs_locgbn=SJ&bbs_id=notice&sType=&sWord=&pPage=" + String.valueOf(pageIndex/23 + 1);
            noticeCrawlingDTOS.add(new NoticeCrawlingDTO(noticeId, bbsId, isPinned, (pageIndex / 23 + 1), url));
        }
        return noticeCrawlingDTOS;
    }

    public Notice fetchNotice(NoticeCrawlingDTO noticeCrawlingDTO) throws Exception {
        setup();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // 30초 대기
        driver.get(noticeCrawlingDTO.getNoticeUrl());

        wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'"));

        String title = driver.findElement(By.id("subject")).getText();
        String writer = driver.findElement(By.id("regname")).getText();
        String dateText = driver.findElement(By.id("regdate")).getText();
        String viewCountText = driver.findElement(By.id("visitcnt")).getText();
        String content = driver.findElement(By.id("contents")).getText();
        WebElement attFileDiv = driver.findElement(By.id("attFile"));

        Date createdAt = parseDate(dateText);


        int viewCount = Integer.parseInt(viewCountText);

        List<WebElement> fileLinks = attFileDiv.findElements(By.tagName("a"));

        List<AttachedFile> attFiles = new ArrayList<>();

        for (WebElement fileLink : fileLinks) {
            String fileUrl = fileLink.getAttribute("href");
            System.out.println(fileUrl);
            if(fileUrl != null) {
                System.out.println("첨부파일 정보 저장 중");
                // 파일 이름
                String fileName = fileLink.getText();

                AttachedFile attFile = new AttachedFile(fileUrl, fileName);
                attFiles.add(attFile);
                // 파일 정보 데이터베이스에 저장
                //fileService.saveFile(attFile);    연관관계 설정으로 따로 저장 필요 x
            }
        }
        Notice notice =  new Notice(noticeCrawlingDTO.getNoticeId(), title, content, writer, createdAt, viewCount, noticeCrawlingDTO.isPinned(), noticeCrawlingDTO.getPageIndex(), attFiles);
        for(AttachedFile attFile : attFiles){
            attFile.setNotice(notice);
        }
        return notice;
    }

    public List<Notice> fetchNotices() throws Exception {
        setup();
        String noticeListUrl = "https://happydorm.sejong.ac.kr/60/6010.kmc#";

        List<Notice> notices = new ArrayList<>();
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // 10초 대기
            driver.get(noticeListUrl);
            wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'"));

            while (true) {
                List<NoticeCrawlingDTO> noticeCrawlingDTO = crawlUrls(noticeListUrl);

                for (NoticeCrawlingDTO noticeCrawlDTO : noticeCrawlingDTO) {
                    notices.add(fetchNotice(noticeCrawlDTO));
                }
                // 다음 게시글 버튼 확인
                WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.btn_arr1[onclick*='getBbsList']")));
                //((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", nextButton);

                // 버튼의 onclick 속성 가져오기
                String onClickValue = nextButton.getAttribute("onclick");

                // "마지막 페이지입니다."인지 확인
                if (onClickValue.contains("alert('마지막 페이지입니다.')")) {
                    System.out.println("마지막 페이지입니다. 크롤링을 종료합니다.");
                    break;
                }
                // 다음 페이지로 이동
                JavascriptExecutor js = (JavascriptExecutor) driver;
                System.out.println(nextButton.isDisplayed());
                nextButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.btn_arr1[onclick*='getBbsList']")));
                js.executeScript("arguments[0].click();", nextButton);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            driver.quit();
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

   /* private static File downloadFile(String fileUrl, String destinationPath) throws Exception {
        try (InputStream in = new URL(fileUrl).openStream();
             FileOutputStream out = new FileOutputStream(destinationPath)) {
            byte[] buffer = new byte[1024];
            int n;
            while ((n = in.read(buffer)) != -1) {
                out.write(buffer, 0, n);
            }
        }
        return new File(destinationPath);
    }*/
}
