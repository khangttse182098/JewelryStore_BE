package com.swp.jewelrystore.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface IStorageService {
    String uploadFile(MultipartFile file);
    byte[] downloadFile(String fileName);
    String deleteFile(String fileName);
}
