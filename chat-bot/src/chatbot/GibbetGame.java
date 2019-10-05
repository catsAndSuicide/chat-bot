package chatbot;
import java.util.Arrays;

public class GibbetGame {
	
	private String hiddenWord;
	private char[] guessedLetters;
	private int wrongGuesses;
	private int guessLimit;
	
	public GibbetGame(String word, int limit){
		hiddenWord = word;
		guessedLetters = new char[hiddenWord.length()];
		Arrays.fill(guessedLetters, '.');
		wrongGuesses = 0;
		guessLimit = limit;
	}

	public String showWord() {
		return String.valueOf(guessedLetters);
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
		for (var i = 0; i < guessedLetters.length; i++) {
			if (guessedLetters[i] == letter)
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
	
	public gameState checkLetter(char letter) {
		if (letterIsInGuessedLetters(letter))
			
			return gameState.repeatedGuess;
		
		if (letterIsInWord(letter))
			return gameState.rightGuess;
		
		wrongGuesses++;
		return gameState.wrongGuess;
	}
	
	public enum gameState {
		wrongGuess,
		rightGuess,
		repeatedGuess,
		loss, 
		win,
		help,
		start,
		end,
		showWord,
		strangeGuess
	}

}
