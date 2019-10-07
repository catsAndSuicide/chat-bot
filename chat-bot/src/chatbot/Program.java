package chatbot;

import java.util.Random;
import java.util.Scanner;

public class Program {
	
	public static void main(String[] args) {
		var chatBot = new ChatBot(new GibbetGameFactory(new Random()));
		var botMessage = new BotMessage();
		
		System.out.println(botMessage.getMessage(chatBot.reply("/help")));

		Scanner input = new Scanner(System.in);
		try {
			while (true) {
				String message = input.nextLine();
				if (message.equals("/exit"))
					break;
				
				System.out.println(botMessage.getMessage(chatBot.reply(message)));
			}
		} finally {
			input.close();
		}
	}
}
