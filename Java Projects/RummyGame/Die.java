import java.util.Random;//Using this import for the random value of dice
public class Die {
    private int dice_value;
    public static final int MAXVALUE=6;

    public Die() {//Constructor methode
      dice_value = (int)(Math.random() * 6 + 1);
    }

    public int getValue() {//Getter (returns value of dice)
      return this.dice_value; //retourne la valeur du dé
   }

   public void roll(){//Rolls de dice (picks random number between 1 and 6)
     this.dice_value = (int)(Math.random() * 6 + 1);
   }

   public String toString(){//Output for when you want to print the object
     String message;
     message = "Die {value:"+this.dice_value+"}";
     return message;
   }


}
