package chatbot;

public class SimpleLevelSwitcher implements LevelSwitcher {
	private boolean gameWasWon = false;
	
	public void registerLoss() {
		return;
	}
	public void registerWin() {
		gameWasWon = true;
	}
	public boolean canStartLevel(int level) {
		if (level == 0 || (level == 1 && gameWasWon))
			return true;
		return false;
			
	}
}
