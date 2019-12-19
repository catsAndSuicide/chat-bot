package chatbot;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import chatbot.GibbetGame.TurnResult;

class GibbetGameTest {

	@Test
	void showEmptyWord() {
		var gibbetGame = new GibbetGame("gibbet", 5);
		assertEquals("******", gibbetGame.showWord());
	}
	
	@Test
	void showWordWithGuessedLetters() {
		var gibbetGame = new GibbetGame("gibbet", 5);
		gibbetGame.checkLetter('b');
		assertEquals("**bb**", gibbetGame.showWord());
	}
	
	@Test
	void letterIsInWord() {
		var gibbetGame = new GibbetGame("gibbet", 5);
		assertEquals(true, gibbetGame.checkLetter('b'));
	}
	
	@Test
	void letterIsNotInWord() {
		var gibbetGame = new GibbetGame("gibbet", 5);
		assertEquals(false, gibbetGame.checkLetter('a'));
	}
	
	@Test
	void letterIsInGuessedLetters() {
		var gibbetGame = new GibbetGame("gibbet", 5);
		gibbetGame.checkLetter('b');
		assertEquals(true, gibbetGame.letterIsInGuessedLetters('b'));
	}
	
	@Test
	void letterIsNotInGuessedLetters() {
		var gibbetGame = new GibbetGame("gibbet", 5);
		gibbetGame.checkLetter('g');
		assertEquals(false, gibbetGame.letterIsInGuessedLetters('b'));
	}
	
	@Test
	void checkRightLetter() {
		var gibbetGame = new GibbetGame("gibbet", 5);
		assertEquals(TurnResult.rightGuess, gibbetGame.receiveLetter('e'));
	}
	
	@Test
	void checkWrongLetter() {
		var gibbetGame = new GibbetGame("gibbet", 5);
		assertEquals(TurnResult.wrongGuess, gibbetGame.receiveLetter('a'));
	}
	
	@Test
	void isWin() {
		var gibbetGame = new GibbetGame("cat", 5);
		gibbetGame.receiveLetter('c');
		gibbetGame.receiveLetter('a');
		gibbetGame.receiveLetter('t');
		assertEquals(true, gibbetGame.isWin());
	}
	
	@Test
	void isLoss() {
		var gibbetGame = new GibbetGame("cat", 2);
		gibbetGame.receiveLetter('b');
		gibbetGame.receiveLetter('d');
		assertEquals(true, gibbetGame.isLoss());
	}
}
