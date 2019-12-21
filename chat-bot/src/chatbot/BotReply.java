package chatbot;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import chatbot.ChatBot.ReplyType;
import chatbot.GibbetGame.TurnResult;
 
public class BotReply {
	public String guessedWord;
	public int wrongGuesses;
	public ArrayList<ReplyType> replyTypes;
	public TurnResult turnResult;
	public String[] availableOperations;
	public String photo;
	public Timer timer;
	public TimerTask task;
	public long delay;
	
	public BotReply(String guessedWord, ArrayList<ReplyType> replyTypes, 
			TurnResult turnResult, int wrongGuesses, String[] availableOperations, 
			String photo, Timer timer, TimerTask task, long delay) {
		this.guessedWord = guessedWord;
		this.replyTypes = replyTypes;
		this.turnResult = turnResult;
		this.wrongGuesses = wrongGuesses;
		this.availableOperations = availableOperations;
		this.photo = photo;
		this.timer = timer;
		this.task = task;
		this.delay = delay;
	}
}
