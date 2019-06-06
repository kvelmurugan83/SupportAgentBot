package com.cts.de.bot.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.cts.de.bot.agent.DetectIndentService;
import com.cts.de.bot.model.ChatMessage;

@Controller
public class WebSocketController {
	
	private static final Logger logger = LoggerFactory.getLogger(WebSocketController.class);
	
	@Autowired
    private SimpMessagingTemplate messageTemplate;

	@Autowired
	private DetectIndentService service;
	
	@MessageMapping("/chat.send")
	public void chat(@Payload ChatMessage chatMessage, Principal principal) throws Exception {
		logger.debug("Chat {}", chatMessage.getContent());
		ChatMessage response = this.service.detectIndentText(principal.getName(), chatMessage);
		this.messageTemplate.convertAndSend("/user/" + principal.getName() + "/exchange/amq.direct/chat.message", response);
	}
	
	@MessageMapping("/chat.connect")
	public void connect(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
		logger.debug("connect {}", chatMessage.getContent());
	}
}
