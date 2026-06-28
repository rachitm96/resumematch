package com.resumematch.util;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class TextExtractor {
    private final Tika tika = new Tika();

    public String extract(MultipartFile file) {
        try {
            String text = tika.parseToString(file.getInputStream());
            return text == null ? "" : text;
        } catch (IOException | TikaException e) {
            throw new RuntimeException("Failed to parse file: " + e.getMessage());
        }
    }
}