package chatbot;

import java.util.Random;
import java.util.Scanner;

public class Program {
	
	public static void main(String[] args) {
		var chatBot = new ChatBot(new GibbetGameFactory(new Random()));
		var botMessage = new BotMessage();
		
		System.out.println(botMessage.getMessage(chatBot.reply("/help").get(0)));

		Scanner input = new Scanner(System.in);
		try {
			while (true) {
				String message = input.nextLine();
				if (message.equals("/exit"))
					break;

				var answer = "";
				var replies = chatBot.reply(message);
				for (BotReply reply : replies) {
					answer += "\n" + botMessage.getMessage(reply);
				}
				
				System.out.println(answer);
			}
		} finally {
			input.close();
		}
	}
}
