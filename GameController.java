import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.*;


/**
 * The class <b>GameController</b> is the controller of the game. It is a listener
 * of the view, and has a method <b>play</b> which computes the next
 * step of the game, and  updates model and view.
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */


public class GameController implements ActionListener {

    private GameModel gameModel;
    private GameView gameView;

    /**
     * Constructor used for initializing the controller. It creates the game's view 
     * and the game's model instances
     * 
     * @param width
     *            the width of the board on which the game will be played
     * @param height
     *            the height of the board on which the game will be played
     * @param numberOfMines
     *            the number of mines hidden in the board
     */
    public GameController(int width, int height, int numberOfMines) {
        gameModel = new GameModel(width,height,numberOfMines);
        gameView = new GameView(gameModel, this);
        StudentInfo.display();
    }


    /**
     * Callback used when the user clicks a button (reset or quit)
     *
     * @param e
     *            the ActionEvent
     */

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Reset")){
            gameModel.reset();
            gameView.update();        } 
        else if(e.getActionCommand().equals("Quit")){
            System.exit(0);
        }
        else {
            if (e.getSource() instanceof DotButton){
                DotButton b;
                b = (DotButton) e.getSource();
                play(b.getRow(),b.getColumn());
            }
            
        }
    }

    /**
     * resets the game
     */
    private void reset(){
        gameModel.reset();
        gameView.update();
    }

    /**
     * <b>play</b> is the method called when the user clicks on a square.
     * If that square is not already clicked, then it applies the logic
     * of the game to uncover that square, and possibly end the game if
     * that square was mined, or possibly uncover some other squares. 
     * It then checks if the game
     * is finished, and if so, congratulates the player, showing the number of
     * moves, and gives to options: start a new game, or exit
     * @param width
     *            the selected column
     * @param heigth
     *            the selected line
     */
    private void play(int width, int heigth){
        if (!(gameModel.hasBeenClicked(width,heigth)) && gameModel.isCovered(width,heigth) && !(gameModel.isFinished())){
            gameModel.click(width, heigth);
            gameModel.uncover (width, heigth);
            gameModel.step();
            if(gameModel.isMined(width, heigth)){
                gameModel.uncoverAll();
                gameView.update();
                int reply = JOptionPane.showOptionDialog(null, "Aouch! you lost in " +String.valueOf(gameModel.getNumberOfSteps()) + " steps!\nWould you like to play again?", "BOOM!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"Quit", "Play Again"},"default");
                if (reply == JOptionPane.OK_OPTION) {
                    System.exit(0);
                }
                else {
                    reset();
                }
            }
            else{
                if (gameModel.isBlank(width,heigth))
                    clearZone(gameModel.get(width,heigth));
                    gameView.update();
                if (gameModel.isFinished()){
                    gameModel.uncoverAll();
                    gameView.update();
                    int reply = JOptionPane.showOptionDialog(null, "You won in " +String.valueOf(gameModel.getNumberOfSteps()) + " steps!\nWould you like to play again?", "Won", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"Quit", "Play Again"},"default");
                    if (reply == JOptionPane.OK_OPTION) {
                        System.exit(0);
                    }
                    else {
                        reset();
                    }      
                }
            }
        }
    }

   /**
     * <b>clearZone</b> is the method that computes which new dots should be ``uncovered'' 
     * when a new square with no mine in its neighborood has been selected
     * @param initialDot
     *      the DotInfo object corresponding to the selected DotButton that
     * had zero neighbouring mines
     */
    private void clearZone(DotInfo initialDot) {

        DotInfo temp;
        Stack<DotInfo> stack=new GenericArrayStack<DotInfo>(gameModel.getHeigth()*gameModel.getWidth());
        stack.push(initialDot);
        while(!stack.isEmpty()){
        temp = stack.pop();
        for(int m=temp.getY()-1;m<=(temp.getY()+1);m++){
          for(int n=temp.getX()-1;n<=temp.getX()+1;n++){
            if(0<=m && m<gameModel.getHeigth() && 0<=n && n<gameModel.getWidth()){
                if(gameModel.isCovered(n,m)&& !(gameModel.isMined(n,m))){
                  gameModel.uncover(n,m);
                  if(gameModel.isBlank(n,m)){
                    stack.push(gameModel.get(n,m));
                  }
                }
            }
          }
        }       
      }
    }



}
