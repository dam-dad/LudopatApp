package gameslib;

import java.util.Comparator;

import games.Player;

public class ComparePlayers implements Comparator<Player> {

	@Override
	public int compare(Player player1, Player player2) {
		return player1.getHand().size() - player2.getHand().size();
	}

}
