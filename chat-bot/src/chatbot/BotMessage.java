package chatbot;

public class BotMessage {

	public String getMessage(BotReply reply) {
		var message = "";

		for (var i = 0; i < reply.gameStates.size(); i++) {
			switch(reply.gameStates.get(i)) {
				case help:
					message += "This is a Gibbet-game bot.\n"
							+ "/start - to start a new game.\n"
							+ "/end - to end the current game.\n"
							+ "/show - to show the word, which you guess.\n"
							+ "/help - to see this message.\n"
							+ "/exit - to close chat-bot.";
					break;
				case start:
					message += "Game started. Guess one letter!";
					break;
				case win:
					message += "You win!";
					break;
				case loss:
					message += "You lose!";
					break;
				case end:
					message += "Game over!";
					break;
				case rightGuess:
					message += "You are right!";
					break;
				case wrongGuess:
					message += "There is no such letter in my word!";
					break;
				case repeatedGuess:
					message += "You have already guessed this letter!";
					break;
				case showWord:
					message += reply.guessedWord;
					break;
				default:
					message += "I do not understand!";
					break;
			}
			if (i != reply.gameStates.size() - 1)
			message += "\n";
		}
		return message;
	}
}
