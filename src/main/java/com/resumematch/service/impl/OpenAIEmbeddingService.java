package com.resumematch.service.impl;

import com.resumematch.service.EmbeddingService;
import com.resumematch.model.EmbeddingResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;

@Service
public class OpenAIEmbeddingService implements EmbeddingService {

    private final WebClient webClient;

    public OpenAIEmbeddingService(@Value("${openai.api.key}") String apiKey) {
        this.webClient = WebClient.builder()
                .baseUrl("https://api.openai.com/v1/embeddings")
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .build();
    }

    @Override
    public float[] embed(String text) {
        EmbeddingResponse response = webClient.post()
                .bodyValue(new EmbeddingRequest("text-embedding-3-small", text))
                .retrieve()
                .bodyToMono(EmbeddingResponse.class)
                .block();

        List<Float> embeddingList = response.data().get(0).embedding();
        float[] embeddingArray = new float[embeddingList.size()];
        for (int i = 0; i < embeddingList.size(); i++) {
            embeddingArray[i] = embeddingList.get(i);
        }
        return embeddingArray;
    }
    record EmbeddingRequest(String model, String input) {}
}
