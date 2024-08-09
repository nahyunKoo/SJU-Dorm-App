package com.example.sjudormbackend.service;

import com.example.sjudormbackend.domain.AttachedFile;
import com.example.sjudormbackend.repository.AttachedFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FileService {
    @Autowired
    private AttachedFileRepository attachedFileRepository;

    // 1. 엔티티 저장
    public AttachedFile saveFile(AttachedFile fileEntity) {
        return attachedFileRepository.save(fileEntity);
    }

    // 2. 엔티티 조회
    public Optional<AttachedFile> getFileById(Long id) {
        return attachedFileRepository.findById(id);
    }

    // 3. 모든 엔티티 조회
    public List<AttachedFile> getAllFiles() {
        return attachedFileRepository.findAll();
    }
    // 7. 엔티티 삭제
    public void deleteFile(Long id) {
        attachedFileRepository.deleteById(id);
    }

    // 8. 여러 엔티티 삭제
    public void deleteAllFiles() {
        attachedFileRepository.deleteAll();
    }

    // 9. 엔티티 수 카운트
    public long countFiles() {
        return attachedFileRepository.count();
    }
}
