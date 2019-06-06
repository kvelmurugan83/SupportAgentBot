package com.cts.de.bot.agent;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cts.de.bot.model.ChatMessage;
import com.google.cloud.dialogflow.v2.DetectIntentResponse;
import com.google.cloud.dialogflow.v2.QueryInput;
import com.google.cloud.dialogflow.v2.QueryResult;
import com.google.cloud.dialogflow.v2.SessionName;
import com.google.cloud.dialogflow.v2.SessionsClient;
import com.google.cloud.dialogflow.v2.TextInput;
import com.google.cloud.dialogflow.v2.TextInput.Builder;

@Service
public class DetectIndentService {
	
	private static final Logger logger = LoggerFactory.getLogger(DetectIndentService.class);

	@Value( "${dialogflow.projectid}" )
	private String projectId;
	
	@Value( "${dialogflow.languagecode}" )
	private String languageCode;
	
	public ChatMessage detectIndentText(String sessionId, ChatMessage chatMessage) throws IOException {
		logger.debug("Detect Index {}, {} ", sessionId, chatMessage.getContent());
		try (SessionsClient sessionsClient = SessionsClient.create()) {
			SessionName session = SessionName.of(projectId, sessionId);
			 Builder textInput = TextInput.newBuilder().setText(chatMessage.getContent()).setLanguageCode(languageCode);
			 QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();
			 DetectIntentResponse response = sessionsClient.detectIntent(session, queryInput);
			 QueryResult queryResult = response.getQueryResult();
			 logger.debug("Google Response {} ", queryResult.getFulfillmentText());
			 return new ChatMessage("BotAgent",  queryResult.getFulfillmentText());
		}
	}
}
