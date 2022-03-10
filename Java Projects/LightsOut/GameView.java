/*package part02;

import part02.*;*/
import javax.swing.*;
import java.awt.Checkbox;
import java.awt.Component;
import java.lang.Object;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
// your other import here if needed

/**
 * The class <b>GameView</b> provides the current view of the entire Game. It extends
 * <b>JFrame</b> and lays out a matrix of <b>GridButton</b> (the actual game) and
 * two instances of JButton. The action listener for the buttons is the controller.
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */

public class GameView extends JFrame {
    // your variables here
    private GameModel model;
    private GameController gameController;
    private GridButton[] buttons;
    private JCheckBox solutionBox;
    private JLabel bottom;


    /**
     * Constructor used for initializing the Frame
     *
     * @param gameModel
     *            the model of the game (already initialized)
     * @param gameController
     *            the controller
     */

    public GameView(GameModel gameModel, GameController gameController) {
        // YOUR CODE HERE
        super("LightsOut - Assaad (300057716) & Amin (300059636)");

        buttons = new GridButton[gameModel.getWidth()*gameModel.getHeight()];

        setLayout(new BorderLayout());
        setSize(400,400);
        setBackground(Color.WHITE);
        this.model = gameModel;
        this.gameController = gameController;

        //Add the reset button
        JButton reset = new JButton("Reset");
        reset.addActionListener(gameController);

        //Add the quit button
        JButton quit = new JButton("Quit");
        quit.addActionListener(gameController);
        //Add the randomize button
        JButton randomize = new JButton("Random");
        randomize.addActionListener(gameController);
        //Add solution box
        solutionBox = new JCheckBox("Solution");
        solutionBox.addItemListener(gameController);
		    solutionBox.setBackground(Color.WHITE);

        //JPanel qui contient tout les boutons
        JPanel right = new JPanel(new GridLayout(4,1));
        right.setBackground(Color.WHITE);

        //JPanel that contains each button and checkbox
        JPanel topright = new JPanel();
        topright.setBackground(Color.WHITE);
        JPanel midtopright = new JPanel();
        midtopright.setBackground(Color.WHITE);
        JPanel midbottomright = new JPanel();
        midbottomright.setBackground(Color.WHITE);
        JPanel bottomright = new JPanel();
        bottomright.setBackground(Color.WHITE);

        bottom = new JLabel("Number of steps: 0");


        //add buttons and checkboxes to those JPanels
        topright.add(reset);
        midtopright.add(randomize);
        midbottomright.add(solutionBox);
        bottomright.add(quit);

        //add subJPanels that contain buttons in the main right panel
        right.add(topright);
        right.add(midtopright);
        right.add(midbottomright);
        right.add(bottomright);
        right.setBackground(Color.WHITE);

        //create the panel that contains all the GridButtons
        JPanel mainPanel  = new JPanel(new GridLayout(model.getHeight(),model.getWidth()));
        int total = model.getHeight()*model.getWidth();
        for (int i = 0; i < total; i++){
          int column = i%model.getWidth();
          int row = i / model.getWidth();
          GridButton buttonDummy = new GridButton(column, row);
          buttons[i] = buttonDummy;
          buttonDummy.addActionListener(gameController);
          mainPanel.add(buttonDummy);
        }


        mainPanel.setBackground(Color.WHITE);

        //create a panel that contains JLabel that keeps tracks of steps
        JPanel bottomContain = new JPanel();
        bottomContain.add(bottom);
        bottomContain.setBackground(Color.WHITE);

        //add Jpanel that contains the label and other that contains gridButton
        add(right, BorderLayout.EAST);
        add(bottomContain, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * updates the status of the board's GridButton instances based
     * on the current game model, then redraws the view
     */

    public void update(){
        // YOUR CODE HERE

        if(solutionShown()){
          updateSolution();
        } else {
          updateNormal();
        }
        if (model.isFinished()){
          endGame();
        }
    }

    private void updateSolution(){
      //updates the buttons the way they should be updated when solution checkbox is checked
      int total = model.getHeight()*model.getWidth();
      for(int i =0; i<total; i++){
        int column = i%model.getWidth();
        int row = i / model.getWidth();
        buttons[i].setState(model.isON(row,column), model.getSolutionBoolean(row,column));
        bottom.setText("Number of steps: " + Integer.toString(model.getNumberOfSteps()));
      }
    }

    private void updateNormal(){
      //updates the buttons the way they should be updates when solution box is not checked
      //increments the amount of steps
      int total = model.getHeight()*model.getWidth();
      for (int i = 0; i < total ; i++){
        int column = i%model.getWidth();
        int row = i / model.getWidth();
        buttons[i].setState(model.isON(row,column),false);
        bottom.setText("Number of steps: " + Integer.toString(model.getNumberOfSteps()));
      }
    }

    private void endGame(){
      // code for the ending of the game if he wants to play again box resets
      // and if he doesnt we kill the whole code
      String[] options ={"Quit", "Play Again"};
      int answer = JOptionPane.showOptionDialog(null,"You have beat the game in "+ model.getNumberOfSteps()+
      " steps, Congratulations do you want to play again","Winner!!!", JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE, null, options,options[0]);
      if(answer == 0){
        System.exit(0);
      } else {
        model.reset();
        update();
      }
    }

    /**
     * returns true if the ``solution'' checkbox
     * is checked
     *
     * @return the status of the ``solution'' checkbox
     */

    public boolean solutionShown(){
        // YOUR CODE HERE
        return solutionBox.isSelected();
    }

}
