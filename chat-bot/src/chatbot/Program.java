package chatbot;

import java.util.Random;
import java.util.Scanner;

public class Program {

	public static void main(String[] args) {
		var chatBot = new ChatBot(new GibbetGameFactory(new Random()));
		System.out.println(chatBot.help);

		Scanner input = new Scanner(System.in);
		try {
			while (true) {
				String message = input.nextLine();
				if (message.equals("/exit"))
					break;

				var answer = chatBot.reply(message);
				System.out.println(answer);
			}
		} finally {
			input.close();
		}
	}
}
