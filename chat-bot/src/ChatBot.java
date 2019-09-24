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

	private GibbetGameInitializer currentGameInitializer = null;
	
	public String checkWinOrLoss() {
		if (currentGameInitializer.game.isWin())
		{
			currentGameInitializer = null;
			return win;
		}
		if (currentGameInitializer.game.isLoss())
		{
			currentGameInitializer = null;
			return loss;
		}
		return nextLetter;
	}
	
	public String reply(String message){
		switch (message) {
			case "/start":
				currentGameInitializer = new GibbetGameInitializer();
				return String.join("\n", start, currentGameInitializer.game.showWord());
			case "/help":
				return help;
			case "/end":
				currentGameInitializer = null;
				return end;
			case "/show":
				if (currentGameInitializer != null)
					return currentGameInitializer.game.showWord();
				return "";
			default:
				if (currentGameInitializer != null) {
					var answer = currentGameInitializer.game.processMessage(message);
					return String.join("\n", answer, checkWinOrLoss());
				}
				return "";
		}
	}
}
