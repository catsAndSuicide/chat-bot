package chatbot;

public class BotMessage {
	
	public String getMessage(BotReply reply) {
		switch(reply.gameState) {
		    case help:
			    return "This is a Gibbet-game bot.\n"
				  + "/start - to start a new game.\n"
				  + "/end - to end the current game.\n"
				  + "/show - to show the word, which you guess.\n"
				  + "/help - to see this message.\n"
				  + "/exit - to close chat-bot.";
		    case start:
		    	return "Game started. Guess one letter!";
		    case win:
		    	return "You win!";
		    case loss:
		    	return "You lose!";
		    case end:
		    	return "Game over!";
		    case rightGuess:
		    	return "You are right!";
		    case wrongGuess:
		    	return "There is no such letter in my word!";
		    case repeatedGuess:
		    	return "You have already guessed this letter!";
		    case showWord:
		    	return reply.variables.get(0);
		    default:
		    	return "I do not understand!";
		}
	}

}
