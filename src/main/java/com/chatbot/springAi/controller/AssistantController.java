package com.chatbot.springAi.controller;

import com.chatbot.springAi.service.EmbeddingService;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chatbot")
public class AssistantController {

    @Autowired
    private OpenAiChatClient openAiChatClient;

    @Autowired
    private EmbeddingService embeddingService;

    @GetMapping("/chat")
    public ChatResponse sendMessage(@RequestParam(value = "message", defaultValue = "Conte me uma piada!") String message) {
        return openAiChatClient.call(new Prompt(message));
    }

    @PostMapping("/file")
    public ResponseEntity<Object> loadFile() throws Exception {
        embeddingService.load();
        return ResponseEntity.status(200).body("File loaded successfully");
    }

}
