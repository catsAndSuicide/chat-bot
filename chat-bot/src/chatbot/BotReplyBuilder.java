package chatbot;

import java.util.ArrayList;

import chatbot.ChatBot.ReplyType;
import chatbot.GibbetGame.TurnResult;

public class BotReplyBuilder {
	private String guessedWord;
	private ArrayList<ReplyType> replyTypes;
	private TurnResult turnResult;
	private int wrongGuesses;
	private String[] availableOperations;
	
	public BotReplyBuilder() {
		guessedWord = "";
		replyTypes = new ArrayList<ReplyType>();
		turnResult = null;
		wrongGuesses = 0;
		availableOperations = null;
	}
	
	public void setGuessedWord(String word) {
		guessedWord = word;
	}
	
	public void addReplyType(ReplyType newReplyType) {
		replyTypes.add(newReplyType);
	}
	
	public void setTurnResult(TurnResult newTurnResult) {
		turnResult = newTurnResult;
	}
	
	public void setWrongGuesses(int count) {
		wrongGuesses = count;
	}
	
	public void setAvailableOperations(String[] operations) {
		availableOperations = operations;
	}
	
	public BotReply buildReply() {
		return new BotReply(guessedWord, replyTypes, turnResult, wrongGuesses, availableOperations);
	}
}
