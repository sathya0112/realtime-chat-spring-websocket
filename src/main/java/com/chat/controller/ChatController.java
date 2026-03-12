package com.chat.controller;

import com.chat.dto.ChatMessage;
import com.chat.dto.MessageDto;
import com.chat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class ChatController {
    
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    
    @Autowired
    private ChatService chatService;
    
    @MessageMapping("/chat.send")
    public void sendMessage(@Payload ChatMessage chatMessage, Principal principal) {
        try {
            // Save message to database
            MessageDto savedMessage = chatService.saveMessage(chatMessage, principal.getName());
            
            // Broadcast message to all subscribers of the room
            messagingTemplate.convertAndSend(
                "/topic/room/" + chatMessage.getRoomId(), 
                savedMessage
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @MessageMapping("/chat.join")
    public void joinRoom(@Payload ChatMessage chatMessage, Principal principal) {
        try {
            chatMessage.setType(com.chat.entity.Message.MessageType.JOIN);
            chatMessage.setContent(principal.getName() + " joined the room");
            chatMessage.setSenderName(principal.getName());
            
            // Save join message
            MessageDto savedMessage = chatService.saveMessage(chatMessage, principal.getName());
            
            // Broadcast join notification
            messagingTemplate.convertAndSend(
                "/topic/room/" + chatMessage.getRoomId(), 
                savedMessage
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @MessageMapping("/chat.leave")
    public void leaveRoom(@Payload ChatMessage chatMessage, Principal principal) {
        try {
            chatMessage.setType(com.chat.entity.Message.MessageType.LEAVE);
            chatMessage.setContent(principal.getName() + " left the room");
            chatMessage.setSenderName(principal.getName());
            
            // Save leave message
            MessageDto savedMessage = chatService.saveMessage(chatMessage, principal.getName());
            
            // Broadcast leave notification
            messagingTemplate.convertAndSend(
                "/topic/room/" + chatMessage.getRoomId(), 
                savedMessage
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

