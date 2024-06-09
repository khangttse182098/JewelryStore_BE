package com.swp.jewelrystore.api;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.swp.jewelrystore.service.IStorageService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@RestController
@RequestMapping("/api/file")
public class StorageAPI {
    @Autowired
    private IStorageService service;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam(value = "file") MultipartFile file) {
        return new ResponseEntity<>(service.uploadFile(file), HttpStatus.OK);
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName) {
        byte[] data = service.downloadFile(fileName);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE) // Set content type to image/jpeg
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }

    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
        return new ResponseEntity<>(service.deleteFile(fileName), HttpStatus.OK);
    }
}
