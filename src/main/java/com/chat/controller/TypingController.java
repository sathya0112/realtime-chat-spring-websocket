package com.chat.controller;

import com.chat.dto.TypingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class TypingController {
    
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    
    @MessageMapping("/typing")
    public void handleTyping(@Payload TypingDto typingDto, Principal principal) {
        try {
            typingDto.setUsername(principal.getName());
            
            // Broadcast typing status to all subscribers of the room
            messagingTemplate.convertAndSend(
                "/topic/room/" + typingDto.getRoomId() + "/typing", 
                typingDto
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

