package chatbot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class TelegramBot extends TelegramLongPollingBot{
	
	private HashMap<String, ChatBot> chatBots;
	private TelegramBotMessageMaker botMessage;
	
	public TelegramBot() {
		chatBots = new HashMap<String, ChatBot>();
		botMessage = new TelegramBotMessageMaker();
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
		var message = "";
		var id = "";
		TelegramBotMessage answer = null;
		
		if (update.hasMessage() && update.getMessage().hasText()){
			message = update.getMessage().getText();
			id = update.getMessage().getChatId().toString();
		}
		
		else if (update.hasCallbackQuery()) {
			message = update.getCallbackQuery().getData();
			id = update.getCallbackQuery().getMessage().getChatId().toString();
		}
		
		synchronized(chatBots) {
			if (!chatBots.containsKey(id)) {
				chatBots.put(id, new ChatBot(new GibbetGameFactory(new Random())));
			}
			answer = botMessage.getMessage(chatBots.get(id).reply(message));
		}
		sendMsg(id, answer.text);
		if (answer.photoName != null)
			sendPht(id, answer.photoName);
	}
	
	private void sendPht(String chatId, String photoName) {
		SendPhoto sendPhoto = new SendPhoto();
		sendPhoto.setChatId(chatId);
		sendPhoto.setPhoto(new File(System.getProperty("user.dir") , photoName));
		
		try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
        	e.printStackTrace();
        }
	}

	private void sendMsg(String chatId, String answer) {
		SendMessage sendMessage = new SendMessage();
		sendMessage.setChatId(chatId);
        sendMessage.setText(answer);
        //sendMessage.enableMarkdown(true);
        
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        
        keyboardButtonsRow.add(new InlineKeyboardButton().setText("start") 
                .setCallbackData("/start"));
        keyboardButtonsRow.add(new InlineKeyboardButton().setText("show") 
                .setCallbackData("/show"));
        keyboardButtonsRow.add(new InlineKeyboardButton().setText("help") 
                .setCallbackData("/help"));
        keyboardButtonsRow.add(new InlineKeyboardButton().setText("end") 
                .setCallbackData("/end"));
        
        List<List<InlineKeyboardButton>> rowList= new ArrayList<>();
        rowList.add(keyboardButtonsRow);
        inlineKeyboardMarkup.setKeyboard(rowList);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);

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