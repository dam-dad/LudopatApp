package statistics;

import java.util.ArrayList;

public class PlayerStatistic {
	
	private String playerID;
	private String playerName;
	private String playerImage;
	private ArrayList<Integer> scorePerRound = new ArrayList<Integer>();
	private int playerScore;
	
	public PlayerStatistic(String playerID, String playerName, String playerImage) {
		this.playerID = playerID;
		this.playerName = playerName;
		this.playerImage = playerImage;
	}
	
	public void setPoints(int score) {
		this.scorePerRound.add(score);
		this.playerScore = score;
	}
	

	public String getPlayerID() {
		return playerID;
	}

	public String getPlayerName() {
		return playerName;
	}

	public String getPlayerImage() {
		return playerImage;
	}

	public ArrayList<Integer> getScorePerRound() {
		return scorePerRound;
	}

	public int getPlayerScore() {
		return playerScore;
	}
	
	public void sysoStatistics() {
		System.out.println(this.playerID);
		System.out.println(this.playerName);
		System.out.println(this.playerImage);
		System.out.println("Score: " + this.playerScore);
	}
	
}
