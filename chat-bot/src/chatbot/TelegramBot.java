package chatbot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import java.util.Random;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramBot extends TelegramLongPollingBot{
	
	private ChatBot chatBot;
	
	public TelegramBot() {
		chatBot = new ChatBot(new GibbetGameFactory(new Random()));
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
		var answer = chatBot.reply(message);
		sendMsg(update.getMessage().getChatId().toString(), answer);
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
		return "BotUsername";
	}

	@Override
	public String getBotToken() { 
		return "BotToken"; 
	}
}
