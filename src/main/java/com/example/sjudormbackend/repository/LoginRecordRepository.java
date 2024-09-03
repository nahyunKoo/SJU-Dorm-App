package com.example.sjudormbackend.repository;

import com.example.sjudormbackend.domain.LoginRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRecordRepository extends JpaRepository <LoginRecord, Long> {
}
