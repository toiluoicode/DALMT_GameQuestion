package com.example.demo.WebSocketConfig;

import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker (MessageBrokerRegistry config){

    }
    @Override
    public void registerStompEndpoints (StompEndpointRegistry registry){
        registry.addEndpoint("ws").withSockJS();
    }
}
