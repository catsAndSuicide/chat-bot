import java.util.Scanner;

public class Program {

	public static void main(String[] args) {
		var chatBot = new ChatBot();
		System.out.println(chatBot.help);
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
	
		while (true) {
			String message = input.nextLine();
			if (message.equals("/exit"))
				System.exit(0);
				
			var answer = chatBot.reply(message);
		    System.out.println(answer);
		}
	}
}
