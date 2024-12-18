package com.ict.camping.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
      registry.addEndpoint("/ws/chat")
              .setAllowedOrigins("http://localhost:3000") // Next.js URL
              .withSockJS();
  }

  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
      registry.enableSimpleBroker("/topic"); // 메시지 브로커 경로
      registry.setApplicationDestinationPrefixes("/app"); // 클라이언트 메시지 전송 경로
  }
}
