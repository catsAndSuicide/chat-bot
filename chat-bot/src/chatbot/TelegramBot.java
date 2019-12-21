package chatbot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class TelegramBot extends TelegramLongPollingBot implements Bot {
	
	private HashMap<String, ChatBot> chatBots;
	private HashMap<String, Timer> timers;
	private BotMessageMaker botMessage;
	private AbstractChatBotFactory chatBotFactory;
	
	public TelegramBot() {
		chatBots = new HashMap<String, ChatBot>();
		botMessage = new BotMessageMaker();
		timers = new HashMap<String, Timer>();
		chatBotFactory = new ChatBotFactory();
	}

	public static void main(String[] args) throws TelegramApiRequestException {
		ApiContextInitializer.init();
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
		telegramBotsApi.registerBot(new TelegramBot());
	}

	@Override
	public void onUpdateReceived(Update update) {
		var message = "";
		var id = "";
		try {
			if (update.hasMessage() && update.getMessage().hasText()){
				message = update.getMessage().getText();
				id = update.getMessage().getChatId().toString();
			}
			else if (update.hasCallbackQuery()) {
				message = update.getCallbackQuery().getData();
				id = update.getCallbackQuery().getMessage().getChatId().toString();
			}
			replyToMessage(message, id);
		}
		catch (Exception e) {
			System.out.printf("Error %1$s, id: %2$s, message: %3$s\n", e.toString(), id, message);
		}
	}
	
	public void replyToMessage(String message, String id) {
		BotMessage answer = null;
		HashMap<String, String> availableOperations = null;
		
		synchronized(chatBots) {
			if (!chatBots.containsKey(id)) {
				chatBots.put(id, chatBotFactory.createNewChatBot(id));
			}
			var chatBot = chatBots.get(id);
			answer = botMessage.getMessage(chatBot.reply(message, this));
			availableOperations = answer.availableOperations;
		}
		if (answer.timer != null) {
			if (timers.containsKey(id) && timers.get(id) != null)
				timers.get(id).cancel();
			timers.put(id, answer.timer);
			answer.timer.schedule(answer.task, answer.delay);
		}
		
		if (answer.photo != null) {
			try {
				sendPhoto(id, answer.photo);
			} catch (TelegramApiException e) {
				System.out.println(e.toString());
			}
		}
		
		else {
			if (answer.photoName != null) {
				var photo = new File(
						System.getProperty("user.dir") + File.separator + "pictures", answer.photoName);
				try {
					sendPhoto(id, photo);
				} catch (TelegramApiException e) {
					System.out.println(e.toString());
				}
			}
			try {
				sendMsg(id, answer.text, availableOperations);
			} catch (TelegramApiException e) {
				System.out.println(e.toString());
			}
		}
	}
	
	private void sendPhoto(String chatId, String photoName) throws TelegramApiException {
		SendPhoto sendPhoto = new SendPhoto();
		sendPhoto.setChatId(chatId);
		sendPhoto.setPhoto(photoName);
		execute(sendPhoto);
	}
	
	private void sendPhoto(String chatId, File photo) throws TelegramApiException {
		SendPhoto sendPhoto = new SendPhoto();
		sendPhoto.setChatId(chatId);
		sendPhoto.setPhoto(photo);
		execute(sendPhoto);
	}

	private void sendMsg(String chatId, String answer, HashMap<String, String> textForOperation)
			throws TelegramApiException {
		SendMessage sendMessage = new SendMessage();
		sendMessage.setChatId(chatId);
        sendMessage.setText(answer);
        sendMessage.enableHtml(true);
        
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList= new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow = null;
        var i = 0;
        
        for (Map.Entry<String, String> operationAndText : textForOperation.entrySet()) {
        	if (i % 4 == 0) {
        		if (keyboardButtonsRow != null)
        			rowList.add(keyboardButtonsRow);
        		keyboardButtonsRow = new ArrayList<>();
        	}
        	keyboardButtonsRow.add(new InlineKeyboardButton()
        			.setText((String) operationAndText.getValue()) 
                    .setCallbackData((String) operationAndText.getKey()));
        	i++;
        }
        
        if (keyboardButtonsRow.size() != 0)
        	rowList.add(keyboardButtonsRow);
        inlineKeyboardMarkup.setKeyboard(rowList);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);

        execute(sendMessage);
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