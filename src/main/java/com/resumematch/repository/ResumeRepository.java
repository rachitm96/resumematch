package com.resumematch.repository;

import com.resumematch.entity.Resume;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ResumeRepository {
    private final Map<String, Resume> nameToResume = new ConcurrentHashMap<>();

    public Resume save(Resume resume) {
        if (resume.getCandidateName() == null) {
            throw new IllegalArgumentException("candidateName is required as key");
        }
        nameToResume.put(resume.getCandidateName().toLowerCase(Locale.ROOT), resume);
        return resume;
    }

    public Optional<Resume> findByName(String name) {
        if (name == null) return Optional.empty();
        return Optional.ofNullable(nameToResume.get(name.toLowerCase(Locale.ROOT)));
    }

    public List<Resume> findAll() {
        return new ArrayList<>(nameToResume.values());
    }
}