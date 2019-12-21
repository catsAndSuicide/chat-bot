package chatbot;

import java.util.Scanner;

public class Program {
	
	public static void main(String[] args) {
		var chatBot = new ConsoleBot();
		chatBot.replyToMessage("/help", null);

		Scanner input = new Scanner(System.in);
		try {
			while (true) {
				String message = input.nextLine();
				if (message.equals("/exit"))
					break;
				
				chatBot.replyToMessage(message, null);
			}
		} finally {
			input.close();
		}
	}
}
