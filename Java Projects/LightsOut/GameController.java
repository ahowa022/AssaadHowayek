import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

// YOUR OTHER IMPORTS HERE IF NEEDED

/**
 * The class <b>GameController</b> is the controller of the game. It is a listener
 * of the view, and has a method <b>play</b> which computes the next
 * step of the game, and  updates model and view.
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */


public class GameController implements ActionListener, ItemListener {

    // YOUR VARIABLES HERE
    private int width, height;
    private GameModel model;
    private GameView visibility;

    /**
     * Constructor used for initializing the controller. It creates the game's view
     * and the game's model instances
     *
     * @param width
     *            the width of the board on which the game will be played
     * @param height
     *            the height of the board on which the game will be played
     */
    public GameController(int width, int height) {
        // YOUR CODE HERE
        this.width = width;
        this.height= height;
        this.model = new GameModel(width, height);
        visibility = new GameView(model, this);
    }


    /**
     * Callback used when the user clicks a button (reset,
     * random or quit)
     *
     * @param e
     *            the ActionEvent
     */

    public void actionPerformed(ActionEvent e) {

        // YOUR CODE HERE
        if (e.getActionCommand().equals("Reset")){
          model.reset();
          play();
        }
        if (e.getActionCommand().equals("Random")){
          model.randomize();
          play();
        }
        if (e.getActionCommand().equals("Quit")){
          System.exit(0);
        }

        if (e.getSource() instanceof GridButton){
          GridButton tmpButton = (GridButton) e.getSource();
          int row, col;
          row = tmpButton.getRow();
          col = tmpButton.getColumn();
          //System.out.println("you are in row "+ row + " you are in col " +col);
          model.click(row,col);
          play();
        }
    }

    /**
     * Callback used when the user select/unselects
     * a checkbox
     *
     * @param e
     *            the ItemEvent
     */

    public void itemStateChanged(ItemEvent e){
        // YOU CODE HERE
        if (e.getStateChange() == ItemEvent.SELECTED){
          play();
        } else {
          play();
        }

    }

    // YOUR OTHER METHODS HERE
    public void play(){
      visibility.update();
    }

}
