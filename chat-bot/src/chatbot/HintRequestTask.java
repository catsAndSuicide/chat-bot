package chatbot;

import java.util.TimerTask;

public class HintRequestTask extends TimerTask {
	private Bot bot;
	private String id;
	
	public HintRequestTask(String id, Bot bot) {
		this.id = id;
		this.bot = bot;
	}

	public void run() {
		bot.replyToMessage("/hint", id);
	}

}
