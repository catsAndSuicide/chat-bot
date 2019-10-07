package chatbot;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import org.junit.jupiter.api.Test;

class GibbetGameTest {

	@Test
	void showEmptyWord() {
		var gibbetGame = new GibbetGame("gibbet", 5);
		assertEquals(gibbetGame.showWord(), "......");
	}
	
	@Test
	void showWordWithGuessedLetters() {
		var gibbetGame = new GibbetGame("gibbet", 5);
		gibbetGame.letterIsInWord('b');
		assertEquals(gibbetGame.showWord(), "..bb..");
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
	
	@Test
	void replyHelp() {
		var chatBot = new ChatBot(new GibbetGameFactory(new Random()));
		var reply = chatBot.reply("/help");
		
		assertTrue(reply.gameStates.contains(GibbetGame.gameState.help));
		assertEquals(reply.guessedWord, "");
	}
	
	@Test
	void replyStart() {
		var chatBot = new ChatBot(new GibbetGameFactory(new Random()));
		var reply = chatBot.reply("/start");
		
		assertTrue(reply.gameStates.contains(GibbetGame.gameState.start) &&
				   reply.gameStates.contains(GibbetGame.gameState.showWord));
		assertEquals(reply.guessedWord, chatBot.game.showWord());
	}
	
	@Test
	void replyShowWhenNullGame() {
		var chatBot = new ChatBot(new GibbetGameFactory(new Random()));
		var reply = chatBot.reply("/show");
		
		assertTrue(reply.gameStates.contains(GibbetGame.gameState.help));
		assertEquals(reply.guessedWord, "");
	}
	
	@Test
	void replyShowWhenGameStarted() {
		var chatBot = new ChatBot(new GibbetGameFactory(new Random()));
		chatBot.reply("/start");
		var reply = chatBot.reply("/show");
		
		assertTrue(reply.gameStates.contains(GibbetGame.gameState.showWord));
		assertEquals(reply.guessedWord, chatBot.game.showWord());
	}
	
	@Test
	void replyEnd() {
		var chatBot = new ChatBot(new GibbetGameFactory(new Random()));
		var reply = chatBot.reply("/end");
		
		assertTrue(reply.gameStates.contains(GibbetGame.gameState.end));
		assertEquals(reply.guessedWord, "");
	}
	
	@Test
	void replyRightGuess() {
		var chatBot = new ChatBot(new GibbetGameFactory(new Random()));
		chatBot.game = new GibbetGame("gibbet", 5);
		var reply = chatBot.reply("b");
		
		assertTrue(reply.gameStates.contains(GibbetGame.gameState.rightGuess));
		assertEquals(reply.guessedWord, chatBot.game.showWord());
	}
	
	@Test
	void replyWrongGuess() {
		var chatBot = new ChatBot(new GibbetGameFactory(new Random()));
		chatBot.game = new GibbetGame("gibbet", 5);
		var reply = chatBot.reply("s");
		
		assertTrue(reply.gameStates.contains(GibbetGame.gameState.wrongGuess));
		assertEquals(reply.guessedWord, chatBot.game.showWord());
	}
	
	@Test
	void replyStrangeGuess() {
		var chatBot = new ChatBot(new GibbetGameFactory(new Random()));
		chatBot.game = new GibbetGame("gibbet", 5);
		var reply = chatBot.reply("!");
		
		assertTrue(reply.gameStates.contains(GibbetGame.gameState.strangeGuess));
		assertEquals(reply.guessedWord, chatBot.game.showWord());
	}
	
	@Test
	void replyLoss() {
		var chatBot = new ChatBot(new GibbetGameFactory(new Random()));
		chatBot.game = new GibbetGame("cat", 2);
		chatBot.reply("b");
		var reply = chatBot.reply("d");
		
		assertTrue(reply.gameStates.contains(GibbetGame.gameState.loss));
		assertEquals(reply.guessedWord, "cat");
	}
	
	@Test
	void replyWin() {
		var chatBot = new ChatBot(new GibbetGameFactory(new Random()));
		chatBot.game = new GibbetGame("cat", 5);
		chatBot.reply("a");
		chatBot.reply("t");
		var reply = chatBot.reply("c");
		
		assertTrue(reply.gameStates.contains(GibbetGame.gameState.win));
		assertEquals(reply.guessedWord, "cat");
	}
}
