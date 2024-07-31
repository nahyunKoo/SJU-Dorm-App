package com.example.sjudormbackend.repository;

import com.example.sjudormbackend.domain.Menu;
import org.aspectj.apache.bcel.util.Repository;
import org.springframework.data.repository.CrudRepository;

public interface MenuRepository extends CrudRepository<Menu, Integer> {
    Menu findByDate(String date);
}
