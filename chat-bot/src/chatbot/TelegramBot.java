package chatbot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.util.HashMap;
import java.util.Random;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramBot extends TelegramLongPollingBot{
	
	private HashMap<String, ChatBot> chatBots;
	private BotMessageMaker botMessage;
	
	public TelegramBot() {
		chatBots = new HashMap<String, ChatBot>();
		botMessage = new BotMessageMaker();
	}

	public static void main(String[] args) {
		ApiContextInitializer.init();
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

		try {
			telegramBotsApi.registerBot(new TelegramBot());
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onUpdateReceived(Update update) {
		var message = update.getMessage().getText();
		var id = update.getMessage().getChatId().toString();
		var answer = "";
		
		synchronized(chatBots) {
			if (!chatBots.containsKey(id)) {
				chatBots.put(id, new ChatBot(new GibbetGameFactory(new Random())));
			}
			answer = botMessage.getMessage(chatBots.get(id).reply(message));
		}
		sendMsg(id, answer);
	}

	private void sendMsg(String chatId, String answer) {
		SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(answer);
        
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
        	e.printStackTrace();
        }
	}

	@Override
	public String getBotUsername() {
		return System.getenv("BOT_USERNAME");
	}

	@Override
	public String getBotToken() { 
		return System.getenv("BOT_TOKEN"); 
	}
}