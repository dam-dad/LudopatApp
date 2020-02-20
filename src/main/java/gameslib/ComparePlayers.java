package gameslib;

import java.util.Comparator;

import games.Player;
/**
 * <b>ComparePlayers</b> <br>
 * <br>
 * 
 * Comparador de puntuación de jugadores usado para
 * determinar las puntuaciones finales en DOS.
 * 
 * @author David Fernández Nieves
 * @author Pablo Daniel Urtiaga Pinto
 * @author Joel Rodriguez Martín
 * @author Kevin Rodriguez Morales
 *
 */
public class ComparePlayers implements Comparator<Player> {
	
	@Override
	public int compare(Player player1, Player player2) {
		return player1.getHand().size() - player2.getHand().size();
	}

}
