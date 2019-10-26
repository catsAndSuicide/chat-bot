package chatbot;

import java.util.HashMap;

import chatbot.GibbetGame.TurnResult;

public class BotMessageMaker {
	
	public BotMessage getMessage(BotReply reply) {
		var text = "";
		String photoName = null;
		var availableOperations = new HashMap<String, String>();
		
		for (String operation : reply.availableOperations) 
			switch(operation) {
				case "start":
					availableOperations.put("/start", "start");
					break;
				case "restart":
					availableOperations.put("/start", "restart");
					break;
				case "end":
					availableOperations.put("/end", "end");
					break;
				case "show":
					availableOperations.put("/show", "show");
					break;
				case "help":
					availableOperations.put("/help", "help");
					break;
			}
		
		if (reply.turnResult != null) {
			photoName = Integer.toString(reply.wrongGuesses + 1) + ".jpg";
			
			if (reply.turnResult == TurnResult.rightGuess)
				text += "You are right!";
			else if (reply.turnResult == TurnResult.wrongGuess)
				text += "There is no such letter in my word!";
			if (reply.replyTypes.size() != 0)
				text += '\n';
		}
		
		for (var i = 0; i < reply.replyTypes.size(); i++) {
			switch(reply.replyTypes.get(i)) {
				case help:
					text += "This is a Gibbet-game bot.\n"
							+ "/start - to start a new game.\n"
							+ "/restart - to restart a game.\n"
							+ "/end - to end the current game.\n"
							+ "/show - to show the word, which you guess.\n"
							+ "/help - to see this message.\n";
					break;
				case start:
					text += "New game started. Guess one letter!";
					break;
				case win:
					text += "You win!";
					break;
				case loss:
					text += "You lose!";
					break;
				case end:
					text += "Game over!";
					break;
				case repeatedGuess:
					text += "You have already guessed this letter!";
					break;
				case show:
					text += reply.guessedWord;
					break;
				case endNotStartedGame:
					text += "You should start game to end it!";
					break;
				default:
					text += "I don't understand!";
					break;
			}
			if (i != reply.replyTypes.size() - 1)
				text += "\n";
		}
		return new BotMessage(text, photoName, availableOperations);
	}
}
