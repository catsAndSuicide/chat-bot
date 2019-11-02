package chatbot;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import chatbot.ChatBot.ReplyType;
import chatbot.GibbetGame.TurnResult;

public class ChatBotTest {
	
	private ChatBot createChatBot(String word, int limit) {
		return new ChatBot(new SimpleGibbetGameCreator(word, limit));
	}

	private void checkReply(BotReply actualReply,
							String expectedWord,
							ReplyType[] expectedReplyTypes, 
							TurnResult expectedTurnResult,
							int expectedWrongGuesses) {

		for (ReplyType type : expectedReplyTypes)
			assertTrue(type.toString(), actualReply.replyTypes.contains(type));
		
		assertEquals(expectedWord, actualReply.guessedWord);
		assertEquals(actualReply.turnResult, expectedTurnResult);
		assertEquals(actualReply.wrongGuesses, expectedWrongGuesses);
	}

	@Test
	void replyHelp() {
		var chatBot = createChatBot("someWord", 5);
		var reply = chatBot.reply("/help");
		
		checkReply(reply, "",  
				new ReplyType[] {ReplyType.help}, null, 0);
	}
	
	@Test
	void replyStart() {
		var chatBot = createChatBot("someWord", 5);
		var reply = chatBot.reply("/start");
		
		checkReply(reply, "********", 
				new ReplyType[] {ReplyType.start, ReplyType.show}, null, 0);
	}
	
	@Test
	void replyShowWhenNullGame() {
		var chatBot = createChatBot("someWord", 5);
		var reply = chatBot.reply("/show");
		
		checkReply(reply, "", 
				new ReplyType[] {ReplyType.help}, null, 0);
	}
	
	@Test
	void replyShowWhenGameStarted() {
		var chatBot = createChatBot("someWord", 5);
		chatBot.reply("/start");
		var reply = chatBot.reply("/show");
		
		checkReply(reply, "********", 
				new ReplyType[] {ReplyType.show}, null, 0);
	}
	
	@Test
	void replyEnd() {
		var chatBot = createChatBot("gibbet", 5);
		var reply = chatBot.reply("/end");
		
		checkReply(reply, "", 
				new ReplyType[] {ReplyType.endNotStartedGame, ReplyType.help}, null, 0);
	}
	
	@Test
	void replyRightGuess() {
		var chatBot = createChatBot("gibbet", 5);
		chatBot.reply("/start");
		var reply = chatBot.reply("b");
		
		checkReply(reply, "**bb**", 
				new ReplyType[] {ReplyType.show}, TurnResult.rightGuess, 0);
	}

	@Test
	void replyWrongGuess() {
		var chatBot = createChatBot("gibbet", 5);
		chatBot.reply("/start");
		var reply = chatBot.reply("s");
		
		checkReply(reply, "******", 
				new ReplyType[] {ReplyType.show}, TurnResult.wrongGuess, 1);
	}
	
	@Test
	void replyStrangeGuess() {
		var chatBot = createChatBot("gibbet", 5);
		chatBot.reply("/start");
		var reply = chatBot.reply("!");
		
		checkReply(reply, "******", 
				new ReplyType[] {ReplyType.strangeGuess}, null, 0);
	}
	
	@Test
	void replyLoss() {
		var chatBot = createChatBot("cat", 2);
		chatBot.reply("/start");
		chatBot.reply("e");
		var reply = chatBot.reply("d");
		
		checkReply(reply, "cat", 
				new ReplyType[] {ReplyType.show, ReplyType.loss}, TurnResult.wrongGuess, 2);
	}

	@Test
	void replyWin() {
		var chatBot = createChatBot("cat", 5);
		chatBot.reply("/start");
		chatBot.reply("a");
		chatBot.reply("t");
		var reply = chatBot.reply("c");
		
		checkReply(reply, "cat", 
				new ReplyType[] {ReplyType.show, ReplyType.win}, TurnResult.rightGuess, 0);
	}
}
