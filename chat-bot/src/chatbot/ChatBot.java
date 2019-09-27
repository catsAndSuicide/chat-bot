package chatbot;
public class ChatBot {
	protected String help = "This is a Gibbet-game bot.\n"
						  + "/start - to start a new game.\n"
						  + "/end - to end the current game.\n"
						  + "/show - to show the word, which you guess.\n"
						  + "/help - to see this message.\n"
						  + "/exit - to close chat-bot.";
	private String start = "Game started. Guess one letter!";
	private String win = "You win!";
	private String loss = "You lose!";
	private String nextLetter = "Try to guess the next letter!";
	private String end = "Game over!";

	private GibbetGameFactory gameFactory;
	private GibbetGame game;
	
	public ChatBot(GibbetGameFactory gameFactory) {
		super();
		this.gameFactory = gameFactory;
	}

	public String checkWinOrLoss() {
		if (game.isWin())
		{
			game = null;
			return win;
		}
		if (game.isLoss())
		{
			game = null;
			return loss;
		}
		return nextLetter;
	}
	
	public String reply(String message){
		switch (message) {
			case "/start":
				game = gameFactory.createNew();
				return String.join("\n", start, game.showWord());
			case "/help":
				return help;
			case "/end":
				game = null;
				return end;
			case "/show":
				if (game != null)
					return game.showWord();
				return "";
			default:
				if (game != null) {
					if (message.matches("[a-z]{1}"))
					{
						var answer = game.checkLetter(message.charAt(0));
						return String.join("\n", answer, checkWinOrLoss());
					}
					return game.wrongMessage;
				}
				return "";
		}
	}
}
