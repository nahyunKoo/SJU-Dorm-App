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

        //menu 크롤링 해서 db 에 save
        @PostMapping("/save")
        public ResponseEntity<String> saveMenu() {
            try {
                menuService.fetchAndSaveMenu("https://happydorm.sejong.ac.kr/");
                log.info("Menu data fetched and saved successfully.");
                return ResponseEntity.status(HttpStatus.CREATED).body("Menu data fetched and saved successfully.");
            } catch (IOException e) {
                // 크롤링 실패 시 예외 처리
                System.out.println(e.getMessage());
                log.error("IO Exception occurred while fetching menu data: {}", e.getMessage(), e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch menu data due to an IO error.");
            } catch (Exception e) {
                // 그 외의 모든 예외 처리
                System.out.println(e.getMessage());
                log.error("Error occurred while fetching and saving menu data: {}", e.getMessage(), e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching and saving menu data.");
            }
        }

    }
