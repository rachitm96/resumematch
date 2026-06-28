package com.resumematch.controller;

import com.resumematch.entity.Resume;
import com.resumematch.service.ResumeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/resumes")
public class ResumeController {

    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Resume> uploadResume(
            @RequestParam("file") MultipartFile file
    ) {
        Resume resume = resumeService.uploadResume(file);
        return ResponseEntity.ok(resume);
    }

    @GetMapping
    public ResponseEntity<?> getAllOrByName(@RequestParam(value = "name", required = false) String name) {
        if (name == null || name.isBlank()) {
            List<Resume> all = resumeService.getAll();
            return ResponseEntity.ok(all);
        }
        Optional<Resume> found = resumeService.getByName(name);
        return found.<ResponseEntity<?>>map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/compatibility")
    public ResponseEntity<Double> compatibility(
            @RequestParam("jobDescription") String jobDescription,
            @RequestParam("file") MultipartFile resumePdf
    ) {
        double score = resumeService.compatibilityScore(jobDescription, resumePdf);
        return ResponseEntity.ok(score);
    }
}