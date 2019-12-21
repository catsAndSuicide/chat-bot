package chatbot;

import java.util.Random;

public class ChatBotFactory implements AbstractChatBotFactory {

	@Override
	public ChatBot createNewChatBot(String id) {
		return new ChatBot(new GibbetGameFactory(new Random()), new SimpleLevelSwitcher(), id);
	}
}
