package gamesAI;

import java.util.ArrayList;

import SortArrays.SortArrays;
import games.Card;
import games.Player;
import gameslib.Dos;
import javafx.application.Platform;

/**
 * <b>IA</b> <br>
 * <br>
 * 
 * Inteligencia artifical que representa a un jugador en el juego. Su
 * implementación es básica. Está adecuado al juego del DOS
 * 
 * @author David Fernández Nieves
 * @author Pablo Daniel Urtiaga Pinto
 * @author Joel Rodriguez Martín
 * @author Kevin Rodriguez Morales
 *
 */
public class Dav_IA_DOS extends Dav_AI implements Runnable {

	private final int SPECIALS_MIN_VALUE = 11;
	/**
	 * Jugador que representa esta IA
	 */
	private Player player;

	private boolean playableBlueCards = false;
	private boolean playableYellowCards = false;
	private boolean playableWhiteCards = false;
	private boolean playableGreenCards = false;

	public Dav_IA_DOS(Player player) {
		this.player = player;
	}

	public void disconnectAI() {
		setStopAI(true);
	}

	@Override
	public void run() {

		Dos baseGame = (Dos) getBaseGame();

		while (!isStopAI()) {

			try {

				Thread.sleep(AI_SLEEP);
				// Necesitamos el check de si es nuestro turno o no
				if (isOurTurn()) {
					// Tenemos que añadir un retardo
					Thread.sleep(500);
					ArrayList<Card> cards = new ArrayList<Card>();
					resetPlayableCards();

					// Comprobamos las cartas que puede jugar
					for (Card card : player.getHand()) {
						// Comprobamos el color
						if (card.isPlayable() && card.getSuit() != null) {
							if (card.getSuit().getName().contentEquals("blue")) {
								playableBlueCards = true;
							} else {
								if (card.getSuit().getName().contentEquals("yellow")) {
									playableYellowCards = true;
								} else {
									if (card.getSuit().getName().contentEquals("white")) {
										playableWhiteCards = true;
									} else {
										if (card.getSuit().getName().contentEquals("green")) {
											playableGreenCards = true;
										}
									}
								}
							}
						}
						if (card.isPlayable()) {
							cards.add(card);
						}
					}
					// Vemos si tenemos cartas para jugar
					if (cards.size() > 0) {
						// Cartas del mismo palo de la IA
						int[] cardsPerSuit = groupCards();

						// Comprobamos que pueda lanzar cartas que no sean especiales
						if (onlySpecials(cards)) {
							throwRandomCard(cards);
						} else {
							// Puede reservar las especiales
							// Obtenemos el palo con más cartas
							// 0 = blue
							// 1 = yellow
							// 2 = white
							// 3 = green
							int[] codes = checkNumberOfCards(cardsPerSuit);

							int throwableSuit = -1;

							switch (codes[0]) {
							case 0:
								if (playableBlueCards) {
									throwableSuit = 0;
								}
								break;
							case 1:
								if (playableYellowCards) {
									throwableSuit = 1;
								}
								break;
							case 2:
								if (playableWhiteCards) {
									throwableSuit = 2;
								}
								break;
							case 3:
								if (playableGreenCards) {
									throwableSuit = 3;
								}
								break;
							}

							if (throwableSuit == -1) {
								switch (codes[1]) {
								case 0:
									if (playableBlueCards) {
										throwableSuit = 0;
									}
									break;
								case 1:
									if (playableYellowCards) {
										throwableSuit = 1;
									}
									break;
								case 2:
									if (playableWhiteCards) {
										throwableSuit = 2;
									}
									break;
								case 3:
									if (playableGreenCards) {
										throwableSuit = 3;
									}
									break;
								}
							}

							// Si podemos lanzar una carta del palo con más cartas
							// o del segundo la lanzamos y si no, aleatoria
							if (throwableSuit == -1) {
								throwRandomCard(cards);
							} else {
								throwSuitCard(throwableSuit, cards);
							}
						}
					} else {
						// Entonces tenemos que robar
						Platform.runLater(new Runnable() {
							@Override
							public void run() {

								baseGame.drawCard();

							}
						});
					}

					// Antes de pasar turno, esperamos un tiempo
					sleepAI();

					setOurTurn(false);

					// Pasamos turno
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							if (player.getHand().size() > 0) {
								baseGame.nextTurn();
							} else {

								baseGame.controller.endGame();
							}
						}
					});
				}

			} catch (InterruptedException e) {
			}
		}

	}

	private void resetPlayableCards() {
		this.playableBlueCards = false;
		this.playableYellowCards = false;
		this.playableWhiteCards = false;
		this.playableGreenCards = false;
	}

	private int[] groupCards() {
		int[] cardsPerSuit = { 0, 0, 0, 0 };

		for (Card card : player.getHand()) {
			if (card.getSuit() != null) {
				switch (card.getSuit().getName()) {
				case "blue":
					cardsPerSuit[0]++;
					break;
				case "yellow":
					cardsPerSuit[1]++;
					break;
				case "white":
					cardsPerSuit[2]++;
					break;
				case "green":
					cardsPerSuit[3]++;
					break;
				}
			}
		}

		return cardsPerSuit;
	}

	private boolean onlySpecials(ArrayList<Card> cards) {
		for (int i = 0; i < cards.size(); i++) {
			if (cards.get(i).getCardValue() < SPECIALS_MIN_VALUE) {
				return false;
			}
		}
		return true;
	}

	private void throwRandomCard(ArrayList<Card> cards) {
		// Cómo sólo dispone de especiales lanzamos una cualquiera
		int r = (int) (Math.random() * cards.size());
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				baseGame.throwCard(cards.get(r));
				refreshHand();
			}
		});
	}

	private int[] checkNumberOfCards(int[] cardsPerSuit) {
		SortArrays sortArrays = new SortArrays(cardsPerSuit); // Nos creamos el objeto de la clase SortArray
		sortArrays = sortArrays.decreasingOrderInt(); // Llamamos al método que ordena el array de mayor a menor
		int codes[] = sortArrays.getIndexArray(); // Obtengo los indices del array tras ordenarlos
		return codes;
	}

	private void throwSuitCard(int throwableSuit, ArrayList<Card> cards) {

		int i = 0;
		String suitName = null;

		switch (throwableSuit) {
		case 0:
			suitName = "blue";
			break;
		case 1:
			suitName = "yellow";
			break;
		case 2:
			suitName = "white";
			break;
		case 3:
			suitName = "green";
			break;
		}
		while (cards.get(i).getCardValue() >= SPECIALS_MIN_VALUE
				|| !cards.get(i).getSuit().getName().contentEquals(suitName)) {
			i++;
		}

		int pos = i;

		// Lanzamos la carta
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				baseGame.throwCard(cards.get(pos));
				refreshHand();
			}
		});
	}

	private void refreshHand() {
		baseGame.refreshHand();
	}

	public void initTurn() {
		setOurTurn(true);
	}

	@Override
	public void sleepAI() {

		try {
			Thread.sleep((long) (Math.random() * 3000 + 500));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
