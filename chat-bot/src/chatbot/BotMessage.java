package chatbot;

import java.util.HashMap;

public class BotMessage {
	public String text;
	public String photoName;
	public HashMap<String, String> availableOperations;
	public String hint;
	public boolean gameIsStarted;
	public boolean gameIsEnded;
	
	public BotMessage(String text, String photoName, 
			HashMap<String, String> availableOperations, String hint, 
			boolean gameIsStarted, boolean gameIsEnded) {
		this.text = text;
		this.photoName = photoName;
		this.availableOperations = availableOperations;
		this.hint = hint;
		this.gameIsStarted = gameIsStarted;
		this.gameIsEnded = gameIsEnded;
	}
}
