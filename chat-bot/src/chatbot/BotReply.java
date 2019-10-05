package chatbot;

import java.util.ArrayList;
 
public class BotReply {
	public ArrayList<String> variables;
	public GibbetGame.gameState gameState;
	
	BotReply(ArrayList<String> variables, GibbetGame.gameState gameState) {
		this.variables = variables;
		this.gameState = gameState;
	}
}
