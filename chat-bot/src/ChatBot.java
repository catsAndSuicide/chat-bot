import java.util.Scanner;

public class ChatBot {
	private String help = "This is a Gibbet-game bot.\n"
				   + "/start - to start a new game.\n"
			       + "/end - to end the current game.\n"
				   + "/show - to show the word, which you guess.\n"
			       + "/help - to see this message.\n"
				   + "/exit - to close chat-bot.";
	private String start = "Game started. Guess one letter!";
	private String win = "You win!";
	private String loss = "You lose!";
	private String end = "Game over!";

	private GibbetGame currentGame = null;
	
	public static void main(String[] args) {
		var chatBot = new ChatBot();
		System.out.println(chatBot.help);
		
		while (true) {
			Scanner input = new Scanner(System.in);
			
			if (chatBot.currentGame != null) {
				if (chatBot.currentGame.isWin()) {
					chatBot.currentGame = null;
					System.out.println(chatBot.win);
				}
				else if (chatBot.currentGame.isLoss()) {
					chatBot.currentGame = null;
					System.out.println(chatBot.loss);
				}
			}
			String message = input.nextLine();
			var answer = chatBot.reply(message);
		    System.out.println(answer);
		}
	}
	
	public String reply(String message){
		switch (message) {
			case "/start":
				this.currentGame = new GibbetGame();
				return String.join("\n", start, currentGame.showWord());
			case "/help":
				return help;
			case "/end":
				currentGame = null;
				return end;
			case "/exit":
				System.exit(0);
			case "/show":
				if (currentGame != null)
					return currentGame.showWord();
				return "";
			default:
				if (currentGame != null)
				    return this.currentGame.processMessage(message);
				return "";
		}
	}
}

