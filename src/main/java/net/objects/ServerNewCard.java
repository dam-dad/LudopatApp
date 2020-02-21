package net.objects;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 
 * Paquete para cuando un usuario lanza una carta,
 * que contiene la mano modificada del jugador
 * y la nueva carta sobre la mesa.
 * 
 * @author David Fernández Nieves
 * @author Pablo Daniel Urtiaga Pinto
 * @author Joel Rodriguez Martín
 * @author Kevin Rodriguez Morales
 *
 */
public class ServerNewCard implements Serializable {

	private static final long serialVersionUID = 1L;

	private ArrayList<NET_Card> cards;
	private NET_Card cardInTable;
	
	
	public ServerNewCard(ArrayList<NET_Card> cards, NET_Card cardInTable) {
		this.cards = cards;
		this.cardInTable = cardInTable;
	}

	public ArrayList<NET_Card> getCards() {
		return cards;
	}
	
	public void setCards(ArrayList<NET_Card> cards) {
		this.cards = cards;
	}

	public NET_Card getCardInTable() {
		return cardInTable;
	}

	public void setCardInTable(NET_Card cardInTable) {
		this.cardInTable = cardInTable;
	}


}
