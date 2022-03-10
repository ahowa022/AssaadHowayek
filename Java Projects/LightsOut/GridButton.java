// YOUR IMPORT HERE
/*package part02;

import part02.*;*/
import java.util.Random;

import java.io.File;

import java.awt.Color;

import java.awt.Image;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

public class GridButton extends JButton {


    // YOUR VARIABLES HERE
    private int column;
    private int row;
    private static final ImageIcon[] buttonLayout = new ImageIcon[4];


    /**
     * Constructor used for initializing a GridButton at a specific
     * Board location.
     *
     * @param column
     *            the column of this Cell
     * @param row
     *            the row of this Cell
     */

    public GridButton(int column, int row) {
        // YOUR CODE HERE
        this.column = column;
        this.row = row;

        for(int i=0; i<4;i++){
          buttonLayout[i]= new ImageIcon(GridButton.class.getResource("Icons/Light-"+i+".png"));
        }

        ImageIcon thisButtonIcon = buttonLayout[1];
        this.setIcon(thisButtonIcon);
        setBackground(Color.WHITE);
        Border emptyBorder = BorderFactory.createEmptyBorder(0, 0, 0, 0);
	      setBorder(emptyBorder);
	      setBorderPainted(false);

    }

   /**
    * sets the icon of the button to reflect the
    * state specified by the parameters
    * @param isOn true if that location is ON
    * @param isClicked true if that location is
    * tapped in the model's current solution
    */
    public void setState(boolean isOn, boolean isClicked) {
        // YOUR CODE HERE
        if(!isOn && !isClicked){
          this.setIcon(buttonLayout[1]);
        }
        if(!isOn && isClicked){
          this.setIcon(buttonLayout[3]);
        }
        if(isOn && !isClicked){
          this.setIcon(buttonLayout[0]);
        }
        if(isOn && isClicked){
          this.setIcon(buttonLayout[2]);
        }
    }



    /**
     * Getter method for the attribute row.
     *
     * @return the value of the attribute row
     */

    public int getRow() {
        // YOUR CODE HERE
        return row;
    }

    /**
     * Getter method for the attribute column.
     *
     * @return the value of the attribute column
     */

    public int getColumn() {
        // YOUR CODE HERE
        return column;
    }

    // YOUR OTHER METHODS HERE


}
