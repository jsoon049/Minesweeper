import java.util.Random;

/**
 * The class <b>GameModel</b> holds the model, the state of the systems. 
 * It stores the following information:
 * - the state of all the ``dots'' on the board (mined or not, clicked
 * or not, number of neighbooring mines...)
 * - the size of the board
 * - the number of steps since the last reset
 *
 * The model provides all of this informations to the other classes trough 
 *  appropriate Getters. 
 * The controller can also update the model through Setters.
 * Finally, the model is also in charge of initializing the game
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */
public class GameModel {

    private int widthOfGame;
    private int heigthOfGame;
    private int numberOfMines;
    private int numberOfSteps;
    private int numberUncovered;
    private DotInfo[][] model;
    private Random generator=new Random();


    /**
     * Constructor to initialize the model to a given size of board.
     * 
     * @param width
     *            the width of the board
     * 
     * @param heigth
     *            the heigth of the board
     * 
     * @param numberOfMines
     *            the number of mines to hide in the board
     */
    public GameModel(int width, int heigth, int numberOfMines){
      this.widthOfGame = width;
      this.heigthOfGame = heigth;
      this.numberOfMines = numberOfMines;
      this.model = new DotInfo[heigthOfGame][widthOfGame];
      numberUncovered = 0;
      numberOfSteps =0;
      reset();
    }


 
    /**
     * Resets the model to (re)start a game. The previous game (if there is one)
     * is cleared up . 
     */
    public void reset(){
      for (int i = 0; i < heigthOfGame; i++){
        for (int j = 0; j < widthOfGame; j++){
            this.model[i][j] = new DotInfo(j,i);
        }
      }
      numberOfSteps=0;
      numberUncovered=0;
      int i=0;
      while(i<numberOfMines){
        int height=generator.nextInt(heigthOfGame);
        int width=generator.nextInt(widthOfGame);
        if(!(model[height][width].isMined())){
            model[height][width].setMined();
            for(int m=height-1;m<=(height+1);m++){
              for(int n=width-1;n<=width+1;n++){
                if(0<=m && m<heigthOfGame && 0<=n && n<widthOfGame){
                    model[m][n].setNeighbooringMines(1 + model[m][n].getNeighbooringMines());
                }
              }
            }
            i++;
            }
        }
    }
    /**
     * Getter method for the heigth of the game
     * 
     * @return the value of the attribute heigthOfGame
     */   
    public int getHeigth(){
        return heigthOfGame;
    }

    /**
     * Getter method for the width of the game
     * 
     * @return the value of the attribute widthOfGame
     */   
    public int getWidth(){
        return widthOfGame;
    }



    /**
     * returns true if the dot at location (i,j) is mined, false otherwise
    * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */   
    public boolean isMined(int i, int j){
        return model[j][i].isMined();
    }

    /**
     * returns true if the dot  at location (i,j) has 
     * been clicked, false otherwise
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */   
    public boolean hasBeenClicked(int i, int j){
        return model[j][i].hasBeenClicked();
    }

  /**
     * returns true if the dot  at location (i,j) has zero mined 
     * neighboor, false otherwise
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */   
    public boolean isBlank(int i, int j){
        return model[j][i].getNeighbooringMines() == 0;
    }
    /**
     * returns true if the dot is covered, false otherwise
    * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */   
    public boolean isCovered(int i, int j){
        return model[j][i].isCovered();
    }

    /**
     * returns the number of neighbooring mines os the dot  
     * at location (i,j)
     *
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the number of neighbooring mines at location (i,j)
     */   
    public int getNeighbooringMines(int i, int j){
        return model[j][i].getNeighbooringMines();
    }


    /**
     * Sets the status of the dot at location (i,j) to uncovered
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     */   
    public void uncover(int i, int j){
        numberUncovered ++;
        model[j][i].uncover();

    }

    /**
     * Sets the status of the dot at location (i,j) to clicked
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     */   
    public void click(int i, int j){
        model[j][i].click();
    }
     /**
     * Uncover all remaining covered dot
     */   
    public void uncoverAll(){
        for (int i = 0; i < heigthOfGame; i++){
            for (int j = 0; j < widthOfGame; j++){
                if (isCovered(j,i))
                    uncover(j,i);
            }
        }
    }

 

    /**
     * Getter method for the current number of steps
     * 
     * @return the current number of steps
     */   
    public int getNumberOfSteps(){
        return numberOfSteps;
    }

  

    /**
     * Getter method for the model's dotInfo reference
     * at location (i,j)
     *
      * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     *
     * @return model[i][j]
     */   
    public DotInfo get(int i, int j) {
        return model[j][i];
    }


   /**
     * The metod <b>step</b> updates the number of steps. It must be called 
     * once the model has been updated after the payer selected a new square.
     */
     public void step(){
        numberOfSteps++;
    }
 
   /**
     * The metod <b>isFinished</b> returns true iff the game is finished, that
     * is, all the nonmined dots are uncovered.
     *
     * @return true if the game is finished, false otherwise
     */
    public boolean isFinished(){
        // Changed Simpler Code, i think
        if(numberUncovered+numberOfMines==(heigthOfGame*widthOfGame))
            return true;
        return false;
    }


   /**
     * Builds a String representation of the model
     *
     * @return String representation of the model
     */
    public String toString(){
        String str = "";
        for (int i = 0; i < heigthOfGame; i ++){
            for (int j = 0; j < widthOfGame; j++){
                if (!(model[i][j].isMined())){
                    str = str + "[" + String.valueOf(model[i][j].getNeighbooringMines())+"]";
                }
                else{
                    str = str + "[X]";
                }
            }
            str = str + "\n";
        }
        return str;
    }
}
