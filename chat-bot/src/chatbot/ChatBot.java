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
	
	private String[] getGameCommands() {
		if (game == null)
			return new String[] {"start", "help"};
		return new String[] {"restart", "help", "end", "show"};
	}
	
	public enum ReplyType {
		start,
		end, 
		win,
		loss,
		help,
		show,
		repeatedGuess,
		strangeGuess,
		endNotStartedGame
	}
	
	public BotReply reply(String message){
		var types = new ArrayList<ReplyType>();
		var wrongGuesses = 0;
		var hiddenWord = "";
		
		switch (message) {
			case "/start":
			case "/restart":
				game = gameFactory.createNew();
				types.add(ReplyType.start);
				types.add(ReplyType.show);
				wrongGuesses = game.getWrongGuesses();
				return new BotReply(game.showWord(), types, null, wrongGuesses, getGameCommands());
				
			case "/help":
				types.add(ReplyType.help);
				return new BotReply("", types, null, wrongGuesses, getGameCommands());
				
			case "/end":
				if (game == null) {
					types.add(ReplyType.endNotStartedGame);
					types.add(ReplyType.help);
					return new BotReply("", types, null, wrongGuesses, getGameCommands());
				}
				hiddenWord = game.showHiddenWord();
				game = null;
				types.add(ReplyType.show);
				types.add(ReplyType.end);
				return new BotReply(hiddenWord, types, null, wrongGuesses, getGameCommands());
				
			case "/show":
				if (game != null) {
					types.add(ReplyType.show);
					return new BotReply(game.showWord(), types, null, wrongGuesses, getGameCommands());
				}
				types.add(ReplyType.help);
				return new BotReply("", types, null, wrongGuesses, getGameCommands());
				
			default:
				if (game != null) {
					if (message.matches("[A-Za-z]{1}"))
					{
						var letter = Character.toLowerCase(message.charAt(0));
						if (game.letterIsInGuessedLetters(letter))
							types.add(ReplyType.repeatedGuess);
						else {
							var answer = game.receiveLetter(letter);
							wrongGuesses = game.getWrongGuesses();
							types.add(ReplyType.show);
							
							var winOrLoss = checkWinOrLoss();
							if (winOrLoss != null) {
								hiddenWord = game.showHiddenWord();
								game = null;
								types.add(winOrLoss);
								return new BotReply(hiddenWord, types, answer, wrongGuesses, getGameCommands());
							}
							return new BotReply(game.showWord(), types, answer, wrongGuesses, getGameCommands());
						}
						return new BotReply(game.showWord(), types, null, wrongGuesses, getGameCommands());
					}
					types.add(ReplyType.strangeGuess);
					return new BotReply(game.showWord(), types, null, wrongGuesses, getGameCommands());
				}
				types.add(ReplyType.help);
				return new BotReply("", types, null, wrongGuesses, getGameCommands());
		}
	}
}
