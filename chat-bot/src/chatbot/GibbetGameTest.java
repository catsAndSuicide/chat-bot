package chatbot;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Random;

import org.junit.jupiter.api.Test;

import chatbot.ChatBot.ReplyType;
import chatbot.GibbetGame.TurnResult;

class GibbetGameTest {
	
	private ChatBot chatBot;
	private ArrayList<ReplyType> replyTypes;
	private BotReply reply;

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
		assertEquals(gibbetGame.receiveLetter('e'), TurnResult.rightGuess);
	}
	
	@Test
	void checkWrongLetter() {
		var gibbetGame = new GibbetGame("gibbet", 5);
		assertEquals(gibbetGame.receiveLetter('a'), TurnResult.wrongGuess);
	}
	
	@Test
	void isWin() {
		var gibbetGame = new GibbetGame("cat", 5);
		gibbetGame.receiveLetter('c');
		gibbetGame.receiveLetter('a');
		gibbetGame.receiveLetter('t');
		assertEquals(gibbetGame.isWin(), true);
	}
	
	@Test
	void isLoss() {
		var gibbetGame = new GibbetGame("cat", 2);
		gibbetGame.receiveLetter('b');
		gibbetGame.receiveLetter('d');
		assertEquals(gibbetGame.isLoss(), true);
	}
	
	private void createReply(String message) {
		chatBot = new ChatBot(new GibbetGameFactory(new Random()));
		reply = chatBot.reply(message);
		
		replyTypes = new ArrayList<ReplyType>();
	}
	
	private void checkReply(BotReply reply,
							String guessedWord,
							ArrayList<ReplyType> replyTypes, 
							TurnResult turnResult,
							int wrongGuesses) {

		assertEquals(reply.guessedWord, guessedWord);
		assertEquals(reply.replyTypes, replyTypes);
		assertEquals(reply.turnResult, turnResult);
		assertEquals(reply.wrongGuesses, wrongGuesses);
	}

	@Test
	void replyHelp() {
		createReply("/help");
		replyTypes.add(ReplyType.help);
		
		checkReply(reply, "", replyTypes, null, 0);
	}
	
	@Test
	void replyStart() {
		createReply("/start");
		replyTypes.add(ReplyType.start);
		replyTypes.add(ReplyType.show);
		
		checkReply(reply, chatBot.game.showWord(), replyTypes, null, 0);
	}
	
	@Test
	void replyShowWhenNullGame() {
		createReply("/show");
		replyTypes.add(ReplyType.help);
		
		checkReply(reply, "", replyTypes, null, 0);
	}
	
	@Test
	void replyShowWhenGameStarted() {
		createReply("/start");
		reply = chatBot.reply("/show");
		replyTypes.add(ReplyType.show);
		
		checkReply(reply, chatBot.game.showWord(), replyTypes, null, 0);
	}
	
	@Test
	void replyEnd() {
		createReply("/end");
		replyTypes.add(ReplyType.end);
		
		checkReply(reply, "", replyTypes, null, 0);
	}
	
	private void createReplyForGuess(String word, int limit, String message) {
		chatBot = new ChatBot(new GibbetGameFactory(new Random()));
		chatBot.game = new GibbetGame(word, limit);
		reply = chatBot.reply(message);
		
		replyTypes = new ArrayList<ReplyType>();
		replyTypes.add(ReplyType.show);
	}
	
	@Test
	void replyRightGuess() {
		createReplyForGuess("gibbet", 5, "b");
		checkReply(reply, chatBot.game.showWord(), replyTypes, TurnResult.rightGuess, 0);
	}
	
	@Test
	void replyWrongGuess() {
		createReplyForGuess("gibbet", 5, "s");
		checkReply(reply, chatBot.game.showWord(), replyTypes, TurnResult.wrongGuess, 1);
	}
	
	@Test
	void replyStrangeGuess() {
		createReplyForGuess("gibbet", 5, "!");
		replyTypes.remove(ReplyType.show);
		replyTypes.add(ReplyType.strangeGuess);
		
		checkReply(reply, chatBot.game.showWord(), replyTypes, null, 0);
	}
	
	@Test
	void replyLoss() {
		createReplyForGuess("cat", 2, "b");
		reply = chatBot.reply("d");
		replyTypes.add(ReplyType.loss);
		checkReply(reply, "cat", replyTypes, TurnResult.wrongGuess, 2);
	}

	@Test
	void replyWin() {
		createReplyForGuess("cat", 5, "a");
		chatBot.reply("t");
		reply = chatBot.reply("c");
		replyTypes.add(ReplyType.win);
		
		checkReply(reply, "cat", replyTypes, TurnResult.rightGuess, 0);
	}
}
