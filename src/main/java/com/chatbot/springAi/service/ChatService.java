package com.chatbot.springAi.service;

import com.chatbot.springAi.repository.PGVectorRepository;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.Generation;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ChatService {

    @Autowired
    private OpenAiChatClient openAiChatClient;

    @Autowired
    private PGVectorRepository pgVectorRepository;

    private static final String DEFAULT_PROMPT_TEMPLATE = """
        Você responde perguntas sobre o regulamento de estágios de uma universidade.
        O usuario acessou o site da universidade buscando informações sobre o regulamento.
        Use o conteúdo do regulamento abaixo para responder as perguntas do usuario.
        Se a resposta não for encontrada no regulamento, responda que você não sabe, não tente inventar uma resposta.

        Regulamento:
        {information}
        
        Pergunta:
        {question}
    """;

    public Generation sendMessage(String message) {
        List<Document> results = pgVectorRepository.searchSimilarity(SearchRequest.query(message).withTopK(2));

        PromptTemplate promptTemplate = new PromptTemplate(DEFAULT_PROMPT_TEMPLATE, Map.of("information", getDocumentInformationMessage(results),"question",message));
        Prompt prompt = promptTemplate.create();

        return openAiChatClient.call(prompt).getResult();
    }

    private String getDocumentInformationMessage(List<Document> results) {
        return results.stream().map(Document::getContent).reduce("", String::concat);
    }
}