package chatbot;
import java.util.Arrays;

public class GibbetGame {
	
	private String hiddenWord;
	private char[] guessedLetters;
	private int wrongGuesses;
	private int guessLimit;
	
	public enum TurnResult {
		wrongGuess,
		rightGuess,
	}
	
	public GibbetGame(String word, int limit){
		hiddenWord = word;
		guessedLetters = new char[hiddenWord.length()];
		Arrays.fill(guessedLetters, '*');
		wrongGuesses = 0;
		guessLimit = limit;
	}
	
	public int getWrongGuesses() {
		return wrongGuesses;
	}

	public String showWord() {
		return String.valueOf(guessedLetters);
	}
	
	public String showHiddenWord() {
		return hiddenWord;
	}

	public boolean letterIsInWord(char letter) {
		var letterInWord = false;
		
		for (var i = 0; i < hiddenWord.length(); i++) {
			if (hiddenWord.charAt(i) == letter) {
				guessedLetters[i] = letter;
				letterInWord = true;
			}
		}
		return letterInWord;
	}
	
	public boolean letterIsInGuessedLetters(char letter) {
		for (char guessedLetter : guessedLetters) {
			if (guessedLetter == letter)
				return true;
			}
		return false;
	}
	
	public boolean isWin() {
		return hiddenWord.equals(showWord());
	}
	
	public boolean isLoss() {
		return wrongGuesses == guessLimit;
	}
	
	public TurnResult receiveLetter(char letter) {
		if (letterIsInWord(letter))
			return TurnResult.rightGuess;
	
		wrongGuesses++;
		return TurnResult.wrongGuess;
	}
	
	public TurnResult receiveWord(String word) {
		if (hiddenWord.equalsIgnoreCase(word)) {
			guessedLetters = hiddenWord.toCharArray();
			return TurnResult.rightGuess;
		}
	
		wrongGuesses++;
		return TurnResult.wrongGuess;
	}
}
