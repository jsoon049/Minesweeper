import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * The class <b>GameView</b> provides the current view of the entire Game. It extends
 * <b>JFrame</b> and lays out a matrix of <b>DotButton</b> (the actual game) and 
 * two instances of JButton. The action listener for the buttons is the controller.
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */

public class GameView extends JFrame {

    private GameModel gameModel;
    private DotButton[][] board;
    private javax.swing.JLabel nbreOfStepsLabel;

    /**
     * Constructor used for initializing the Frame
     * 
     * @param gameModel
     *            the model of the game (already initialized)
     * @param gameController
     *            the controller
     */

    public GameView(GameModel gameModel, GameController gameController) {
        super("Minesweeper ITI 1121");
        this.gameModel = gameModel;
        //create 2d grid
        int columns=gameModel.getHeigth();
        int rows=gameModel.getWidth();
        JPanel myPanel;
        myPanel=new JPanel(new GridLayout(columns,rows));
        board=new DotButton[columns][rows];
        for(int i=0;i<columns;i++){
          for(int j=0;j<rows;j++){
            board[i][j]=new DotButton(i,j,11);
            board[i][j].addActionListener(gameController);
            myPanel.add(board[i][j]);
          }
        }
        nbreOfStepsLabel = new JLabel("Number of steps "+gameModel.getNumberOfSteps());
        //create bottom part of grid
        JPanel options=new JPanel();
        options.add(nbreOfStepsLabel);
        JButton quit=new JButton("Quit");
        quit.addActionListener(gameController);
        JButton reset=new JButton("Reset");
        reset.addActionListener(gameController);
        options.add(reset);
        options.add(quit);
        //create outer border for grid
        JPanel main=new JPanel();
        main.add(myPanel);
        main.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));
        //add grids to frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(main,BorderLayout.CENTER);
        add(options,BorderLayout.SOUTH);
        pack();
        setVisible(true);
    }

    /**
     * update the status of the board's DotButton instances based 
     * on the current game model, then redraws the view
     */

    public void update(){
        for (int i = 0; i < gameModel.getHeigth();i++){
            for (int j = 0; j < gameModel.getWidth(); j ++){
                //System.out.print((gameModel.isCovered(j,i)));
                if (!(gameModel.isCovered(j,i))){
                    board[i][j].setIconNumber(getIcon(i,j));
                }
                else{
                    if (gameModel.getNumberOfSteps() == 0)
                        board[i][j].setIconNumber(11);
                }

            }
        }
        nbreOfStepsLabel.setText("Number of steps "+gameModel.getNumberOfSteps());
        repaint();
    }

    /**
     * returns the icon value that must be used for a given dot 
     * in the game
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the icon to use for the dot at location (i,j)
     */   
    private int getIcon(int i, int j){
        if (gameModel.isMined(j,i)){
            if (gameModel.hasBeenClicked(j,i))
                return 10;
            else
                return 9;
        }
        return gameModel.getNeighbooringMines(j,i);
    }


}
