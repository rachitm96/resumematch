package com.resumematch.service.impl;

import com.resumematch.entity.Resume;
import com.resumematch.repository.ResumeRepository;
import com.resumematch.service.ResumeService;
import com.resumematch.util.ResumeParser;
import com.resumematch.util.TextExtractor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.*;

@Service
public class ResumeServiceImpl implements ResumeService {

    private final TextExtractor textExtractor;
    private final ResumeParser resumeParser;
    private final ResumeRepository resumeRepository;

    public ResumeServiceImpl(TextExtractor textExtractor, ResumeParser resumeParser, ResumeRepository resumeRepository) {
        this.textExtractor = textExtractor;
        this.resumeParser = resumeParser;
        this.resumeRepository = resumeRepository;
    }

    @Override
    public Resume uploadResume(MultipartFile file) {
        String text = textExtractor.extract(file);

        Resume resume = new Resume();
        resume.setId(UUID.randomUUID());
        resume.setRawText(text);
        resume.setCreatedAt(Instant.now());

        String name = resumeParser.extractName(text);
        String email = resumeParser.extractEmail(text);
        String phone = resumeParser.extractPhone(text);
        String company = resumeParser.extractCurrentCompany(text);
        List<String> skills = resumeParser.extractTopSkills(text, 5);

        if (name != null) resume.setCandidateName(name);
        if (email != null) resume.setEmail(email);
        if (phone != null) resume.setPhone(phone);
        if (company != null) resume.setCurrentCompany(company);
        resume.setSkills(skills);

        return resumeRepository.save(resume);
    }

    @Override
    public List<Resume> getAll() {
        return resumeRepository.findAll();
    }

    @Override
    public Optional<Resume> getByName(String name) {
        return resumeRepository.findByName(name);
    }

    @Override
    public double compatibilityScore(String jobDescription, MultipartFile resumePdf) {
        String resumeText = textExtractor.extract(resumePdf);
        List<String> resumeSkills = resumeParser.extractTopSkills(resumeText, 20);
        Set<String> resumeSkillSet = new HashSet<>(resumeSkills);

        List<String> jdSkills = resumeParser.extractTopSkills(jobDescription == null ? "" : jobDescription, 50);
        if (jdSkills.isEmpty() || resumeSkillSet.isEmpty()) return 0.0;

        int matches = 0;
        for (String s : jdSkills) {
            if (resumeSkillSet.contains(s)) matches++;
        }
        double score = (double) matches / (double) jdSkills.size();
        return Math.round(score * 1000.0) / 10.0; // percentage with 1 decimal
    }
}
