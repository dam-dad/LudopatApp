package net.objects;

import java.io.Serializable;
import java.util.ArrayList;

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
