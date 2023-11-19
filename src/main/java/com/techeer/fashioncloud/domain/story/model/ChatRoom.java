package com.techeer.fashioncloud.domain.story.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoom {
    private String roomId;
    private String name;

    public static ChatRoom create(String email) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = email;
        chatRoom.name = email;
        return chatRoom;
    }
}
