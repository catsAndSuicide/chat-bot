package chatbot;

public class TelegramBotMessage {
	public String text;
	public String photoName;
	
	TelegramBotMessage(String text, String photoName) {
		this.text = text;
		this.photoName = photoName;
	}
}
