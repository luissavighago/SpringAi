package com.chatbot.springAi.service;

import com.chatbot.springAi.repository.PGVectorRepository;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.document.Document;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {

    @Autowired
    private OpenAiChatClient openAiChatClient;

    @Autowired
    private PGVectorRepository pgVectorRepository;

    public ChatResponse sendMessage(String message) {
        List<Document> results = pgVectorRepository.searchSimilarity(SearchRequest.query(message).withTopK(2));

        return openAiChatClient.call(new Prompt(message));
    }
}
