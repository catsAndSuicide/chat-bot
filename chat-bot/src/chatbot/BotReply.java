package chatbot;

import java.util.ArrayList;
 
public class BotReply {
	public String guessedWord;
	public ArrayList<GibbetGame.gameState> gameStates;
	
	BotReply(String guessedWord, ArrayList<GibbetGame.gameState> gameStates) {
		this.guessedWord = guessedWord;
		this.gameStates = gameStates;
	}
}
