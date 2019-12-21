package chatbot;

import java.util.Timer;

public class ChatBot {

	private AbstractGibbetGameFactory gameFactory;
	private GibbetGame game;
	private LevelSwitcher levelSwitcher;
	private String id;
	
	public ChatBot(AbstractGibbetGameFactory gameFactory, LevelSwitcher levelSwitcher, String id) {
		super();
		this.gameFactory = gameFactory;
		this.levelSwitcher = levelSwitcher;
		this.id = id;
	}

	private ReplyType checkWinOrLoss() {
		if (game.isWin())
			return ReplyType.win;
		if (game.isLoss())
			return ReplyType.loss;
		return null;
	}
	
	private String[] getAvailableOperations() {
		if (game == null) {
			if (levelSwitcher.canStartLevel(1))
				return new String[] {"start", "start hard", "help"};
			return new String[] {"start", "help"};
		}
		if (game instanceof HardGibbetGame)
			return new String[] {"restart hard", "help", "end", "show", "hint"};
		return new String[] {"restart", "help", "end", "show", "hint"};
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
		endNotStartedGame,
		closedLevel,
		hint,
		hintNotStartedGame
	}
	
	public BotReply reply(String message, Bot requester){
		var replyBuilder = new BotReplyBuilder();
		
		switch (message) {
			case "/start":
			case "/restart":
				game = gameFactory.createNewGibbetGame(0);
				replyStartGame(replyBuilder, requester);
				break;
			
			case "/start hard":
			case "/restart hard":
				if (levelSwitcher.canStartLevel(1)) {
					game = gameFactory.createNewGibbetGame(1);
					replyStartGame(replyBuilder, requester);
				}
				else 
					replyBuilder.addReplyType(ReplyType.closedLevel);
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
				
			case "/hint":
				if (game != null) {
					replyBuilder.setPhoto(new ImageSearcher(game.hiddenWord).findImage());
					replyBuilder.setTimer(new Timer(), new EmptyTask(), 1L);
				}
				else
					replyBuilder.addReplyType(ReplyType.hintNotStartedGame);
				break;
				
			default:
				replyDefault(replyBuilder, message);
				break;
		}
		
		replyBuilder.setAvailableOperations(getAvailableOperations());
		return replyBuilder.buildReply();
	}
	
	private void replyShowWord(BotReplyBuilder replyBuilder) {
		if (game != null) {
			replyBuilder.setGuessedWord(game.showWord());
			replyBuilder.addReplyType(ReplyType.show);		
		}
		else {
			replyBuilder.addReplyType(ReplyType.strangeGuess);
		}
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
			replyBuilder.setTimer(new Timer(), new EmptyTask(), 1L);
		}
	}
	
	private void replyStartGame(BotReplyBuilder replyBuilder, Bot bot) {
		replyBuilder.addReplyType(ReplyType.start);
		replyBuilder.addReplyType(ReplyType.show);
		replyBuilder.setWrongGuesses(game.getWrongGuesses());
		replyBuilder.setGuessedWord(game.showWord());
		replyBuilder.setTimer(new Timer(), new HintRequestTask(id, bot), 60000L);
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
			if (winOrLoss == ReplyType.win)
				levelSwitcher.registerWin();
			else
				levelSwitcher.registerLoss();
			replyBuilder.setGuessedWord(game.showHiddenWord());
			game = null;
			replyBuilder.addReplyType(winOrLoss);
		}
		else {
			replyBuilder.setGuessedWord(game.showWord());
		}
	}
}
