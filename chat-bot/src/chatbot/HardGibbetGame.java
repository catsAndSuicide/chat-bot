package chatbot;

import java.util.ArrayList;

public class HardGibbetGame extends GibbetGame {
	private ArrayList<String> words;

	public HardGibbetGame(String word, int limit, 
			ArrayList<String> words) 
	{
		super(word, limit);
		this.words = words;
	}
	
	public boolean chechLetter(char letter) {
		var indexes = indexesOfLetter(letter);
		if (indexes.size() == 0)
			return false;
		var wordsWithLetter = new ArrayList<String>();
		var wordsWithoutLetter = new ArrayList<String>();
		for (String word : words) {
			if (word.indexOf(letter) != -1)
				wordsWithLetter.add(word);
			else
				wordsWithoutLetter.add(word);
		}
		if (wordsWithoutLetter.size() <= wordsWithLetter.size()) {
			words = wordsWithLetter;
			for (Integer index : indexes)
				guessedLetters[index] = letter;
			return true;
		}
		hiddenWord = wordsWithoutLetter.get(0);
		words = wordsWithoutLetter;
		return false;
	}
	
	public boolean checkWord(String word) {
		if (!hiddenWord.equalsIgnoreCase(word))
			return false;
		if (words.size() == 1)
			return true;
		var previousWord = hiddenWord;
		for (String availableWord : words) {
			if (!availableWord.equalsIgnoreCase(word))
				hiddenWord = availableWord;
		}
		words.remove(previousWord);
		return false;
	}
}
