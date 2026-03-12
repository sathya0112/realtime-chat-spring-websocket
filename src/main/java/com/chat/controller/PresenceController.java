package com.chat.controller;

import com.chat.dto.UserDto;
import com.chat.service.PresenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/presence")
@CrossOrigin(origins = "*")
public class PresenceController {
    
    @Autowired
    private PresenceService presenceService;
    
    @GetMapping("/room/{roomId}/online")
    public ResponseEntity<List<UserDto>> getOnlineUsersInRoom(@PathVariable Long roomId) {
        List<UserDto> users = presenceService.getOnlineUsersInRoom(roomId);
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/room/{roomId}/members")
    public ResponseEntity<List<UserDto>> getRoomMembers(@PathVariable Long roomId) {
        List<UserDto> users = presenceService.getRoomMembers(roomId);
        return ResponseEntity.ok(users);
    }
}

