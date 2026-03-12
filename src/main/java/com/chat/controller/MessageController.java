package com.chat.controller;

import com.chat.dto.MessageDto;
import com.chat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
@CrossOrigin(origins = "*")
public class MessageController {
    
    @Autowired
    private ChatService chatService;
    
    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<MessageDto>> getRoomMessages(
            @PathVariable Long roomId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        
        List<MessageDto> messages = chatService.getRoomMessages(roomId, page, size);
        return ResponseEntity.ok(messages);
    }
    
    @GetMapping("/room/{roomId}/all")
    public ResponseEntity<List<MessageDto>> getAllRoomMessages(@PathVariable Long roomId) {
        List<MessageDto> messages = chatService.getAllRoomMessages(roomId);
        return ResponseEntity.ok(messages);
    }
    
    @DeleteMapping("/{messageId}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long messageId, Authentication authentication) {
        try {
            chatService.deleteMessage(messageId, authentication.getName());
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

