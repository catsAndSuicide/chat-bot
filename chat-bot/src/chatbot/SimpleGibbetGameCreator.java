package chatbot;

import java.util.ArrayList;

public class SimpleGibbetGameCreator implements GibbetGameCreator {
	private String word;
	private int limit;
	
	public SimpleGibbetGameCreator(String word, int limit) {
		this.word = word;
		this.limit = limit;
	}

	public GibbetGame createNewGibbetGame() {
		return new GibbetGame(word, limit);
	}
	
	public HardGibbetGame createNewHardGibbetGame() {
		var words = new ArrayList<String>();
		words.add(word);
		return new HardGibbetGame(word, limit, words);
	}
}
