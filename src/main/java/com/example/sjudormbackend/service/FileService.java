package com.example.sjudormbackend.service;

import com.example.sjudormbackend.domain.AttachedFile;
import com.example.sjudormbackend.dto.AttachedFileDTO;
import com.example.sjudormbackend.repository.AttachedFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public Optional<List<AttachedFile>> getFilesByNoticeId(Long noticeId) {
        return attachedFileRepository.findByNotice_NoticeId(noticeId);
    }

    public Optional<List<AttachedFileDTO>> convertToAttachedFileDTOs(Optional<List<AttachedFile>> attachedFiles) {
        // Optional이 비어있는지 확인하고 비어있지 않다면 변환 작업 수행
        return attachedFiles.map(files ->
                files.stream()
                        .map(file -> new AttachedFileDTO(file.getId(), file.getFileName(), file.getUrl()))
                        .collect(Collectors.toList())
        );
    }
}
