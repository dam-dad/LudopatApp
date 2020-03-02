package statistics;

import java.util.ArrayList;

public class PlayerStatistic {
	
	private String playerID;
	private String playerName;
	private String playerImage;
	private ArrayList<Integer> scorePerRound = new ArrayList<Integer>();
	private Integer handCardNumber;

	
	public PlayerStatistic(String playerID, String playerName, String playerImage) {
		this.playerID = playerID;
		this.playerName = playerName;
		this.playerImage = playerImage;
	}
	
	public void setPoints(int score) {
		this.scorePerRound.add(score);
		this.handCardNumber = score;
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

	public Integer getHandCardNumber() {
		return handCardNumber;
	}
	
	public void sysoStatistics() {
		System.out.println(this.playerID);
		System.out.println(this.playerName);
		System.out.println(this.playerImage);
		System.out.println("Score: " + this.handCardNumber);
	}
	
}
