package com.chatbot.springAi.repository;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.vectorstore.PgVectorStore;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PGVectorRepository {

    private VectorStore  vectorStore;

    @Autowired
    public PGVectorRepository(EmbeddingClient embeddingClient, JdbcTemplate jdbcTemplate, EmbeddingClient embeddingClient1, JdbcTemplate jdbcTemplate1) {
        this.vectorStore = new PgVectorStore(jdbcTemplate, embeddingClient);
    }

    public void add(List<Document> documentList) {
        vectorStore.add(documentList);
    }

    public List<Document> searchSimilarity(SearchRequest searchRequest) {
        return vectorStore.similaritySearch(searchRequest);
    }
}
