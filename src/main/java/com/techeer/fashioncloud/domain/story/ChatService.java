package com.techeer.fashioncloud.domain.story;

import com.techeer.fashioncloud.domain.story.model.ChatMessage;
import com.techeer.fashioncloud.domain.user.entity.Follow;
import com.techeer.fashioncloud.domain.user.entity.User;
import com.techeer.fashioncloud.domain.user.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final SimpMessageSendingOperations messagingTemplate;
    private final FollowRepository followRepository;

    public void sendMessage(ChatMessage message, User sender) {
        List<Follow> followers = followRepository.findByToUser_Id(sender.getId());
        for (Follow follower : followers) {
            messagingTemplate.convertAndSend("/sub/chat/room/" + follower.getFromUser().getId(), message);
        }
    }
}
