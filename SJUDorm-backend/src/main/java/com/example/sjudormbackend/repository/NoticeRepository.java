package com.example.sjudormbackend.repository;

import com.example.sjudormbackend.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}