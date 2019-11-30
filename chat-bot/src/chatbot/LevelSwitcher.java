package chatbot;

public interface LevelSwitcher {
	public void registerLoss();
	public void registerWin();
	public boolean canStartLevel(int level);
}
