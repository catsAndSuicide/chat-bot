package chatbot;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatBot {

	private GibbetGameFactory gameFactory;
	protected GibbetGame game;
	protected HashMap<String, String> availableOperations;
	
	public ChatBot(GibbetGameFactory gameFactory) {
		super();
		this.gameFactory = gameFactory;
		this.availableOperations = new HashMap<String, String>();
		this.availableOperations.put("/start", "start");
		this.availableOperations.put("/help", "help");
	}

	private ReplyType checkWinOrLoss() {
		if (game.isWin())
			return ReplyType.win;
		if (game.isLoss())
			return ReplyType.loss;
		return null;
	}
	
	private void startGame() {
		game = gameFactory.createNew();
		this.availableOperations.remove("/start");
		this.availableOperations.put("/restart", "restart");
		this.availableOperations.put("/end", "end");
		this.availableOperations.put("/show", "show");
	}
	
	private void endGame() {
		game = null;
		this.availableOperations.put("/start", "start");
		this.availableOperations.remove("/restart");
		this.availableOperations.remove("/end");	
		this.availableOperations.remove("/show");
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
		
		switch (message) {
			case "/start":
			case "/restart":
				startGame();
				types.add(ReplyType.start);
				types.add(ReplyType.show);
				wrongGuesses = game.getWrongGuesses();
				return new BotReply(game.showWord(), types, null, wrongGuesses);
				
			case "/help":
				types.add(ReplyType.help);
				return new BotReply("", types, null, wrongGuesses);
				
			case "/end":
				if (!this.availableOperations.containsKey(message)) {
					types.add(ReplyType.endNotStartedGame);
					types.add(ReplyType.help);
					return new BotReply("", types, null, wrongGuesses);
				}
				endGame();
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
							wrongGuesses = game.getWrongGuesses();
							types.add(ReplyType.show);
							
							var winOrLoss = checkWinOrLoss();
							if (winOrLoss != null) {
								var hiddenWord = game.showHiddenWord();
								endGame();
								types.add(winOrLoss);
								return new BotReply(hiddenWord, types, answer, wrongGuesses);
							}
							return new BotReply(game.showWord(), types, answer, wrongGuesses);
						}
						return new BotReply(game.showWord(), types, null, wrongGuesses);
					}
					types.add(ReplyType.strangeGuess);
					return new BotReply(game.showWord(), types, null, wrongGuesses);
				}
				types.add(ReplyType.help);
				return new BotReply("", types, null, wrongGuesses);
		}
	}
}
