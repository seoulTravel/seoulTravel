package com.dongyang.seoulTravel.controller.gpt;

import com.dongyang.seoulTravel.service.gpt.OpenAiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class OpenAiController {

    private final OpenAiService openAiService;

    public OpenAiController(OpenAiService openAiService) {
        this.openAiService = openAiService;
    }

    // 사용자가 입력 -->> 바탕으로 ChatGPT에 요청
    @GetMapping("/ask-chatgpt")
    public Mono<String> askChatGpt(@RequestParam String question) {
        // 사용자 입력 -> ChatGPT API로 전달하고, 응답을 받음 ..
        return openAiService.getChatGptResponse(question);
    }
}

