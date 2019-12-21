package chatbot;

public class ConsoleBot implements Bot {
	private BotMessageMaker botMessage;
	private ChatBot chatBot;
	
	public ConsoleBot() {
		botMessage = new BotMessageMaker();
		ChatBotFactory chatBotFactory = new ChatBotFactory();
		chatBot = chatBotFactory.createNewChatBot(null);
	}

	@Override
	public void replyToMessage(String message, String id) {
		System.out.println(botMessage.getMessage(chatBot.reply(message, this)).text);
	}
}
