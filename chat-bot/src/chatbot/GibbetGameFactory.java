package chatbot;
import java.util.Random;

public class GibbetGameFactory {
	
	private String[] words = new String[] { "gibbet", "death", "sessions", "pain" };
	private Random rnd;

	public GibbetGameFactory(Random rnd) {
		this.rnd = rnd;
	}
	
	public GibbetGame createNew() {
		return new GibbetGame(chooseWord(), 5);
	}

	private String chooseWord() {
		var wordIndex = rnd.nextInt(words.length);
		return words[wordIndex];
	}
}