package com.techeer.fashioncloud.domain.story.model;

import com.techeer.fashioncloud.domain.post.dto.response.PostCreateResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChatMessage {

    // 메시지 타입 : 입장, 채팅
//    public enum MessageType {
//        ENTER, TALK
//    }


    private String roomId; // 방번호 /email
    private String sender; // 메시지 보낸사람
    private PostCreateResponseDto message; // 메시지
}
