package chatbot;

import java.util.ArrayList;

import chatbot.ChatBot.ReplyType;
import chatbot.GibbetGame.TurnResult;
 
public class BotReply {
	public String guessedWord;
	public int wrongGuesses;
	public ArrayList<ReplyType> replyTypes;
	public TurnResult turnResult;
	public String[] availableOperations;
	
	public BotReply(String guessedWord, ArrayList<ReplyType> replyTypes, 
			TurnResult turnResult, int wrongGuesses, String[] availableOperations) {
		this.guessedWord = guessedWord;
		this.replyTypes = replyTypes;
		this.turnResult = turnResult;
		this.wrongGuesses = wrongGuesses;
		this.availableOperations = availableOperations;
	}
}
