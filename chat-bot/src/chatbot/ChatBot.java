package chatbot;

import java.util.ArrayList;

public class ChatBot {

	private GibbetGameFactory gameFactory;
	protected GibbetGame game;
	
	public ChatBot(GibbetGameFactory gameFactory) {
		super();
		this.gameFactory = gameFactory;
	}

	private GibbetGame.gameState checkWinOrLoss() {
		if (game.isWin())
			return GibbetGame.gameState.win;
		if (game.isLoss())
			return GibbetGame.gameState.loss;
		return null;
	}
	
	public BotReply reply(String message){
		var states = new ArrayList<GibbetGame.gameState>();
		
		switch (message) {
			case "/start":
				game = gameFactory.createNew();
				states.add(GibbetGame.gameState.start);
				states.add(GibbetGame.gameState.showWord);
				return new BotReply(game.showWord(), states);
				
			case "/help":
				states.add(GibbetGame.gameState.help);
				return new BotReply("", states);
				
			case "/end":
				game = null;
				states.add(GibbetGame.gameState.end);
				return new BotReply("", states);
				
			case "/show":
				if (game != null) {
					states.add(GibbetGame.gameState.showWord);
					return new BotReply(game.showWord(), states);
				}
				states.add(GibbetGame.gameState.help);
				return new BotReply("", states);
				
			default:
				if (game != null) {
					if (message.matches("[A-Za-z]{1}"))
					{
						var answer = game.checkLetter(Character.toLowerCase(message.charAt(0)));
						states.add(answer);
						states.add(GibbetGame.gameState.showWord);
						
						var winOrLoss = checkWinOrLoss();
						if (winOrLoss != null) {
							var hiddenWord = game.showHiddenWord();
							game = null;
							states.add(winOrLoss);
							return new BotReply(hiddenWord, states);
						}
						
						return new BotReply(game.showWord(), states);
					}
					states.add(GibbetGame.gameState.strangeGuess);
					return new BotReply(game.showWord(), states);
				}
				states.add(GibbetGame.gameState.help);
				return new BotReply("", states);
		}
	}
}
