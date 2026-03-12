# 💬 Real-Time Multi-Room Chat Application

[![Java](https://img.shields.io/badge/Java-17+-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![WebSocket](https://img.shields.io/badge/WebSocket-STOMP-blue.svg)](https://stomp.github.io/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0+-blue.svg)](https://www.mysql.com/)

A production-ready real-time chat application built with **Spring Boot** and **WebSocket**, featuring JWT authentication, multi-room support, and real-time messaging capabilities.

## 🎯 What This Project Demonstrates

- **Real-time Communication**: WebSocket (STOMP) for instant bidirectional messaging
- **Security**: JWT authentication, BCrypt password hashing, role-based access control
- **Database Design**: JPA entities with proper relationships and indexing
- **RESTful APIs**: Clean API design for room and message management
- **Scalable Architecture**: Stateless design ready for horizontal scaling

## ✨ Key Features

- 🔐 **JWT Authentication** - Secure user registration and login
- 💬 **Real-Time Messaging** - Instant message delivery via WebSocket (STOMP)
- 🏠 **Multi-Room Support** - Create and join multiple chat rooms
- 📝 **Typing Indicators** - Live "user is typing..." notifications
- 👥 **Online Presence** - Track active users in real-time
- 💾 **Message Persistence** - Complete chat history with pagination
- 🛡️ **Role-Based Access** - USER, MODERATOR, and ADMIN roles

## 🛠️ Technology Stack

- **Backend**: Spring Boot 3.4.0, Spring Security, Spring Data JPA
- **Real-Time**: WebSocket wit- **Real-Time**: WebSocket wit- **Real-Time**: WebSocket wit- **Real-Time**: WebSoc**: Redis (optional)
- **Security**: JWT tokens, BCrypt
- **Build**: Maven, Java 17

## 🚀 Quick Start

### Prerequisites
- Java 17+
- Maven 3.6+
- MySQL 8.0+ (or use H2 for local development)
- Docker (optional)

### Run with Docker (Recommended)

```bash
# Start MySQL and Redis
docker-compose up -d

# Build and run the application
mvn clean install
mvn spring-boot:run
```

### Run Locally (Without Docker)

```bash
# Clone the repository
git clone https://github.com/yougit clone https://github.com/yougit clone https://github.com/youginggit clone https://github.com/yougit clone https://github.com/yougit Acgit clone https://github.com/yougit clone https://github.coient: http://localhost:8080/test-client.html
```

## 📡 API Overview

##################################################################rname": "john_doe",
  "email": "  "email": "  "email": "  "email": "  "email": "  "email": "  "emhn  "email": "  "email": "  "email": "  "email": "  "email": "  "email": "  "emhn  "email": "  "email": "  "email": "  "email": "  "emublic` - Get all public rooms
- `POST /rooms` - Create a new room
- `POST /rooms/{roomI- `POST /rooms/ a - `POST /ET- `POST /roro- `POST /rooms/{roomI- `POST /rooms/ a - `POSet- `POST /rooms/{roript
// Connect to WebSocket
const socket = neconst socket = neconslhoconst socket = neco stompClient = Stomp.over(socket);
const socket = n room messages
stompClient.subscribe('/topic/room/1', (messagstompC{
stompClient.subscribessage:', JSON.parse(message.body));
});

// Send a message
stompClient.send('/app/chat.send', {}, JSON.stringify({
  roomId: 1,
  content: "Hello!",
  type: 'CHAT'
}));
```

## 🗂️ Project Structure

```
src/main/java/com/chat/
├── config/          # WebSocket, Security, JWT configuration
├├├├├├├├├├├├├├├├├├├├├├├├├├├�  # Bu├├├├├├├├├├├├├├├├├├├├├├├├├├├�  # Bu├├├├├├├├├├├├├├├├├├├├├├├├├├├�  # Bu├├├├├├├├├├├├├├├├├├├├�, ├├├├├├├├├├├�ers can create and join multiple rooms
- Each room contains messages from multiple users
- RoomMembers tracks user membership and activity
- All passwords are BCrypt hashed
- Supports role-based access (USER, MODERATOR, ADMIN)

## 📄 License

This projThis projThis projThis projTh LThis projThis projThis projThis projTh LThis projThi.
