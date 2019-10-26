package chatbot;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Random;

import org.junit.jupiter.api.Test;

import chatbot.ChatBot.ReplyType;
import chatbot.GibbetGame.TurnResult;

public class ChatBotTest {
	
	private ChatBot chatBot = new ChatBot(new GibbetGameFactory(new Random()));

	private void checkReply(BotReply reply,
							String guessedWord,
							ReplyType[] replyTypes, 
							TurnResult turnResult,
							int wrongGuesses) {

		for (ReplyType type : replyTypes)
			assertTrue(reply.replyTypes.contains(type));
		
		assertEquals(reply.guessedWord, guessedWord);
		assertEquals(reply.turnResult, turnResult);
		assertEquals(reply.wrongGuesses, wrongGuesses);
	}

	@Test
	void replyHelp() {
		var reply = chatBot.reply("/help");
		checkReply(reply, "",  
				new ReplyType[] {ReplyType.help}, null, 0);
	}
	
	@Test
	void replyStart() {
		var reply = chatBot.reply("/start");
		checkReply(reply, chatBot.game.showWord(), 
				new ReplyType[] {ReplyType.start, ReplyType.show}, null, 0);
	}
	
	@Test
	void replyShowWhenNullGame() {
		var reply = chatBot.reply("/show");
		checkReply(reply, "", 
				new ReplyType[] {ReplyType.help}, null, 0);
	}
	
	@Test
	void replyShowWhenGameStarted() {
		chatBot.reply("/start");
		var reply = chatBot.reply("/show");
		checkReply(reply, chatBot.game.showWord(), 
				new ReplyType[] {ReplyType.show}, null, 0);
	}
	
	@Test
	void replyEnd() {
		chatBot.game = new GibbetGame("gibbet", 5);
		var reply = chatBot.reply("/end");
		checkReply(reply, "", 
				new ReplyType[] {ReplyType.endNotStartedGame, ReplyType.help}, null, 0);
	}
	
	@Test
	void replyRightGuess() {
		chatBot.game = new GibbetGame("gibbet", 5);
		var reply = chatBot.reply("b");
		
		checkReply(reply, chatBot.game.showWord(), 
				new ReplyType[] {ReplyType.show}, TurnResult.rightGuess, 0);
	}
	
	@Test
	void replyWrongGuess() {
		chatBot.game = new GibbetGame("gibbet", 5);
		var reply = chatBot.reply("s");
		
		checkReply(reply, chatBot.game.showWord(), 
				new ReplyType[] {ReplyType.show}, TurnResult.wrongGuess, 1);
	}
	
	@Test
	void replyStrangeGuess() {
		chatBot.game = new GibbetGame("gibbet", 5);
		var reply = chatBot.reply("!");
		
		checkReply(reply, chatBot.game.showWord(), 
				new ReplyType[] {ReplyType.strangeGuess}, null, 0);
	}
	
	@Test
	void replyLoss() {
		chatBot.game = new GibbetGame("cat", 2);
		chatBot.reply("e");
		var reply = chatBot.reply("d");

		checkReply(reply, "cat", 
				new ReplyType[] {ReplyType.show, ReplyType.loss}, TurnResult.wrongGuess, 2);
	}

	@Test
	void replyWin() {
		chatBot.game = new GibbetGame("cat", 5);
		chatBot.reply("a");
		chatBot.reply("t");
		var reply = chatBot.reply("c");
		
		checkReply(reply, "cat", 
				new ReplyType[] {ReplyType.show, ReplyType.win}, TurnResult.rightGuess, 0);
	}
}
