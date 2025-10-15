package com.resumematch.util;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Component
public class TextExtractor {
    private final Tika tika = new Tika();

    public String extract(MultipartFile file) {
        try {
            return tika.parseToString(file.getInputStream());
        } catch (IOException | TikaException e) {
            throw new RuntimeException("Failed to parse file: " + e.getMessage());
        }
    }
}