package com.cts.de.bot.model;

import java.io.Serializable;

public class ChatMessage implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private MessageType type;
	private String content;
	private String sender;
	
	public ChatMessage() {
		
	}
	
	public ChatMessage(String sender, String content) {
		this.sender = sender;
		this.content = content;
		this.type = MessageType.CHAT;
		
	}

	public enum MessageType {
		CHAT, JOIN, LEAVE
	}

	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}
}
