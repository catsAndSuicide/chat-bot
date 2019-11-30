package chatbot;

import java.util.HashMap;

public class BotMessage {
	public String text;
	public String photoName;
	public HashMap<String, String> availableOperations;
	
	public BotMessage(String text, String photoName, HashMap<String, String> availableOperations) {
		this.text = text;
		this.photoName = photoName;
		this.availableOperations = availableOperations;
	}
}
