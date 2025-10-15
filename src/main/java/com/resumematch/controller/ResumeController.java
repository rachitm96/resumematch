package com.resumematch.controller;

import com.resumematch.dto.ResumeRequest;
import com.resumematch.entity.Resume;
import com.resumematch.service.ResumeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/resumes")
public class ResumeController {

    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @PostMapping
    public ResponseEntity<Resume> uploadResume(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam("email") String email
    ) {
        Resume resume = resumeService.uploadResume(file, name, email);
        return ResponseEntity.ok(resume);
    }
}