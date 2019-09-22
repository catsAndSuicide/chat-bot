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

	private GibbetGame currentGame = null;
	
	public String checkWinOrLoss() {
		if (currentGame.isWin())
		{
			currentGame = null;
			return win;
		}
		if (currentGame.isLoss())
		{
			currentGame = null;
			return loss;
		}
		return nextLetter;
	}
	
	public String reply(String message){
		switch (message) {
			case "/start":
				currentGame = new GibbetGame();
				return String.join("\n", start, currentGame.showWord());
			case "/help":
				return help;
			case "/end":
				currentGame = null;
				return end;
			case "/show":
				if (currentGame != null)
					return currentGame.showWord();
				return "";
			default:
				if (currentGame != null) {
					var answer = currentGame.processMessage(message);
					return String.join("\n", answer, checkWinOrLoss());
				}
				return "";
		}
	}
}

