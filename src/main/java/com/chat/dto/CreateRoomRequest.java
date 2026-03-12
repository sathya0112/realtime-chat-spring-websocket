package com.chat.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRoomRequest {
    
    @NotBlank(message = "Room name is required")
    private String name;
    
    private String description;
    
    @NotNull(message = "Privacy setting is required")
    private Boolean isPrivate = false;
    
    private Integer maxCapacity = 100;
}

