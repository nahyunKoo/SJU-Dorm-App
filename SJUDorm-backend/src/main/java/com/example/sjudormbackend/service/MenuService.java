package com.example.sjudormbackend.service;

import com.example.sjudormbackend.domain.Menu;
import com.example.sjudormbackend.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    public Menu getMenuByDate(String date){
        return menuRepository.findByDate(date);
    }
}
