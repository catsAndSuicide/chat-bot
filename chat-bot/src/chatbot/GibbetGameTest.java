package chatbot;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Random;

import org.junit.jupiter.api.Test;

class GibbetGameTest {

	@Test
	void showEmptyWord() {
		var gibbetGame = new GibbetGame("gibbet", 5);
		assertEquals(gibbetGame.showWord(), "******");
	}
	
	@Test
	void showWordWithGuessedLetters() {
		var gibbetGame = new GibbetGame("gibbet", 5);
		gibbetGame.letterIsInWord('b');
		assertEquals(gibbetGame.showWord(), "**bb**");
	}
	
	@Test
	void letterIsInWord() {
		var gibbetGame = new GibbetGame("gibbet", 5);
		assertEquals(gibbetGame.letterIsInWord('b'), true);
	}
	
	@Test
	void letterIsNotInWord() {
		var gibbetGame = new GibbetGame("gibbet", 5);
		assertEquals(gibbetGame.letterIsInWord('a'), false);
	}
	
	@Test
	void letterIsInGuessedLetters() {
		var gibbetGame = new GibbetGame("gibbet", 5);
		gibbetGame.letterIsInWord('b');
		assertEquals(gibbetGame.letterIsInGuessedLetters('b'), true);
	}
	
	@Test
	void letterIsNotInGuessedLetters() {
		var gibbetGame = new GibbetGame("gibbet", 5);
		gibbetGame.letterIsInWord('g');
		assertEquals(gibbetGame.letterIsInGuessedLetters('b'), false);
	}
	
	@Test
	void checkRightLetter() {
		var gibbetGame = new GibbetGame("gibbet", 5);
		assertEquals(gibbetGame.checkLetter('e'), 
				GibbetGame.gameState.rightGuess);
	}
	
	@Test
	void checkWrongLetter() {
		var gibbetGame = new GibbetGame("gibbet", 5);
		assertEquals(gibbetGame.checkLetter('a'), 
				GibbetGame.gameState.wrongGuess);
	}
	
	@Test
	void isWin() {
		var gibbetGame = new GibbetGame("cat", 5);
		gibbetGame.checkLetter('c');
		gibbetGame.checkLetter('a');
		gibbetGame.checkLetter('t');
		assertEquals(gibbetGame.isWin(), true);
	}
	
	@Test
	void isLoss() {
		var gibbetGame = new GibbetGame("cat", 2);
		gibbetGame.checkLetter('b');
		gibbetGame.checkLetter('d');
		assertEquals(gibbetGame.isLoss(), true);
	}
}
