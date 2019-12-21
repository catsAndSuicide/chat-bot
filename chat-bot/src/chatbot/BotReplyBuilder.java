package chatbot;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import chatbot.ChatBot.ReplyType;
import chatbot.GibbetGame.TurnResult;

public class BotReplyBuilder {
	private String guessedWord;
	private ArrayList<ReplyType> replyTypes;
	private TurnResult turnResult;
	private int wrongGuesses;
	private String[] availableOperations;
	private String photo;
	private Timer timer;
	private TimerTask task;
	private long delay;
	
	public BotReplyBuilder() {
		guessedWord = "";
		replyTypes = new ArrayList<ReplyType>();
		turnResult = null;
		wrongGuesses = 0;
		availableOperations = null;
		photo = null;
		timer = null;
		task = null;
		delay = 0;
	}
	
	public void setGuessedWord(String guessedWord) {
		this.guessedWord = guessedWord;
	}
	
	public void addReplyType(ReplyType newReplyType) {
		replyTypes.add(newReplyType);
	}
	
	public void setTurnResult(TurnResult turnResult) {
		this.turnResult = turnResult;
	}
	
	public void setWrongGuesses(int wrongGuesses) {
		this.wrongGuesses = wrongGuesses;
	}
	
	public void setAvailableOperations(String[] availableOperations) {
		this.availableOperations = availableOperations;
	}
	
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	public void setTimer(Timer timer, TimerTask task, long delay) {
		this.timer = timer;
		this.delay = delay;
		this.task = task;
	}
	
	public BotReply buildReply() {
		return new BotReply(guessedWord, replyTypes, turnResult, wrongGuesses, availableOperations, photo, timer, task, delay);
	}
}
