package chatbot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class TelegramBot extends TelegramLongPollingBot{
	
	private HashMap<String, ChatBot> chatBots;
	private BotMessageMaker botMessage;
	
	public TelegramBot() {
		chatBots = new HashMap<String, ChatBot>();
		botMessage = new BotMessageMaker();
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
		BotMessage answer = null;
		HashMap<String, String> availableOperations = null;
		try {
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
				var chatBot = chatBots.get(id);
				answer = botMessage.getMessage(chatBot.reply(message));
				availableOperations = answer.availableOperations;
			}
			if (answer.photoName != null)
				sendPhoto(id, answer.photoName);
			sendMsg(id, answer.text, availableOperations);
		}
		catch (Exception e) {
			System.out.printf("Error %1$s, id: %2$s, message: %3$s\n", e.toString(), id, message);
		}
	}
	
	private void sendPhoto(String chatId, String photoName) throws TelegramApiException {
		SendPhoto sendPhoto = new SendPhoto();
		sendPhoto.setChatId(chatId);
		sendPhoto.setPhoto(new File(System.getProperty("user.dir") + File.separator + "pictures", photoName));
		
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