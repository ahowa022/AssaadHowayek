public class Game{
  private Die dice;
  private Deck player;
  private Deck game;

  public Game (int value){
    game = new Deck(value);
    player = new Deck();
    dice = new Die();
  }

  //this is where the game goes down when player rolls other than 1
  private void jeuValeurPasUn(){
    boolean play = true,question, questionp2;
    Deck discardableDeck;
    Card discardableCard;
    if(Utils.readYesOrNo("Do you  have a sequences of three or more cards of the same suit or two or more of a kind?")==false){
      play = false;
    } else {
      System.out.println("Which 3+ sequence or 2+ of a kind would you like to discard? ");
    }
    discardableDeck = new Deck();//discardableDeck where user can input cards he wants to discard
    while(play){
      //use utils function to read card
      discardableCard = Utils.readCard();
      //add card to the discardable deck
      discardableDeck.add(discardableCard);
      //Asks user if he has more cards to either continue imputing
      question = Utils.readYesOrNo("Do you have more cards?");//if he says yes we just go right back to beginning of this while loop

      //if the user does not want to input any more cards we go in this function
      if (question==false){
        //if the cards inputed by the user are not in the players deck they cannot be discarded
        if (player.containsAll(discardableDeck)==false){
          System.out.println("Cannot discard those cards!");
          questionp2 = Utils.readYesOrNo("Do you  have a sequences of three or more cards of the same suit or two or more of a kind?");
          //if the user does not want to continue he can go back to the game
          if (questionp2==false){
            play= false;
          } else {
            System.out.println("Which 3+ sequence or 2+ of a kind would you like to discard? ");
          }

          discardableDeck = new Deck();// resets deck so the cards inputed previously arent still in there

          //if you get to this else statement that means the cards inputed by the user are present in the deck
        } else {
          //checks if cards are kind or sequence and if not prints that these cards cannot be discarded
          if (discardableDeck.isKind()){
            player.removeAll(discardableDeck);
            System.out.println("Here is your new deck printed in two ways: ");
            player.print();
          } else if (discardableDeck.isSeq()){
            player.removeAll(discardableDeck);
            System.out.println("Here is your new deck printed in two ways: ");
            player.print();
          } else {
            System.out.println("Cannot discard those cards!");
          }
          //asks the user if he wants to back in the game
          questionp2 = Utils.readYesOrNo("Do you  have a sequences of three or more cards of the same suit or two or more of a kind?");
          //goes back in the game because he doesnt want to input more cards to discard
          if (questionp2==false){
            play= false;
          } else {
            System.out.println("Which 3+ sequence or 2+ of a kind would you like to discard? ");
          }

          discardableDeck = new Deck();//resets deck so the cards he previously inputed arent still in there
        }
        }
        }
      }

    private void jeuValeurUn(){
        Card oneCard;
        boolean gamePlay=true;
        System.out.println("Discard any cards of your choosing");
        while (gamePlay){
        System.out.println("Which card would you like to discard");
            oneCard=Utils.readCard();//gets a card
            if (player.contains(oneCard)){//if card is in players hand remove it
                player.remove(oneCard);//removes card
                System.out.println("Here is your new deck printed in two ways: ");
                player.print();
                gamePlay=false;
            }
            else{//if card given isnt in the players hand, tells this to the player and asks him to try again
                 System.out.println("No such card in your hand. Try again.");
            }
        }
    }








  public void play(){
    game.shuffle();
    player = game.deal(7);
    System.out.println("Here is your new deck printed in two ways: ");
    player.print();
    int round=1;

    boolean flag = true;
    while (flag){
      dice.roll();
      if(game.size()>0){
        //general code
        System.out.println("Rolling the die!");
        System.out.println("The die has value " + dice.getValue());

        //game when dice value is one follow the helper functions
        if(dice.getValue()!=1){
          System.out.println("Adding (up to) "+ dice.getValue()+" to your hand.");
          player.addAll(game.deal(dice.getValue()));
          System.out.println("Here is your new deck printed in two ways: ");
          player.print();
          jeuValeurPasUn();
        }
        else{//if dice is 1 go into the helper function
            System.out.println("Here is your new deck printed in two ways: ");
            player.print();
            jeuValeurUn();

        }




      }
      else{//if the deck is empty
          System.out.println("The game is in empty deck phase");
          jeuValeurUn();
          System.out.println("Round "+round+ " completed!");
      }
        if(player.size()==0){//if the players deck is empty means game is done
            System.out.println("Congratulations you have beaten the game in "+round+" rounds");
            flag=false;
        }
        else{//if player deck isnt empty add one to the round numbers
            round++;
        }

    }


  }

}
