/**
 * Program: State.java
 * 
 * Interface that is implemented by the SudokuBoard class. This interface
 * is used to define the methods that an object must implement to 
 * be considered a SudokuBoard. 
 * 
 * @author Bradley Cronce
 *         Jake Pollard 
 *                
 * @version 3/24/2014
 */
public interface State
{
    /**
     * Method t take an object in as a parameter and compare it
     * to another object to check for equality. In this case the
     * objects that will be check are going to be instances of 
     * SudokuBoards.
     * 
     * @param obj
     *            object to be tested for equality
     * 
     * @return true or false depending on if the objects are equal. 
     */
    public boolean equals(Object obj);
    
    /**
     * HashCode method used to produce a hash value. The value
     * is produced by adding the value in board[i][j] and board[j][i]
     * then multiplying that value by the current hash value.
     * 
     * @return the hash value for a board is returned. 
     */
    public int hashCode();
    
    /**
     * Method to make a copy of the original board. The method takes 
     * a board in as a parameter and makes a copy into it. 
     * The board that is passed a parameter must be the 
     * state type. 
     *  
     * @param anotherState
     *                     another SudokuBoard that needs to be copied to.
     */
    public void copy(State anotherState);
    
    /**
     * Method to clone a SudokuBoard. The method simply creates
     * a new SudokuBoard and then passes it to the copy() method.
     * 
     * @return a new SudokuBoard object is created and returned
     *         based on the current state
     */
    public SudokuBoard clone();
    
    /**
     * Method to check to see if a move is legal. The method will check 
     * the specified row, column, and square. The method also checks the 
     * exact location to see if a value already exist.
     * 
     * @param row
     *            the row index that needs to be checked
     * @param col
     *            the column index that needs to be checked
     * @param value
     *              the value that needs to be checked for
     * 
     * @return true or false is returned depending on whether the 
     *         value is in the specified row, column, or square already.
     */
    public boolean checkMove(int row, int col, int value);
    
    
    /**
     * Method to take the row location of a potential value, and the value
     * itself, and check specified row for the value. This method is used 
     * to check a move for the AI to see of the move can be made.
     * 
     * @param row
     *            the row that needs to be checked for a duplicate value
     * @param value
     *              the value that needs to be checked for in a row
     * 
     * @return true or false is returned depending on if the value already 
     *         occurs in the row
     */
    public boolean checkRow(int row, int value);
    
    /**
     * Method to take the column location of a potential value, and the value
     * itself, and check specified column for the value. This method is used 
     * to check a move for the AI to see of the move can be made.
     * 
     * @param col
     *            the column that needs to be checked for a duplicate value
     * @param value
     *              the value that needs to be checked for in a column
     * 
     * @return true or false is returned depending on if the value already 
     *         occurs in the column
     *         
     */
    boolean checkColumn(int col, int value);
    
    /**
     * Method to take the location of a potential value, and the value
     * itself, and check the square containing the location for the value.
     * This method is used to check a move for the AI to see of the move 
     * can be made.
     * 
     * @param row
     *            row index of a square that needs to be checked 
     * @param col
     *            column index of a square that needs to be checked
     * @param value
     *              value that needs to be checked in the square to see if 
     *              the value is already in the square.
     * 
     * @return true or false is returned depending on whether or not the value
     *         is already in the square that contains the x and y values.
     */
    public boolean checkSquare(int row, int col, int value);
}

//end of State interface
