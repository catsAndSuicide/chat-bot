package chatbot;
import java.io.FileReader;
import java.util.Random;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

public class GibbetGameFactory implements GibbetGameCreator {
	
	private JSONArray words = new JSONArray();
	private Random rnd;
	
	public GibbetGameFactory(Random rnd) {
		this.rnd = rnd;
		addWords();
	}
	
	private void addWords() {
		var parser = new JSONParser();
		var parsedFile = new Object();
		
		try {
			parsedFile = parser.parse(new FileReader("nouns.json"));
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
		
		JSONObject jsonObj = (JSONObject) parsedFile;
		words = (JSONArray) jsonObj.get("nouns");
	}
	
	public GibbetGame createNew() {
		return new GibbetGame(chooseWord(), 6);
	}

	private String chooseWord() {
		var wordIndex = rnd.nextInt(words.size());
		return (String) words.get(wordIndex);
	}
}