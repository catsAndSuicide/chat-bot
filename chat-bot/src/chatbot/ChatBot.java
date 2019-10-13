package chatbot;

import java.util.ArrayList;

public class ChatBot {

	private GibbetGameFactory gameFactory;
	protected GibbetGame game;
	
	public ChatBot(GibbetGameFactory gameFactory) {
		super();
		this.gameFactory = gameFactory;
	}

	private ReplyType checkWinOrLoss() {
		if (game.isWin())
			return ReplyType.win;
		if (game.isLoss())
			return ReplyType.loss;
		return null;
	}
	
	public enum ReplyType {
		start,
		end, 
		win,
		loss,
		help,
		show,
		repeatedGuess,
		strangeGuess
	}
	
	public BotReply reply(String message){
		var types = new ArrayList<ReplyType>();
		var wrongGuesses = 0;
		
		switch (message) {
			case "/start":
				game = gameFactory.createNew();
				types.add(ReplyType.start);
				types.add(ReplyType.show);
				wrongGuesses = game.getWrongGuesses();
				return new BotReply(game.showWord(), types, null, wrongGuesses);
				
			case "/help":
				types.add(ReplyType.help);
				return new BotReply("", types, null, wrongGuesses);
				
			case "/end":
				game = null;
				types.add(ReplyType.end);
				return new BotReply("", types, null, wrongGuesses);
				
			case "/show":
				if (game != null) {
					types.add(ReplyType.show);
					return new BotReply(game.showWord(), types, null, wrongGuesses);
				}
				types.add(ReplyType.help);
				return new BotReply("", types, null, wrongGuesses);
				
			default:
				if (game != null) {
					if (message.matches("[A-Za-z]{1}"))
					{
						var letter = Character.toLowerCase(message.charAt(0));
						if (game.letterIsInGuessedLetters(letter))
							types.add(ReplyType.repeatedGuess);
						else {
							var answer = game.receiveLetter(letter);
							types.add(ReplyType.show);
							
							var winOrLoss = checkWinOrLoss();
							if (winOrLoss != null) {
								var hiddenWord = game.showHiddenWord();
								game = null;
								types.add(winOrLoss);
								return new BotReply(hiddenWord, types, answer, game.getWrongGuesses());
							}
							return new BotReply(game.showWord(), types, answer, game.getWrongGuesses());
						}
						return new BotReply(game.showWord(), types, null, game.getWrongGuesses());
					}
					types.add(ReplyType.strangeGuess);
					return new BotReply(game.showWord(), types, null, wrongGuesses);
				}
				types.add(ReplyType.help);
				return new BotReply("", types, null, wrongGuesses);
		}
	}
}
