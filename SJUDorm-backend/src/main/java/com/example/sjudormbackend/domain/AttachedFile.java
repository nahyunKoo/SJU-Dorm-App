package com.example.sjudormbackend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "attached_files")
@AllArgsConstructor
public class AttachedFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attached_files_id", nullable = false)
    private Long id;

    @Column(name = "attached_files_name", nullable = false)
    private String fileName;

    @Column(name = "attached_files_url", nullable = false)
    private String url;

    /*@Column(name = "attached_files_size", nullable = false)
    private long size;*/

    @ManyToOne
    @JoinColumn(name = "notice_id")
    private Notice notice;

    public AttachedFile(String fileUrl, String fileName) {
        this.fileName = fileName;
        this.url = fileUrl;
    }
}
