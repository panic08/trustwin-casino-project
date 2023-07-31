package eu.panic.chatservice.template.controller;

import eu.panic.chatservice.template.payload.ChatSendMessageRequest;
import eu.panic.chatservice.template.payload.MessageMessage;
import eu.panic.chatservice.template.service.implement.ChatServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    public ChatController(ChatServiceImpl chatService) {
        this.chatService = chatService;
    }

    private final ChatServiceImpl chatService;
    @GetMapping("/getAll")
    private List<MessageMessage> getAllMessages(){
        return chatService.getAllMessages();
    }
    @PostMapping("/send")
    private void sendMessage(
            @RequestHeader String jwtToken,
            @RequestBody ChatSendMessageRequest chatSendMessageRequest
            ){
        chatService.sendMessage(jwtToken, chatSendMessageRequest);
    }
}
