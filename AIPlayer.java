import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

/**
 * Program: AIPlayer.java
 * 
 * This class is used to implement a representation for an AI player. This AI 
 * will use a depth first search to solve the Sudoku puzzle. At each turn the 
 * AI will be held under certain constraints when choosing which move to make. 
 * These constrains will allow the AI to solve the puzzle in an efficient 
 * amount of time. The time the AI takes to solve the puzzle along with the 
 * solution to the puzzle will be printed to the screen.
 *              
 * @author Bradley Cronce
 *         Jake Pollard
 *         
 * @version 3/24/2014
 */
public class AIPlayer 
{   
    // fields for the AIPlayer class...
    private SudokuBoard currentState;
    private int size;
    
    // field to get rid of the magic number problem
    private final int divideBy = 1000;

    
    /**
     * Constructor for the AIPlayer class. The constructor simply
     * sets the size of the board being used, and sets the current 
     * state of the board.
     * 
     * @param currentState
     *                     the state were the solve AI needs to start at
     */
    public AIPlayer(SudokuBoard currentState) 
    {
        this.currentState = currentState;
        size = currentState.getBoardSize();       
    }
    

    /**
     * Method to actually have the AI solve the game. This method is 
     * called in the Sudoku class by the main method. This method simply
     * calls the depthFirstSearch method and passes it the currentState 
     * of the board. If the depthFirstSearch method returns false then
     * no solution could be found and the user is prompted with the results. 
     * If a solution is found then the time it took to solve and the 
     * solution is printed to the screen.
     * 
     */
    public void play()
    {        
        boolean search = depthFirstSearch(currentState);
        
        // if false then no solution could be found
        if (!search) 
        {
            System.out.println("No Solution was found!");
        }
    }
    
       
    /**
     * Method to check a state to see if it is a goal state. It does
     * this by going through each row, column, and square, and checking
     * to see if each are filled with the correct values. Based on the 
     * constraints placed on the AI at each turn, the isGoalState method
     * will be able to determine if any state is a solution.
     * 
     * @param state
     *              the current state of the board
     *              
     * @return true or false is returned depending on if the state is a goal.
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
                
        // goes through each column and checks 
        // see if it is filled with the required values
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
              
        // goes through each square and checks to 
        // see if it is filled with the required values
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
        // if it makes it through all of the 
        // test, then state, is in fact a goalState
        return true;      
    }
    
    
    /**
     * Method to look through a SudokuBoard and return the indices with
     * the smallest amount of possible actions. This method is used in 
     * the depthFistSearch() method to determine where the AI should make
     * the next move. 
     *              
     * @param board
     *              the board used to find the location with the 
     *              smallest amount of actions.
     *              
     * @return A string containing the indices of the location with the 
     *         smallest amount of actions. charAt(0) is X, and charAt(1)
     *         is Y.
     */
    @SuppressWarnings("unchecked")
    public int[] getSmallestAction(SudokuBoard board)
    {
        ArrayList<Integer> actions;
        
        // array to hold the indices with the smallest value
        int[] indices = new int[2];
        
        // used to test against, to find the smallest number of actions
        ArrayList<Integer> temp = null;
        
        for (int row = 0; row < size; row++)
        {
            for (int col = 0; col < size; col++)
            {
                if (board.getActions(row, col) == null)
                {
                    continue;
                }
                
                // holds the actions for a square
                actions = board.getActions(row, col);
                
                if (temp == null)
                {
                    temp = new ArrayList<Integer>(actions);
                }
                
                if (temp.size() >= actions.size())
                {
                    temp = (ArrayList<Integer>) actions.clone();
                    
                    indices[0] = row;
                    indices[1] = col;
                }               
            }
        }
        return indices;
    }
    
    
    /**
     * Method that implements a Depth First Search to solve the Sudoku game
     * from the currentState. The method returns true or false depending on 
     * whether the search could find a solution. This method will stop at the 
     * first solution that is found and print that solution. By commenting
     * out the returns below and replacing them with print statements, every
     * solution of the board will be printed.
     * 
     * @param board a SudokuBoard that will be used by the DFS to solve
     *              the game from the currentState.
     *              
     * @return true or false is returned depending on if a solution is found
     */
    public boolean depthFirstSearch(SudokuBoard board)
    {
        
        ArrayList<Integer> actions;
       
        // use a clone of the original state
        SudokuBoard state = board.clone();

        // Create a new queue to hold the frontier
        Stack<SudokuBoard> frontier = new Stack<SudokuBoard>();

        // Create a new hashSet to hold the states already seen
        HashSet<SudokuBoard> seen = new HashSet<SudokuBoard>();

        // Push the start state on the empty queue
        frontier.add(state);
       
        // sets a timer to see how long the method takes
        double startTime = System.currentTimeMillis();
        
        // check to see if the initial state was the goal
        // state
        if (isGoalState(state))
        {
            state.printBoard();
            double endTime = System.currentTimeMillis();
            double duration = (endTime - startTime) / divideBy;
            
            // prints a goal was found and the 
            // time it took to find the solution
            System.out.println("Here Is The Solution! It took " 
                    + duration + " seconds to "
                    + "complete.");
            
            return true;
        }
        
        // restart the clock right before going through the loop
        startTime = System.currentTimeMillis();
        
        // loop do
        while (true)
        {
            // if the frontier is empty then return failure.
            if (frontier.isEmpty())
            {
                // failure
                return false;
            }

            // choose a leaf node and remove it from the frontier
            state = frontier.pop();
            // add node to seen set
            seen.add(state);

            // Uncomment the three lines below to print out 
            // all of the steps it takes to get to the solution. 
            
            // print the currently explored path
            // state.printBoard();
            // System.out.println();
            
            // location with the smallest amount of moves
            int[] indices = getSmallestAction(state);
            
            // possible actions at the location with the smallest
            // amount of moves.
            actions = state.getActions(indices[0], indices[1]);

            // expand the chosen node, adding the resulting nodes to the
            // frontier
            for (int i = 0; i < actions.size(); i++)
            {
                // generate the all of the children
                SudokuBoard child = makeMove(indices[0], 
                        indices[1], state, actions.get(i));

                if (!seen.contains(child) && !frontier.contains(child))
                {
                    // if the node contains a goal state then return the 
                    // corresponding solution, if not add the child to 
                    // the frontier
                    if (isGoalState(child))
                    {
                        child.printBoard();
                        double endTime = System.currentTimeMillis();
                        double duration = (endTime - startTime) / divideBy;
                        // prints a goal was found and the time 
                        // it took to find the solution
                        System.out.println("Here Is The Solution! It took " 
                                           + duration + " seconds to "
                                           + "complete.");
                        
                        return true;
                    }
                    else
                    {
                        frontier.add(child);
                    }
                }
            }
        }
    } 
    
    
    /**
     * Method to make a move on a board at a specified row/column location.
     * This method is called by the depthFirstSearch() method to actually 
     * make a move on the board. At a given state the depthFisrtSearch() method 
     * will call this method for each possible action and produce all states
     * for the move.
     * 
     * @param row 
     *            the row location where the move needs to be made
     * @param column 
     *               the column location where the move needs to be made
     * @param state 
     *              the current board where the move needs to be made
     * @param value
     *              the value that needs to be added to the board
     *              
     * @return a copy of the original board with the move made
     */
    private SudokuBoard makeMove(int row, int column, 
            SudokuBoard state, int value)
    {
        // copies the state so the original won't be altered
        SudokuBoard copy = state.clone();

        // makes the move
        copy.setValue(row, column, value);
        return copy;
    }   
}

//end of AIPlayer class
