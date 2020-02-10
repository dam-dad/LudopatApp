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
	private Player player;
	
	public Solitaire(Deck deck, GameRules gameRules, ArrayList<Player> currentPlayers) {
		super(deck, gameRules, currentPlayers);

		// Sólo hay un jugador
		player = currentPlayers.get(0);
		
		// Hay 4 zonas donde colocar las cartas
		// De la baraja tenemos que sacar los 4 ases y colocarlos en las zonas correspondientes
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
		for( ObjectProperty<Card> oCard : cardsInGame ) {
			
			Card currentCard = oCard.get();
			
			// Cogemos el palo
			if( currentCard.getSuit() == card.getSuit() ) {
				// Tiene que ser la siguiente carta
				if( currentCard.getCardValue() + 1 == card.getCardValue() ) {
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
		for( int i = 0; i < PLAYERCARDS; i++ ) {
			
			Card currentCard = cardsInGame.get(i).get();
			
			if( currentCard.getSuit() == card.getSuit() ) {
				// Cambiamos la carta de la mesa
				cardsInGame.get(i).set(card);
				break;
			}
		}

	}

	@Override
	public void dealCards() {
		
		// Repartimos 4 cartas al jugador, o las que queden en el mazo
		for( int c = 0; c < PLAYERCARDS && deck.getCards().size() > 0; c++ ) {
			player.getHand().add(deck.getCards().remove(c));
		}

	}
	
	public void setController(GameControllerSolitaire controller) {
		this.controller = controller;
	}

	@Override
	public void refreshHand() {
		controller.refreshHand();
	}

}
