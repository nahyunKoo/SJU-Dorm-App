package com.example.sjudormbackend.repository;

import com.example.sjudormbackend.domain.Menu;
import org.aspectj.apache.bcel.util.Repository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MenuRepository extends CrudRepository<Menu, Integer> {
    Menu findByDate(String date);
    List<Menu> findAll();
}
