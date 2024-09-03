package com.example.sjudormbackend.service;

import com.example.sjudormbackend.domain.LoginRecord;
import com.example.sjudormbackend.domain.User;
import com.example.sjudormbackend.dto.LoginRequest;
import com.example.sjudormbackend.repository.LoginRecordRepository;
import com.example.sjudormbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;

@Slf4j
@RequiredArgsConstructor
@Service
public class LoginService {

    private final RestTemplate restTemplate;
    private final UserRepository userRepository;
    private final LoginRecordRepository loginRecordRepository;

    public String performLogin(String loginUrl, LoginRequest loginRequest) {
        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7");
        headers.set("Accept-Encoding", "gzip, deflate, br, zstd");
        headers.set("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7");
        headers.set("Connection", "keep-alive");
        headers.set("Content-Type", "application/x-www-form-urlencoded");
        headers.set("Cookie", "_ga_RMSHZ2FNH3=GS1.1.1693495546.3.0.1693495549.57.0.0; _ga_D20ZX1HSJG=GS1.3.1693891881.1.0.1693891881.60.0.0; _ga_0SZY8E4NNB=GS1.1.1696479023.42.0.1696479025.0.0.0; _ga_7LE8H40GNV=GS1.1.1710590208.23.0.1710590210.0.0.0; SCOUTER=x2284mnme44kc6; _ga_F9KPJB27JW=GS1.1.1720804271.4.1.1720804319.0.0.0; _gid=GA1.3.1153342088.1725348059; _ga_CY65W88TEE=GS1.1.1725369540.680.0.1725369540.0.0.0; _ga_3H1CM3R9KZ=GS1.1.1725369541.682.0.1725369541.0.0.0; _ga=GA1.1.532234916.1686054641; JSESSIONID=\"rt0Vcg5MD4XvOARc6J8P0qC1rreCADzPd1LAdsW1.master:NON-HAKSA-1\"");
        headers.set("Host", "happydorm.sejong.ac.kr");
        headers.set("Origin", "https://happydorm.sejong.ac.kr");
        headers.set("Referer", "https://happydorm.sejong.ac.kr/00/0000.kmc");
        headers.set("Sec-Fetch-Dest", "frame");
        headers.set("Sec-Fetch-Mode", "navigate");
        headers.set("Sec-Fetch-Site", "same-origin");
        headers.set("Sec-Fetch-User", "?1");
        headers.set("Upgrade-Insecure-Requests", "1");
        headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/128.0.0.0 Safari/537.36");
        headers.set("sec-ch-ua", "\"Chromium\";v=\"128\", \"Not;A=Brand\";v=\"24\", \"Google Chrome\";v=\"128\"");
        headers.set("sec-ch-ua-mobile", "?0");
        headers.set("sec-ch-ua-platform", "\"Windows\"");

        // 폼 데이터 설정
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("admin_chk", "N");
        body.add("lf_locgbn", "SJ");
        body.add("id", loginRequest.getUserId().toString());
        body.add("pw", loginRequest.getPassword());

        // 요청 생성
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        log.info(request.toString());

        // 로그인 요청 보내기
        ResponseEntity<String> response = restTemplate.exchange(loginUrl, HttpMethod.POST, request, String.class);

        log.info(response.getBody());

        // 응답 확인
        if (response.getStatusCode().is2xxSuccessful()) {
            String responseBody = response.getBody();

            if (responseBody != null && responseBody.contains("아이디 또는 비밀번호가 일치하지않습니다.")) {
                return "로그인 실패 : 아이디 또는 비밀번호가 일치하지않습니다.";
            }

            // 세션 쿠키 추출
            String cookies = response.getHeaders().getFirst(HttpHeaders.SET_COOKIE);

            User user = userRepository.findByStudentId(loginRequest.getUserId());

            if (user == null) {
                user = new User(loginRequest.getUserId());
                userRepository.save(user);
            }

            // 로그인 기록 저장
            LoginRecord loginRecord = new LoginRecord(user, cookies, new Timestamp(System.currentTimeMillis()));
            loginRecordRepository.save(loginRecord);
            return loginRecord.toString();
        }else{
            return "로그인에 실패하였습니다";
        }
    }
}