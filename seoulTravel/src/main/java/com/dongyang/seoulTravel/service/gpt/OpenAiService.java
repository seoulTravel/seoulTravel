package com.dongyang.seoulTravel.service.gpt;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class OpenAiService {

    private final WebClient webClient;

    // URL
    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";

    // OpenAI API Key -- 불러와야함
    private static final String API_KEY = "your_openai_api_key";

    public OpenAiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(OPENAI_API_URL).build();
    }

    // ChatGPT에 프롬프트를 전달하고 응답을 받는 메서드
    public Mono<String> getChatGptResponse(String prompt) {
        return webClient.post()
                .header("Authorization", "Bearer " + API_KEY)
                .header("Content-Type", "application/json")
                .bodyValue(buildRequestBody(prompt))
                .retrieve()
                .bodyToMono(String.class);
    }

    // ChatGPT API 요청의 Body 생성....
    private String buildRequestBody(String prompt) {
        return "{"
                + "\"model\": \"gpt-4\","
                + "\"messages\": [{\"role\": \"user\", \"content\": \"" + prompt + "\"}],"
                + "\"max_tokens\": 1000"
                + "}";
    }
}
