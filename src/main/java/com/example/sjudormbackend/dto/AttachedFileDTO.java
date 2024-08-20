package com.example.sjudormbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AttachedFileDTO {
    private long attachedFileID;
    private String attachedFileName;
    private String attachedFileUrl;
}
