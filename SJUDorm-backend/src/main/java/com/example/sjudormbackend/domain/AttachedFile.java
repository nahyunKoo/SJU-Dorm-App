package com.example.sjudormbackend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "attatched_files")
public class AttachedFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attached_files_id", nullable = false)
    private Long id;

    @Column(name = "attached_files_name", nullable = false)
    private String fileName;

    @Column(name = "attached_files_path", nullable = false)
    private String path;

    @Column(name = "attached_files_size", nullable = false)
    private long size;

    @ManyToOne
    private Notice notice;

    public AttachedFile(String fileName, String savePath, long length) {
        this.fileName = fileName;
        this.path = savePath;
        this.size = length;
    }
}
