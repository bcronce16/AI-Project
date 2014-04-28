import java.util.ArrayList;
import java.util.Scanner;

/**
 * Program: HumanPlayer.java
 * 
 * This class is used to implement the game for a human player. This class
 * interacts with a human player by prompting them for his or her move. At 
 * each turn the currentState of the Sudoku board is printed and the
 * user is asked by the program to enter values for his or her next move. 
 * No constrains are placed on a human user, except he or she can not erase or 
 * overwrite a fixed value. Once the user reaches a goal they will be notified 
 * and asked if they want to continue playing.
 * 
 * @author Bradley Cronce
 *         Jake Pollard
 *         
 * @version 3/24/2014
 */
public class HumanPlayer
{
    // fields for the HumanPlayer class...
    private SudokuBoard currentState;
    private Scanner scan;
    private int size;

    
    /**
     * Constructor for the HumanPlayer class. The constructor simply
     * sets the size of the board being used, sets the current 
     * state of the board, and sets up a scanner to get user input.
     * 
     * @param currentState
     *                     the state were the solve user gets to start at
     */
    public HumanPlayer(SudokuBoard currentState) 
    {
        this.currentState = currentState;
        scan = new Scanner(System.in);
        size = currentState.getBoardSize();
    }


    /**
     * Method to see if a value is legal. This method gets 
     * passed an integer as a parameter and checks the value 
     * to see if the value is valid. Valid values consist of 0
     * to the size of the board. This method is called in the play()
     * method to see if the users inputed value is legal.
     * 
     * @param value
     *              the value that needs to be checked for validity. 
     * 
     * @return if the value passed as a parameter is between 0 and 
     *         the board size then true is returned, false otherwise.
     */
    public boolean checkValue(int value)
    {
        if (value < 0 || value > size)
        {
            return false;
        }
        return true;
    }


    /**
     * Method to see if two coordinates are legal. This method gets 
     * passed two integers as a parameter and checks the indices 
     * to see if they are valid. Valid indices consist of 0
     * to the board (size - 1). This method is called in the play()
     * method to see if the users inputed indices are legal. 
     * 
     * @param x
     *          the x value that needs to be checked for validity
     * @param y
     *          the y value that needs to be checked for validity
     *          
     * @return if both indices passed to the method are between 0 and 
     *         the board (size - 1) then true is returned, false otherwise.
     */
    public boolean checkXandY(int x, int y)
    {
        if ((x < 0 || x > size - 1) || (y < 0 || y > size - 1))
        {
            return false;
        }
        return true;
    }


    /**
     * Method to check a state to see if it is a goal state. It 
     * does this by going through each row, column, and square, 
     * and checking to see if each are filled with the correct values.
     * This method does not currently work correctly for the HumanPlayer
     * class. NEEDS WORK!!
     * 
     * @param state
     *              the current state of the board
     *              
     * @return true or false depending on if the state is a goal.
     */
    public boolean isGoalState(SudokuBoard state) 
    {
        int[][] board = state.getBoard();

        // ArrayList to hold the the values that have 
        // to be in the board for it to be a goal state
        ArrayList<Integer> check = new ArrayList<Integer>(size);

        // loop to initialize the list above
        for (int i = 1; i <= size; i++)
        {
            check.add(i);
        }


        // goes through each row and checks to see 
        // if it is filled with the required values
        for (int row = 0; row < size; row++)
        {
            for (int col = 0; col < size; col++)
            {
                if (!check.contains(board[row][col]))
                {
                    return false;
                }
            }
        }

        // goes through each column and checks to see 
        // if it is filled with the required values
        for (int col = 0; col < size; col++)
        {           
            for (int row = 0; row < size; row++)
            {
                if (!check.contains(board[row][col]))
                {
                    return false;
                }
            }
        } 

        // goes through each square and checks to see if 
        // it is filled with the required values
        for (int row = 0; row < size; row += Math.sqrt(size))
        {
            for (int col = 0; col < size; col += Math.sqrt(size))
            {                
                // gets each individual square
                int squareNumber = state.getSquareNumber(row, col);
                int[][] square = state.getSquare(squareNumber);

                // loops to traverse the square and 
                // check for the appropriate values
                for (int i = 0; i < square.length; i++)
                {
                    for (int j = 0; j < square[i].length; j++)
                    {
                        if (!check.contains(square[i][j]))
                        {
                            return false;
                        }
                    }
                }               
            }
        } 
        // if it makes it through all of the test, 
        // then state, is in fact a goalState
        return true;      
    }


    /** 
     * Method to actually allow the human player to play the game. 
     * This method is called in the Sudoku class by the main method. 
     * This method allows the user to play the game and make moves until
     * they reach a solution. At each round of the game the currentState
     * of the board is printed and the user is asked to enter a
     * X, Y, and a value which is used as the location for a move to 
     * be made. If the user finds the solution then "You Won!" is printed
     * to the screen and the user is asked if they would like to play again.
     *  
     */
    public void play() 
    {
        
        // variable used to see if the game is over
        Boolean finished = false;

        // repeat while not finished
        while (!finished)
        {
            // gets the user input for the location where they
            // would like to make their move
            System.out.println("**Please enter the coordinates "
                    + "and value that you would like placed**\n"
                    + "Enter the values in the following format: "
                    + "X-Coordinate Y-Coordinate Value");

            // take out the -1 if you want to use 0 to (size - 1) as the indices
            int corX = scan.nextInt() - 1;
            int corY = scan.nextInt() - 1;
            int value = scan.nextInt();

            System.out.println();

            // checks to see if the x and y coordinates are valid
            if (!checkXandY(corX, corY)) 
            {
                System.out.println("You entered an incorrect "
                        + "coordinate value! Please try again!\n"
                        + "Enter a value between 1 and " + size + "\n");
                continue;
            }

            // checks to see if the value the user entered is valid
            if (!checkValue(value)) 
            {                
                System.out.println("You entered an incorrect "
                        + "value! Please try again!\n");
                continue;                
            }

            // TODO: check for fixed value!!

            // if all constraints above are passed then the users requested 
            // move is made.
            currentState.setValue(corX, corY, value);
            currentState.printBoard();

            System.out.println();

            // checks to see if the current state is a goal state
            if (isGoalState(currentState))
            {
                System.out.println("You Won!");
                finished = true;
            }
        }
    }
}

//end of HumanPlayer class
