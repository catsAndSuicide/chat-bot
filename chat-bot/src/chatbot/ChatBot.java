package chatbot;

import java.util.ArrayList;

public class ChatBot {

	private GibbetGameCreator gameFactory;
	private GibbetGame game;
	
	public ChatBot(GibbetGameCreator gameFactory) {
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
			return new String[] {"start", "start hard", "help"};
		if (game instanceof HardGibbetGame)
			return new String[] {"restart hard", "help", "end", "show"};
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
		var replyBuilder = new BotReplyBuilder();
		
		switch (message) {
			case "/start":
			case "/restart":
				game = gameFactory.createNewGibbetGame();
				replyStartGame(replyBuilder);
				break;
			
			case "/start hard":
			case "/restart hard":
				game = gameFactory.createNewHardGibbetGame();
				replyStartGame(replyBuilder);
				break;
				
			case "/help":
				replyBuilder.addReplyType(ReplyType.help);
				break;
				
			case "/end":
				replyEndGame(replyBuilder);
				break;
				
			case "/show":
				replyShowWord(replyBuilder);
				break;
				
			default:
				replyDefault(replyBuilder, message);
				break;
		}
		
		replyBuilder.setAvailableOperations(getGameCommands());
		return replyBuilder.buildReply();
	}
	
	private void replyShowWord(BotReplyBuilder replyBuilder) {
		if (game != null) {
			replyBuilder.setGuessedWord(game.showWord());
		}
		replyBuilder.addReplyType(ReplyType.show);
	}
	
	private void replyEndGame(BotReplyBuilder replyBuilder) {
		if (game == null) {
			replyBuilder.addReplyType(ReplyType.endNotStartedGame);
			replyBuilder.addReplyType(ReplyType.help);
		}
		else {
			replyBuilder.setGuessedWord(game.showHiddenWord());
			game = null;
			replyBuilder.addReplyType(ReplyType.show);
			replyBuilder.addReplyType(ReplyType.end);
		}
	}
	
	private void replyStartGame(BotReplyBuilder replyBuilder) {
		replyBuilder.addReplyType(ReplyType.start);
		replyBuilder.addReplyType(ReplyType.show);
		replyBuilder.setWrongGuesses(game.getWrongGuesses());
		replyBuilder.setGuessedWord(game.showWord());
	}
	
	private void replyDefault(BotReplyBuilder replyBuilder, String message) {
		if (game != null) {
			if (message.matches("[A-Za-z]{1}") || message.length() == game.showHiddenWord().length()) {
				replyGuess(replyBuilder, message);
			}
			else {
				replyBuilder.addReplyType(ReplyType.strangeGuess);
				replyBuilder.setGuessedWord(game.showWord());
			}
		}
		else {
			replyBuilder.addReplyType(ReplyType.help);
		}
	}
	
	private void replyGuess(BotReplyBuilder replyBuilder, String message) {
		if (message.matches("[A-Za-z]{1}")) {
			var letter = Character.toLowerCase(message.charAt(0));
			
			if (game.letterIsInGuessedLetters(letter)) {
				replyBuilder.addReplyType(ReplyType.repeatedGuess);
				replyBuilder.setGuessedWord(game.showWord());
			}
			else {
				replyBuilder.setTurnResult(game.receiveLetter(letter));
				replyBuilder.setWrongGuesses(game.getWrongGuesses());
				replyBuilder.addReplyType(ReplyType.show);
			}
		}
		else {
			replyBuilder.setTurnResult(game.receiveWord(message));
			replyBuilder.setWrongGuesses(game.getWrongGuesses());
			replyBuilder.addReplyType(ReplyType.show);
		}
		
		var winOrLoss = checkWinOrLoss();
		if (winOrLoss != null) {
			replyBuilder.setGuessedWord(game.showHiddenWord());
			game = null;
			replyBuilder.addReplyType(winOrLoss);
		}
		else {
			replyBuilder.setGuessedWord(game.showWord());
		}
	}
}
