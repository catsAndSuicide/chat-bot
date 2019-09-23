import java.util.Random;

public class GibbetGameInitializer {
	
	private String[] words = new String[] { "gibbet", "death", "sessions", "pain" };
	private Random rnd = new Random();
	public GibbetGame game;
	
	public GibbetGameInitializer() {
		game = new GibbetGame(chooseWord(), 5);
	}

	private String chooseWord() {
		var wordIndex = rnd.nextInt(words.length);
		return words[wordIndex];
	}
}