package chatbot;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import chatbot.ChatBot.ReplyType;
import chatbot.GibbetGame.TurnResult;

public class ChatBotTest {
	
	private ChatBot createChatBot(String word, int limit) {
		return new ChatBot(new SimpleGibbetGameFactory(word, limit), new SimpleLevelSwitcher(), null);
	}

	private void checkReply(BotReply actualReply,
							String expectedWord,
							ReplyType[] expectedReplyTypes, 
							TurnResult expectedTurnResult,
							int expectedWrongGuesses) {

		for (ReplyType type : expectedReplyTypes)
			assertTrue(type.toString(), actualReply.replyTypes.contains(type));
		
		assertEquals(expectedWord, actualReply.guessedWord);
		assertEquals(expectedTurnResult, actualReply.turnResult);
		assertEquals(expectedWrongGuesses, actualReply.wrongGuesses);
	}

	@Test
	void replyHelp() {
		var chatBot = createChatBot("gibbet", 5);
		var reply = chatBot.reply("/help", null);
		
		checkReply(reply, "",  
				new ReplyType[] {ReplyType.help}, null, 0);
	}
	
	@Test
	void replyStart() {
		var chatBot = createChatBot("gibbet", 5);
		var reply = chatBot.reply("/start", null);
		
		checkReply(reply, "******", 
				new ReplyType[] {ReplyType.start, ReplyType.show}, null, 0);
	}
	
	@Test
	void replyShow() {
		var chatBot = createChatBot("gibbet", 5);
		chatBot.reply("/start", null);
		var reply = chatBot.reply("/show", null);
		
		checkReply(reply, "******", 
				new ReplyType[] {ReplyType.show}, null, 0);
	}
	
	@Test
	void replyEnd() {
		var chatBot = createChatBot("gibbet", 5);
		var reply = chatBot.reply("/end", null);
		
		checkReply(reply, "", 
				new ReplyType[] {ReplyType.endNotStartedGame, ReplyType.help}, null, 0);
	}
	
	@Test
	void replyRightGuess() {
		var chatBot = createChatBot("gibbet", 5);
		chatBot.reply("/start", null);
		var reply = chatBot.reply("b", null);
		
		checkReply(reply, "**bb**", 
				new ReplyType[] {ReplyType.show}, TurnResult.rightGuess, 0);
	}

	@Test
	void replyWrongGuess() {
		var chatBot = createChatBot("gibbet", 5);
		chatBot.reply("/start", null);
		var reply = chatBot.reply("s", null);
		
		checkReply(reply, "******", 
				new ReplyType[] {ReplyType.show}, TurnResult.wrongGuess, 1);
	}
	
	@Test
	void replyStrangeGuess() {
		var chatBot = createChatBot("gibbet", 5);
		chatBot.reply("/start", null);
		var reply = chatBot.reply("!", null);
		
		checkReply(reply, "******", 
				new ReplyType[] {ReplyType.strangeGuess}, null, 0);
	}
	
	@Test
	void replyLoss() {
		var chatBot = createChatBot("cat", 2);
		chatBot.reply("/start", null);
		chatBot.reply("e", null);
		var reply = chatBot.reply("d", null);
		
		checkReply(reply, "cat", 
				new ReplyType[] {ReplyType.show, ReplyType.loss}, TurnResult.wrongGuess, 2);
	}

	@Test
	void replyWin() {
		var chatBot = createChatBot("cat", 5);
		chatBot.reply("/start", null);
		chatBot.reply("a", null);
		chatBot.reply("t", null);
		var reply = chatBot.reply("c", null);
		
		checkReply(reply, "cat", 
				new ReplyType[] {ReplyType.show, ReplyType.win}, TurnResult.rightGuess, 0);
	}
	
	@Test
	void hardLevelClosed() {
		var chatBot = createChatBot("cat", 5);
		var reply = chatBot.reply("/start hard", null);
		
		checkReply(reply, "",  
				new ReplyType[] {ReplyType.closedLevel}, null, 0);
	}
	
	@Test
	void hardLevelOpened() {
		var chatBot = createChatBot("cat", 5);
		chatBot.reply("/start", null);
		chatBot.reply("a", null);
		chatBot.reply("t", null);
		chatBot.reply("c", null);
		var reply = chatBot.reply("/start hard", null);
		
		assertTrue(reply.replyTypes.contains(ReplyType.start));
	}
	
	@Test
	void checkReplyForShow() {
		var chatBot = createChatBot("gibbet", 5);
		chatBot.reply("/start", null);
		var reply = chatBot.reply("/show", null);
		
		assertArrayEquals(reply.replyTypes.toArray(), new ReplyType[] {ReplyType.show});
	}
}
