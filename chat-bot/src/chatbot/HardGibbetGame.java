package chatbot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HardGibbetGame extends GibbetGame {
	private ArrayList<String> words;

	public HardGibbetGame(String word, int limit, 
			ArrayList<String> words) 
	{
		super(word, limit);
		this.words = words;
	}
	
	public boolean checkLetter(char letter) {
		var wordsWithLetter = new HashMap<ArrayList<Integer>, ArrayList<String>>();
		var wordsWithoutLetter = new ArrayList<String>();
		
		sortWords(letter, wordsWithoutLetter, wordsWithLetter);
		
		var maxWords = wordsWithoutLetter;
		var maxWordsIndexes = new ArrayList<Integer>();
		
		for (Map.Entry<ArrayList<Integer>, ArrayList<String>>indexesAndWords : wordsWithLetter.entrySet()) {
			if (indexesAndWords.getValue().size() >= maxWords.size()) {
				maxWords = indexesAndWords.getValue();
				maxWordsIndexes = indexesAndWords.getKey();
			}
		}
		words = maxWords;
		hiddenWord = maxWords.get(0);
		
		if (maxWordsIndexes.size() != 0) {
			for (Integer index : maxWordsIndexes)
				guessedLetters[index] = letter;
			return true;
		}
		return false;
	}
	
	public boolean checkWord(String word) {
		if (!hiddenWord.equalsIgnoreCase(word)) {
			if (words.contains(word)){
				words.remove(word);
			}
			return false;
		}
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
	
	private void sortWords(char letter, ArrayList<String> wordsWithoutLetter, 
			HashMap<ArrayList<Integer>, ArrayList<String>> wordsWithLetter) {
		for (String word : words) {
			var charIndexes = new ArrayList<Integer>();
			
			if (word.indexOf(letter) == -1)
				wordsWithoutLetter.add(word);
			else {
				var index = -1;
				while (true) {
					index = word.indexOf(letter, index + 1);
					if (index == -1) {
						break;
					}
					charIndexes.add(index);
				}
				var newWords = new ArrayList<String>();
				if (wordsWithLetter.containsKey(charIndexes)) {
					newWords = wordsWithLetter.get(charIndexes);
				}
				newWords.add(word);
				wordsWithLetter.put(charIndexes, newWords);
			}
		}
	}
}
