package chatbot;

import java.util.TimerTask;

public class HintRequestTask extends TimerTask {
	private TelegramBot telegramBot;
	private String id;
	
	public HintRequestTask(String id, TelegramBot telegramBot) {
		this.id = id;
		this.telegramBot = telegramBot;
	}

	public void run() {
		try {
			telegramBot.replyToMessage("/hint", id);
		}
		catch (Exception e) {
			System.out.printf("Error %1$s, id: %2$s, message: %3$s\n", e.toString(), id, "/hint");
		}
	}

}
