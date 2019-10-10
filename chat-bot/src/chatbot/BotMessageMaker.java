package chatbot;

import chatbot.GibbetGame.TurnResult;

public class BotMessageMaker {

	public String getMessage(BotReply reply) {
		var message = "";
		
		if (reply.turnResult != null) {
			if (reply.turnResult == TurnResult.rightGuess)
				message += "You are right!";
			else if (reply.turnResult == TurnResult.wrongGuess)
				message += "There is no such letter in my word!";
			if (reply.replyTypes.size() != 0)
				message += '\n';
		}
		
		for (var i = 0; i < reply.replyTypes.size(); i++) {
			switch(reply.replyTypes.get(i)) {
				case help:
					message += "This is a Gibbet-game bot.\n"
							+ "/start - to start a new game.\n"
							+ "/end - to end the current game.\n"
							+ "/show - to show the word, which you guess.\n"
							+ "/help - to see this message.\n";
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
				case repeatedGuess:
					message += "You have already guessed this letter!";
					break;
				case show:
					message += reply.guessedWord;
					break;
				default:
					message += "I do not understand!";
					break;
			}
			if (i != reply.replyTypes.size() - 1)
				message += "\n";
		}
		return message;
	}
}
