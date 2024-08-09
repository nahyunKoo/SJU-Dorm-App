package com.example.sjudormbackend.repository;

import com.example.sjudormbackend.domain.AttachedFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachedFileRepository extends JpaRepository<AttachedFile, Long> {
}
