//package com.techeer.fashioncloud.global.config.socket;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.CloseStatus;
//import org.springframework.web.socket.WebSocketHandler;
//import org.springframework.web.socket.WebSocketMessage;
//import org.springframework.web.socket.WebSocketSession;
//
//
///*
//brokerRelay사용시 필요 없음
// */
//
//@Component
//@Slf4j
//public class SocketHandler implements WebSocketHandler {
//
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        log.info("new connection created, id: " + session.getId());
//    }
//
//    @Override
//    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
//        log.info("new message arrived to session:" + session.getId() + " | " + "uri: ");
//        session.sendMessage(message);
//    }
//
//    @Override
//    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
//        log.info("error occured!");
//    }
//
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
//        log.info("connection closed, id: " + session.getId());
//
//    }
//
//    @Override
//    public boolean supportsPartialMessages() {
//        return false;
//    }
//
//
//}
