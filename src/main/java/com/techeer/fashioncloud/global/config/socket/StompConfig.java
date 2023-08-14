//package com.techeer.fashioncloud.global.config.socket;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.simp.config.MessageBrokerRegistry;
//import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
//import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
//import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
//
//@Configuration
//@EnableWebSocketMessageBroker
//public class StompConfig implements WebSocketMessageBrokerConfigurer {
//
//
//    //SockJsServiceRegistration
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/weather")
//                .setAllowedOriginPatterns("*")
//                .withSockJS(); //socket지원하지 않는 브라우저
//    }
//
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry registry) {
//        registry.enableStompBrokerRelay("/queue/", "/topic/");
//        registry.setApplicationDestinationPrefixes("/app/"); //서버를 거쳐 가는 경우
//
//    }
//
//}
