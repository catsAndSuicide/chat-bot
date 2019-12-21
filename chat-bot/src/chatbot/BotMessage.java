package chatbot;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class BotMessage {
	public String text;
	public String photoName;
	public HashMap<String, String> availableOperations;
	public String photo;
	public Timer timer;
	public TimerTask task;
	public long delay;
	
	public BotMessage(String text, String photoName, 
			HashMap<String, String> availableOperations, String photo, 
			boolean gameIsStarted, boolean gameIsEnded, Timer timer, TimerTask task, long delay) {
		this.text = text;
		this.photoName = photoName;
		this.availableOperations = availableOperations;
		this.photo = photo;
		this.timer = timer;
		this.task = task;
		this.delay = delay;
	}
}
