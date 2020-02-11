package gameslib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import games.Card;
import games.Deck;
import games.Game;
import games.GameRules;
import games.Player;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Solitaire extends Game {

	GameControllerSolitaire controller;

	private final static int PLAYERCARDS = 4;

	private ArrayList<ObjectProperty<Card>> cardsInGame = new ArrayList<ObjectProperty<Card>>();
	private ArrayList<Card> discardedCards = new ArrayList<Card>();
	private Card savedCard;
	private Player player;

	public Solitaire(Deck deck, GameRules gameRules, ArrayList<Player> currentPlayers) {
		super(deck, gameRules, currentPlayers);

		// Sólo hay un jugador
		player = currentPlayers.get(0);

		// Hay 4 zonas donde colocar las cartas
		// De la baraja tenemos que sacar los 4 ases y colocarlos en las zonas
		// correspondientes
		for (int i = 0; i < PLAYERCARDS; i++) {

			Optional<Card> opCard = deck.getCards().stream().filter(c -> (c.getCardValue() == 1)).findFirst();

			if (opCard.isPresent()) {
				deck.getCards().remove(opCard.get());
				cardsInGame.add(new SimpleObjectProperty<Card>(opCard.get()));
			}
		}

		// Barajamos las cartas
		deck.shuffle();

		// Se reparten las 4 cartas
		dealCards();
	}

	public void checkTable(Card card) {

		// Tenemos que comprobar las 4 zonas
		for (ObjectProperty<Card> oCard : cardsInGame) {

			Card currentCard = oCard.get();

			// Cogemos el palo
			if (currentCard.getSuit() == card.getSuit()) {
				// Tiene que ser la siguiente carta
				if (currentCard.getCardValue() + 1 == card.getCardValue()) {
					// Entonces puede poner la carta
					card.setPlayable(true);
				}

				else {
					card.setPlayable(false);
				}
			} else {
				card.setPlayable(false);
			}
		}

	}

	@Override
	public void initGame() {
		// TODO Auto-generated method stub

	}

	@Override
	public void endGame() {
		// TODO Auto-generated method stub

	}

	@Override
	public void throwCard(Card card) {

		// Lanzamos la carta a la zona correspondiente
		for (int i = 0; i < PLAYERCARDS; i++) {

			Card currentCard = cardsInGame.get(i).get();

			if (currentCard.getSuit() == card.getSuit()) {
				// Cambiamos la carta de la mesa
				cardsInGame.get(i).set(card);
				break;
			}
		}

	}

	public void saveCard(Card card) {
		if (savedCard == null) {
			setSavedCard(card);
		}
	}

	@Override
	public void dealCards() {
		// Repartimos 4 cartas al jugador, o las que queden en el mazo
		for (int c = 0; c < PLAYERCARDS && deck.getCards().size() > 0; c++) {
			player.getHand().add(deck.getCards().remove(c));
		}

	}

	public void reshuffle() {
		for (int i = 0; i < discardedCards.size() - 1; i++) {
			deck.getCards().add(discardedCards.get(i));
		}
		deck.shuffle();
	}

	public void setController(GameControllerSolitaire controller) {
		this.controller = controller;
	}

	@Override
	public void refreshHand() {
		controller.refreshHand();
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public ArrayList<Card> getDiscardedCards() {
		return discardedCards;
	}

	public void setDiscardedCards(ArrayList<Card> discardedCards) {
		this.discardedCards = discardedCards;
	}

	public Card getSavedCard() {
		return savedCard;
	}

	public void setSavedCard(Card savedCard) {
		this.savedCard = savedCard;
	}
}
