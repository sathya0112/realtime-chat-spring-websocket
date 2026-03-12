package com.chat.controller;

import com.chat.dto.CreateRoomRequest;
import com.chat.dto.RoomDto;
import com.chat.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
@CrossOrigin(origins = "*")
public class RoomController {
    
    @Autowired
    private RoomService roomService;
    
    @PostMapping
    public ResponseEntity<RoomDto> createRoom(@Valid @RequestBody CreateRoomRequest request, 
                                               Authentication authentication) {
        try {
            RoomDto room = roomService.createRoom(request, authentication.getName());
            return ResponseEntity.ok(room);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/public")
    public ResponseEntity<List<RoomDto>> getAllPublicRooms() {
        List<RoomDto> rooms = roomService.getAllPublicRooms();
        return ResponseEntity.ok(rooms);
    }
    
    @GetMapping("/my-rooms")
    public ResponseEntity<List<RoomDto>> getUserRooms(Authentication authentication) {
        List<RoomDto> rooms = roomService.getUserRooms(authentication.getName());
        return ResponseEntity.ok(rooms);
    }
    
    @GetMapping("/{roomId}")
    public ResponseEntity<RoomDto> getRoomById(@PathVariable Long roomId) {
        try {
            RoomDto room = roomService.getRoomById(roomId);
            return ResponseEntity.ok(room);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("/{roomId}/join")
    public ResponseEntity<Void> joinRoom(@PathVariable Long roomId, Authentication authentication) {
        try {
            roomService.joinRoom(roomId, authentication.getName());
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/{roomId}/leave")
    public ResponseEntity<Void> leaveRoom(@PathVariable Long roomId, Authentication authentication) {
        try {
            roomService.leaveRoom(roomId, authentication.getName());
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

