package chatbot;

public class SimpleGibbetGameCreator implements GibbetGameCreator {
	private String word;
	private int limit;
	
	public SimpleGibbetGameCreator(String word, int limit) {
		this.word = word;
		this.limit = limit;
	}

	public GibbetGame createNew() {
		return new GibbetGame(word, limit);
	}
}
