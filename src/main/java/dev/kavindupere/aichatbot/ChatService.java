package dev.kavindupere.aichatbot;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

public interface ChatService {

    @SystemMessage("""
        You are an expert at routing a user question to a vectorstore or web search.
        The vectorstore contains documents related to "How to create different types of orders at KSP Publications".
        Use the vectorstore for questions on this topic. For all else, and especially for current events, use web-search.
        Return JSON with two keys, datasource, that is 'websearch' or 'vectorstore' depending on the question,
        and content, that is the response from the datasource.
        """)
    String chat(@MemoryId String memoryId, @UserMessage String question);

}
