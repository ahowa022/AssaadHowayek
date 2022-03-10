import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    private ArrayList<Card> cards;

    /**
    * A constructor for the class <code>Deck</code>. It creates the initial
    * <code>ArrayList</code> that will be used to store the cards of Otherwise
    * deck.
    */

    public Deck() {//constructor creates an empty deck
        cards = new ArrayList<Card>();
    }

    /**
    * A constructor for the class <code>Deck</code>. It creates the initial
    * <code>ArrayList</code> that will be used to store the cards of Otherwise
    * deck. The parameter specifies the number of ranks for the cards. Finally,
    * it also initializes this deck to contain 4 x n cards, where n is the value
    * of the parameter.
    *
    * @param range the number of ranks for the cards
    */

    public Deck(int range) {
      cards = new ArrayList<Card>();
      for(int i=1; i<range+1; i++){//loop that creates the deck of cards 0n operations
        cards.add(new Card(0,i));
        cards.add(new Card(1,i));
        cards.add(new Card(2,i));
        cards.add(new Card(3,i));
      }
        // Complete the implementation of this constructor
    }

    // Add all the necessary methods here
    public int size(){//getter
      return cards.size();
    }

    public boolean hasCards(){
      int x = cards.size();
      if (x>0){  //returns true if theres cards in deck false otherwise
        return true;
      } else {
        return false;
      }
      }

    public Card get(int pos){
      return cards.get(pos);//getter
    }

    public void add(Card card){ //add card to end of deck
      cards.add(card);
    }

    public void addAll(Deck other){
      int length = other.cards.size();
      //goes through deck other and adds it top to bottom to bottom of deck cards
      for (int i=0; i<length; i++){
        cards.add(other.cards.get(i));
      }
      //empties the deck other
      for (int i=0; i<length; i++){
        other.cards.remove(0);
      }
    }

    public Card removeLast(){
      Card temp;
      //prend la valeur de la derniere carte avant de la retirer
      temp = cards.get(cards.size()-1);
      cards.remove(cards.size()-1);
      return temp;
    }

    public Card removeFirst(){
      //same comments as remove Last
      Card temp;
      temp = cards.get(0);
      cards.remove(0);
      return temp;
    }

    public boolean remove(Card card){
      //removes card if present in deck and returns true
      for(int i=0; i<cards.size();i++){
        if (card.equals(cards.get(i))){
          cards.remove(i);
          return true;
        }
      }
      return false;//if u get to this line card not in deck so returns false
    }

    public void removeAll(Deck other){
      int j=0;
      boolean flag=true;
      //first loop goes through every card in deck other
      for(int i=0; i<other.cards.size(); i++){
        while(j<cards.size() && flag==true){// goes through every card in deck and verifies if card from other in there
          if(cards.get(j).equals(other.cards.get(i))){//if card found we remove it from deck cards and break the loop
            cards.remove(j);
            flag= false;
          }
          j++;
        }
        j=0;//reset the values to back in while loop
        flag=true;
      }
    }

    public void shuffle(){
      Collections.shuffle(cards);
    }

    public Deck deal(int n){
      Deck tmp;
      tmp = new Deck();
      int i=0;
      while(cards.size()!=0 && i<n){//these two conditions are essential so u cannot deal cards if deck empty
        tmp.add(cards.get(cards.size()-1)); //and if theres enough cards in the deck to deal n cards then itll happen
        removeLast();//after adding card from bottom of deck we remove it
        i++;
      }
      return tmp;//returns new deck that is dealt from deck cards
    }

    public boolean contains(Card card){
      for(int i=0; i<cards.size();i++){//goes through each element and returns true if card present
        if(card.equals(cards.get(i))){//returns false otherwise
          return true;
        }
      }
      return false;
    }

    public boolean containsAll(Deck other){
      for (int i=0; i<other.cards.size();i++){//goes through each card from deck other
        if (cards.contains(other.cards.get(i))==false){//if one element of deck other not present in deck cards returns false
          return false;
        }
      }
      return true;// if u reach this line that means all cards are present from deck other so returns true
    }

    public boolean isKind(){
      int value;
      if (cards.size()<2){//need at least two cards to have kind
        return false;
      }
      value = cards.get(0).getRank();//gets rank of first card
      for (int i=1;i<cards.size();i++){
        if(cards.get(i).getRank()!=value){//if other cards dont have same rank return false since its not kind
          return false;
        }
      }
      return true;//if u reach this line that means they have the same rank returns true
    }

    public boolean isSeq(){
      int value;
      value = cards.get(0).getSuit();
      //check if theyre same suits
      if (cards.size()<3){//need at least three cards for seq
        return false;
      }
      for (int i=1;i<cards.size();i++){//if cards in deck dont have same suit then its not sequence
        if(cards.get(i).getSuit()!=value){
          return false;
        }
      }


      selectionSortRank(); // sorts deck cards by rank

      for(int i=0; i<cards.size()-1;i++){//after sorting cards by rank the loop verifies if its sequence i.e (9,10,11)
        if(cards.get(i).getRank()+1 != cards.get(i+1).getRank()){
          return false;
        }
      }
      return true;
    }


    private void selectionSortRank(){ //helper functions that sorts deck by rank code exact same as lab
		Card temporary;
		int minArg;
		for(int counter=0; counter<cards.size()-1;counter++){
			minArg = counter;
			for (int counter2=counter+1;counter2<cards.size();counter2++){
				if (cards.get(counter2).getRank()<cards.get(minArg).getRank()){
					minArg=counter2;
				}
			}
			temporary=cards.get(minArg);
      cards.set(minArg, cards.get(counter));
      cards.set(counter,temporary);
		}
	}



  public void sortBySuit(){
    selectionSortRank();//sort by rank, need to do this so the loop later sorts them by suit but ranks still in order
    Deck temp;
    temp=new Deck();
    for (int i=0; i< 4;i++){//goes through each suit
      for(int j=0; j<cards.size();j++){//if card that has that suit add it to temp deck
        if (get(j).getSuit()==i){
          temp.add(get(j));
        }
      }

        }
    this.cards=temp.cards;//give current deck the address of sorted deck
  }

  public void sortByRank(){
    sortBySuit();//sort by suits, need to do this so the loop later sorts them by rank but suits still in order
    Deck newDeck;
    newDeck= new Deck();
    for (int i=1; i<100; i++){//goes through each rank
      for(int j=0;j<cards.size();j++){
        if (cards.get(j).getRank()==i){//if card that has that rank add it to temp deck
          newDeck.add(cards.get(j));
        }
      }
    }
    cards=newDeck.cards;//give current deck the address of sorted deck
  }

  public void print(){
    sortByRank();
    System.out.println("Deck "+cards);
    sortBySuit();
    System.out.println("Deck "+cards);
  }

  public String toString(){
    String message="",tmp="";
    for (int i=0; i<cards.size();i++){
      tmp = cards.get(i).toString();
      message = message+" "+tmp;//loop that adds each card into string
    }
    return message;
  }
    }
