package dev.kavindupere.aichatbot;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.milvus.MilvusEmbeddingStore;

@Configuration
class ChatConfiguration {

    @Bean
    OllamaChatModel chatModel() {
        return OllamaChatModel
                .builder()
                .baseUrl("http://localhost:11434")
                .temperature(0.0)
                .modelName("llama3.2:3b-instruct-fp16")
                .build();
    }

    @Bean("chatModelJSONmode")
    OllamaChatModel chatModelJSONmode() {
        return OllamaChatModel
                .builder()
                .baseUrl("http://localhost:11434")
                .temperature(0.0)
                .modelName("llama3.2:3b-instruct-fp16")
                .topK(3)
                .format("json")
                .logRequests(true)
                .logResponses(true)
                .build();
    }

    @Bean
    EmbeddingModel embeddingModel() {
        return OllamaEmbeddingModel
                .builder()
                .baseUrl("http://localhost:11434")
                .modelName("nomic-embed-text:v1.5")
                .build();
    }

    @Bean
    EmbeddingStore<TextSegment> embeddingStore() {
        return MilvusEmbeddingStore
                .builder()
                .uri("http://localhost:19530")
                .collectionName("knowledge")
                .dimension(768)
                .build();
    }

    @Bean
    ContentRetriever contentRetriever(EmbeddingModel embeddingModel, EmbeddingStore<TextSegment> embeddingStore) {
        return EmbeddingStoreContentRetriever
                .builder()
                .embeddingModel(embeddingModel)
                .embeddingStore(embeddingStore)
                .build();
    }

    @Value("classpath:docs/information.txt")
    private Resource information;

    @Bean
    CommandLineRunner loader(EmbeddingModel embeddingModel, EmbeddingStore<TextSegment> embeddingStore) {
        return args -> {
            var document = FileSystemDocumentLoader.loadDocument(information.getFile().toPath());
            var splitter = DocumentSplitters.recursive(100, 0);
            var ingestor = EmbeddingStoreIngestor
                    .builder()
                    .documentSplitter(splitter)
                    .embeddingModel(embeddingModel)
                    .embeddingStore(embeddingStore)
                    .build();
            ingestor.ingest(document);
        };
    }

    @Bean
    ChatService chatService(@Qualifier("chatModelJSONmode") ChatLanguageModel chatLanguageModel) {
        return AiServices
                .builder(ChatService.class)
                .chatLanguageModel(chatLanguageModel)
                .chatMemoryProvider(chatId -> MessageWindowChatMemory
                        .builder()
                        .id(chatId).maxMessages(50)
                        .build())
                .build();

    }
}
