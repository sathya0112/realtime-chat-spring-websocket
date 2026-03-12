package com.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypingDto {
    
    private Long roomId;
    private String username;
    private Boolean isTyping;
}

