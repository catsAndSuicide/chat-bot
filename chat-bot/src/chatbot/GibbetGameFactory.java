package chatbot;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;

public class GibbetGameFactory implements GibbetGameCreator {
	
	private HashMap<Integer, ArrayList<String>> sortedByLengthWords = new HashMap<Integer, ArrayList<String>>();
	private ArrayList<String> words = new ArrayList<String>();
	private Random rnd;
	
	public GibbetGameFactory(Random rnd) {
		this.rnd = rnd;
		addWords();
	}
	
	private void addWords() {
		var parser = new JSONParser();
		var parsedFile = new Object();
		
		try {
			parsedFile = parser.parse(new FileReader("words.json"));
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
		
		JSONObject jsonObj = (JSONObject) parsedFile;
		for(Object obj : jsonObj.entrySet()) {
			var entry = (Entry<String, ArrayList<String>>) obj;
			var length = Integer.parseInt(entry.getKey());
			var strings = entry.getValue();
			sortedByLengthWords.put(length, strings);
			words.addAll(strings);
		}
	}
	
	public GibbetGame createNewGibbetGame() {
		return new GibbetGame(chooseWord(), 6);
	}
	
	public HardGibbetGame createNewHardGibbetGame() {
		var word = chooseWord();
		return new HardGibbetGame(word, 6, 
				sortedByLengthWords.get(word.length()));
	}

	private String chooseWord() {
		var wordIndex = rnd.nextInt(words.size());
		return (String) words.get(wordIndex);
	}
}