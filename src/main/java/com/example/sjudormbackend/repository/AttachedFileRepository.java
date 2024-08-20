package com.example.sjudormbackend.repository;

import com.example.sjudormbackend.domain.AttachedFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AttachedFileRepository extends JpaRepository<AttachedFile, Long> {
    Optional<List<AttachedFile>> findByNotice_NoticeId(Long noticeId);
}
