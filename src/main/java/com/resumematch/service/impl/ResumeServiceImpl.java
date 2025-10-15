package com.resumematch.service.impl;

import com.resumematch.entity.Resume;
import com.resumematch.service.EmbeddingService;
import com.resumematch.service.ResumeService;
import com.resumematch.util.TextExtractor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.UUID;

@Service
public class ResumeServiceImpl implements ResumeService {

    private final EmbeddingService embeddingService;
    private final TextExtractor textExtractor;

    public ResumeServiceImpl(EmbeddingService embeddingService, TextExtractor textExtractor) {
        this.embeddingService = embeddingService;
        this.textExtractor = textExtractor;
    }

    @Override
    public Resume uploadResume(MultipartFile file, String name, String email) {
        String text = textExtractor.extract(file);
        float[] vector = embeddingService.embed(text);

        Resume resume = new Resume();
        resume.setId(UUID.randomUUID());
        resume.setCandidateName(name);
        resume.setEmail(email);
        resume.setRawText(text);
        resume.setEmbedding(vector);
        resume.setCreatedAt(Instant.now());

        // Mock save - in a real implementation, this would save to database
        System.out.println("Resume saved: " + resume.getId());
        
        return resume;
    }
}
