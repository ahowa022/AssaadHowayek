public class Card {

    private int suit;
    private int rank;
    public static final int DIAMOND=0, CLUB=1, HEART=2, SPADE=3;

    public Card(int suit, int rank){//constructor
    this.suit=suit;//Initialise the suit of the card
    this.rank=rank;//Initialise the rank of the card
    }


public boolean equals(Object object){//Method that returns true if both objects are the same
	if (! (object instanceof Card)) {
      return false;
	}

	Card other;
	other = (Card) object;

    return this.suit == other.suit && this.rank==other.rank;

}
    public String toString(){//Method which gives output for system.out.println
        return "{"+this.suit+","+this.rank+"}";
    }

    public int getSuit(){//Getter (gets the value of the suit)
        return this.suit;
    }
    public int getRank(){//Getter (gets the value of the rank)
        return this.rank;
    }


}
