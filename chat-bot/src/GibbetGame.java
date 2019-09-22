import java.util.Arrays;
import java.util.Random;

public class GibbetGame {
	protected String rightGuess = "You are right!";
	protected String wrongGuess = "There is no such letter in my word!";
	protected String wrongMessage = "I don`t understand!";
	protected String guessedLetter = "You have already guessed this letter!";
	private String[] words = new String[] { "gibbet", "death", "sessions", "pain" };
	Random rnd = new Random();
	
	private String hiddenWord;
	private char[] guessedLetters;
	private int wrongGuesses;
	private int guessLimit;
	
	GibbetGame(){
		hiddenWord = chooseWord();
		guessedLetters = new char[hiddenWord.length()];
		Arrays.fill(guessedLetters, '*');
		
		wrongGuesses = 0;
		guessLimit = 5;
	}
	
	GibbetGame(String word, int limit){
		hiddenWord = word;
		guessedLetters = new char[hiddenWord.length()];
		Arrays.fill(guessedLetters, '*');
		
		wrongGuesses = 0;
		guessLimit = limit;
	}
	
	private String chooseWord() {
		var wordIndex = rnd.nextInt(words.length);
		return words[wordIndex];
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
	
	public String checkLetter(char letter) {
		if (letterIsInGuessedLetters(letter))
			return guessedLetter;
		
		if (letterIsInWord(letter))
			return String.join("\n", showWord(), rightGuess);
		
		wrongGuesses++;
		return String.join("\n", showWord(), wrongGuess);
	}
		
	public String processMessage(String message) {
		if (message.matches("[a-z]{1}"))
			return checkLetter(message.charAt(0));
		return wrongMessage;
	}
}
