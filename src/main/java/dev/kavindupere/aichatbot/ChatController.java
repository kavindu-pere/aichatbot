package dev.kavindupere.aichatbot;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequiredArgsConstructor
class ChatController {

    private final ChatService chatService;
    
    @GetMapping("/chat")
    public String getMethodName(@PathVariable String chatId, @RequestParam String prompt) {
        return chatService.chat(chatId, prompt);
    }    

}
