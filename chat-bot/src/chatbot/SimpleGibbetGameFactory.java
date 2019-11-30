package chatbot;

import java.util.ArrayList;

public class SimpleGibbetGameFactory implements AbstractGibbetGameFactory {
	private String word;
	private int limit;
	
	public SimpleGibbetGameFactory(String word, int limit) {
		this.word = word;
		this.limit = limit;
	}

	public GibbetGame createNewGibbetGame(int level) {
		if (level == 0)
			return new GibbetGame(word, limit);
		return createNewHardGibbetGame();
	}
	
	private HardGibbetGame createNewHardGibbetGame() {
		var words = new ArrayList<String>();
		words.add(word);
		return new HardGibbetGame(word, limit, words);
	}
}
