//package com.techeer.fashioncloud.global.config;
//
//import com.rabbitmq.client.AMQP.Queue;
//import com.rabbitmq.client.Connection;
//import com.rabbitmq.client.ConnectionFactory;
//import lombok.RequiredArgsConstructor;
//import org.springframework.amqp.rabbit.annotation.EnableRabbit;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.io.IOException;
//import java.util.concurrent.TimeoutException;
//
//@Configuration
//@EnableRabbit
//@RequiredArgsConstructor
//public class RabbitMQConfig {
//
//    private static final String CHAT_QUEUE_NAME = "chat.queue";
//    private static final String CHAT_EXCHANGE_NAME = "chat.exchange";
//    private static final String ROUTING_KEY = "room.*";
//
//    @Value("${spring.rabbitmq.host}")
//    private String host;
//
//    @Value("${spring.rabbitmq.username}")
//    private String username;
//
//    @Value("${spring.rabbitmq.password}")
//    private String password;
//
//    @Value("${spring.rabbitmq.port}")
//    private int port;
//
//    @Bean
//    ConnectionFactory connectionFactory() {
//        ConnectionFactory factory = new ConnectionFactory();
//        factory.setHost(host);
//        factory.setUsername(username);
//        factory.setPassword(password);
//        factory.setPort(port);
//
//        return factory;
//    }
//
////    @Bean
////    Connection connection(ConnectionFactory factory) throws IOException, TimeoutException {
////        Connection connection = factory.newConnection();
////        return connection
////    }
//
//
//    @Bean
//    Queue queue() {
//        return new Queue(CHAT_QUEUE_NAME, true);
//    }
//}
