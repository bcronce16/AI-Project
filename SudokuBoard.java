import java.util.ArrayList;
import java.util.Random;

/**
 * Program: SudokuBoard.java
 * 
 * This class is used to represent a single state of the Sudoku board. 
 * This class is the base of the entire game. It is used by the Sudoku 
 * class, the AIPlayer class, and the HumanPlayer class. The methods in 
 * the class consist of methods to print the state of the board, and 
 * methods to check to see if a request move can be made. 
 * 
 * @author Bradley Cronce
 *         Jake Pollard
 *         
 * @version 3/24/2014
 */
public class SudokuBoard implements State
{
    // fields for SudokuBoard class
    private int[][] board;
    private int size;
    private double squareSize;
    
    // variable to handle the magic number problem
    private final int magic37 = 37;


    /**
     * Constructor for the SudokuBoard class. The constructor simply
     * sets the size of the board being used, and the expected size 
     * of each individual square in the board. The constructor also
     * sets up a two dimensional array used to represent the SudokuBoard.
     * 
     * @param boardSize
     *                  size of the board that the user request
     */
    public SudokuBoard(int boardSize) 
    {
        board = new int[boardSize][boardSize];
        size = boardSize;
        squareSize = Math.sqrt((double) size);
    }


    /**
     * Constructor for the SudokuBoard class. The constructor is passed
     * a size and another board. The constructor then makes a copy of 
     * the board passed as a parameter and returns a new SudokuBoard.
     * 
     * @param boardSize
     *                  the size of the board the user request.
     * @param otherBoard
     *                   the board that we need to copy
     */
    public SudokuBoard(int boardSize, int otherBoard[][]) 
    {
        size = boardSize; 
        squareSize = Math.sqrt((double) size);
        board = new int[boardSize][boardSize];

        copyArray(otherBoard);
    }


    /**
     * Method to return the two dimensional array representation
     * of the board for a SudokuBoard.
     * 
     * @return the two dimensional array representation of the board
     *         is returned.
     */
    public int[][] getBoard()
    {
        return board;
    }


    /**
     * Method to return the size of the SudokuBoard.
     * 
     * @return the size of the SudokuBoard is returned.
     */
    public int getBoardSize() 
    {
        return size;
    }


    /**
     * Method to return the size of a individual square for a SudokuBoard.
     * The individual squareSize is calculated by taking the square root
     * of the size of the board.
     * 
     * @return the size of a individual square for a SudokuBoard is returned.
     */
    public double getSquareSize()
    {
        return squareSize;
    }


    /**
     * Method to return the value in a specified location in a SudokuBoard.
     * The method is passed an X and Y integer (x, y), which are used as 
     * indices to get, and return a value in the two dimensional board.
     * 
     * @param x
     *          x index of the value that needs to be returned
     * @param y
     *          y index of the value that needs to be returned
     * 
     * @return the value in a specified location in a SudokuBoard is returned.
     */
    public int getValue(int x, int y) 
    {
        return board[x][y];
    }


    /**
     * Method to set the value in a specified location in a SudokuBoard.
     * The method is passed an X and Y integer (x, y), which are used as 
     * indices of the location that needs to be set. The value that needs 
     * to be put in the board is also passed as a parameter.
     *  
     * @param x
     *              x index of the value that needs to be set
     * @param y
     *              y index of the value that needs to be set
     * @param value
     *              value that needs to be added to the board
     * 
     */
    public void setValue(int x, int y, int value)
    {
        board[x][y] = value;        
    }


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
    public boolean equals(Object obj) 
    {
        // checks to make sure the parameter is a SudokuBoard
        if (obj instanceof SudokuBoard)
        {
            SudokuBoard data = (SudokuBoard) obj;

            // traverse the rows
            for (int row = 0; row < size; row++)
            {   
                // traverse the columns
                for (int col = 0; col < size; col++)
                {
                    // check for inequality
                    if (board[row][col] != data.board[row][col])
                    {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;      
    }


    /**
     * Method used to pick random locations in a board and place 
     * random vales in the locations. This method is used in the 
     * Sudoku class to create random SudokuBoards for the user to 
     * play on. 
     * 
     */
    public void randomFill()
    {
        Random randomGenerator = new Random();

        for (int i = 0; i < size; i++)
        {   
            // random value
            int randomInt = randomGenerator.nextInt(size) + 1;
            // random row value
            int randomInt2 = randomGenerator.nextInt(size);
            // random column value
            int randomInt3 = randomGenerator.nextInt(size);

            // checks to make sure the value generated do not conflict with the
            // values that are already in the board.
            if (checkMove(randomInt2, randomInt3, randomInt))
            {
                setValue(randomInt2, randomInt3, randomInt);
            }
        }
    }


    /**
     * HashCode method used to produce a hash value. The value
     * is produced by adding the value in board[i][j] and board[j][i]
     * then multiplying that value by the current hash value.
     * 
     * @return the hash value for a board is returned. 
     */
    public int hashCode() 
    {
        int hashVal = 0;

        // traverses the rows
        for (int i = 0; i < board.length; i++)
        {
            // traverses the columns
            for (int j = 0; j < board[i].length; j++)
            {
                hashVal = magic37 * hashVal + (board[i][j] + board[j][i]);
            }
        }
        return hashVal;
    }


    /**
     * Method to make a copy of the original board. The method takes 
     * a board in as a parameter and makes a copy into it. 
     * The board that is passed a parameter must be the 
     * state type. 
     *  
     * @param anotherState
     *                     another SudokuBoard that needs to be copied to.
     */
    public void copy(State anotherState)
    {
        // cast anotherState to the state type
        SudokuBoard data = (SudokuBoard) anotherState;

        // traverses the rows
        for (int i = 0; i < size; i++) 
        {
            // traverses the columns
            for (int j = 0; j < size; j++) 
            {
                board[i][j] = data.board[i][j];
            }
        }
    }

    
    /**
     * Method to make a copy of the current board. The method is the same
     * as the method above, except this method makes a copy of the 
     * current board and places it into a two dimensional array. 
     * 
     * @param board
     *              a two dimensional array that needs to be copied to.
     */
    public void copyArray(int[][] board)
    {
        // traverses the rows
        for (int i = 0; i < size; i++) 
        {
            // traverses the columns
            for (int j = 0; j < size; j++) 
            {
                this.board[i][j] = board[i][j];
            }
        }
    }
    

    /**
     * Method to clone a SudokuBoard. The method simply creates
     * a new SudokuBoard and then passes it to the copy() method.
     * 
     * @return a new SudokuBoard object is created and returned
     *         based on the current state
     */
    public SudokuBoard clone()
    {
        SudokuBoard newState = new SudokuBoard(size);
        newState.copy(this);
        return newState;
    }


    /**
     * Method to print the board itself. The method is set
     * up to accommodate to multiple board sizes. After doing
     * some testing, we realized that board sizes 16 and 25
     * mess up the printing of the board. This method needs to 
     * be fixed for sizes 16 and 25.
     *  
     */
    public void printBoard() 
    {
        // gets iteration size based on the board size and square size
        int newSize = size + (int) squareSize;
        int rowCount = (int) squareSize;
        int colCount = (int) squareSize;
        int row = 0;
        int col = 0;

        for (int i = 0; i < newSize; i++)
        {
            if (row == size) 
            {

                break;    
            }

            if (rowCount != 0 && rowCount == i) 
            {
                for (int x = 0; x < newSize - 1; x++) 
                {
                    if (colCount == x && colCount != size) 
                    {
                        System.out.print("+");
                        colCount += squareSize + 1;
                    } 
                    // check for specific values so the board prints correctly
                    else if (x == 6 || x == 11 || x == 15 || x == 20) 
                    { 
                        System.out.print("---");
                    } 
                    else 
                    {
                        System.out.print("--");
                    }
                }
                rowCount += squareSize;
                colCount = (int) squareSize;
                System.out.println();
            }

            col = 0;
            int colIndex = (int) squareSize;
            for (int j = 0; j < newSize; j++) 
            {
                if (col == size) 
                {
                    continue;
                }

                if (colIndex == j && j != newSize) 
                {
                    System.out.print("| ");
                    colIndex += squareSize + 1;
                    continue;
                }

                if (board[row][col] != 0) 
                {
                    System.out.print(board[row][col] + " ");
                } 
                else 
                {
                    // if the value at board[row][col] 
                    // equals 0, a '*' is printed
                    System.out.print("* ");
                }
                col++;
            }
            row++;
            System.out.println();
        }
    }


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
    public boolean checkMove(int row, int col, int value)
    {
        if (checkRow(row, value) 
                && checkColumn(col, value) 
                && checkSquare(row, col, value)
                && !checkForValue(row, col))
        {
            return true;
        }
        return false;
    }


    /**
     * Method to go to a specific row and column location, and get all 
     * of the possible values that can be put into the specified location.
     * The method will first check to see if there is a value in the specified
     * row and column and then continue through the process. If there is
     * a value there already then null is returned.
     * 
     * @param row
     *            the row index of the location to get the possible values
     * @param col
     *            the column index of the location to get the possible values.
     * 
     * @return an ArrayList of the possible values at the specified location
     *         in returned
     */
    public ArrayList<Integer> getActions(int row, int col)
    {
        // array to hold the possible values
        ArrayList<Integer> values = new ArrayList<Integer>();

        if (checkForValue(row, col))
        {
            return null;
        }

        // check each possible value that is possible in the board
        for (int i = 1; i < size + 1; i++)
        {
            // if checkMoves() returns true then 'i' gets added to the list
            if (checkMove(row, col, i))
            {
                values.add(i);
            }
        }       
        return values;        
    }


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
    public boolean checkColumn(int col, int value) 
    {
        // traverse the rows
        for (int i = 0; i < size; i++)
        {
            // if the value is found return false
            if (board[i][col] == value)
            {
                return false;
            }
        }
        return true;
    }


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
    public boolean checkRow(int row, int value)
    {
        // traverse the rows
        for (int i = 0; i < size; i++)
        { 
            // traverse the columns
            // if the value is found return false
            if (board[row][i] == value)
            {
                return false;
            }
        }
        return true;        
    }


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
    public boolean checkSquare(int row, int col, int value) 
    {
        int squareNumber = getSquareNumber(row, col);
        int[][] square = getSquare(squareNumber);

        // traverse the rows
        for (int i = 0; i < square.length; i++)
        {
            // traverse the columns
            for (int j = 0; j < square[i].length; j++)
            {
                if (square[i][j] == value)
                {
                    return false;
                }
            }
        }
        return true;        
    }


    /**
     * Method to get the indices of each square on the board. This method
     * is used to get a specific square so a check for a duplicate value in 
     * a square can take place. The indices will change for each board size
     * so this method is needed to get the indices.
     * 
     * @return an ArrayList containing all of the indices of each square 
     *         is returned. Each index of the array corresponds to a 
     *         specific square on the board.
     */
    public int[][] getSquareIndices()
    {
        // array to hold the indices of each square
        int[][] squareIndices = new int[size][4];
        int square = 0;

        // variables to hold the inner and outer X and
        // Y indices
        int innerX = 0;
        int innerY = 0;
        int outterX = (int) squareSize;
        int outterY = (int) squareSize;

        // traverse the rows
        for (int i = 0; i < squareSize; i++)
        {
            outterY = (int) squareSize;
            innerY = 0;
            
            // traverse the columns
            for (int j = 0; j < squareSize; j++)
            {
                squareIndices[square][0] = innerX;
                squareIndices[square][1] = innerY;
                squareIndices[square][2] = outterX;
                squareIndices[square][3] = outterY;

                // Outer Y index of the current square
                outterY += squareSize;
                // Inner Y index of the current square
                innerY += squareSize;
                // the actual square
                square++;
            } 
            // Outer X index of the current square
            outterX += squareSize;
            // inner X index of the current square
            innerX += squareSize;
        }
        return squareIndices;
    }


    /**
     * Method which takes a 'x' and 'y' and returns the number of the square
     * that contains the location of board[x][y]. An integer 0 to the size
     * is returned. This method is used to check a square for a duplicate 
     * value. 
     * 
     * @param x
     *          x index of the square that is needed
     * @param y
     *          y index of the square that is needed
     * 
     * @return the number of the square that contains the x and y indices
     *         is returned
     */
    public int getSquareNumber(int x, int y)
    {
        // array to hold the square indices
        int[][] indices = getSquareIndices();
        int square = 0;

        for (int i = 0; i < indices.length; i++)
        {
            int innerX = indices[i][0];
            int innerY = indices[i][1];
            int outterX = indices[i][2]; 
            int outterY = indices[i][3];

            if (x >= innerX && y >= innerY && x < outterX && y < outterY)
            {
                square = i;
            }
        }
        return square;
    }


    /**
     * Method that takes a square number and returns a 2D array
     * of that square. This method is used to check a move. The check
     * square method calls this one to get a square and then check the 
     * square for a duplicate value.
     * 
     * @param squareNumber  
     *                     the number of the square that is needed. this 
     *                     is found by calling the getSquareNumber() method.
     * 
     * @return a 2D array of the specified square is returned
     */
    public int[][] getSquare(int squareNumber)
    {
        int[][] square = new int[(int) squareSize][(int) squareSize];
        int[][] indices = getSquareIndices();

        // indices for the specified square that is needed
        int rowLocation = indices[squareNumber][0];
        int colLocation = indices[squareNumber][1];

        // traverses the rows
        for (int row = 0; row < squareSize; row++)
        {
            // traverses the columns
            for (int col = 0; col < squareSize; col++)
            {
                square[row][col] = board[rowLocation][colLocation];
                colLocation++;
            }
            rowLocation++;
            colLocation = indices[squareNumber][1];
        }
        return square;      
    }


    /**
     * Method to see if there is a value in a specific location.
     * The method checks the current state of the board at (row, col)
     * and returns true or false depending on if a value is there.
     * 
     * @param row   
     *            the specific row location that needs to be checked
     * @param col   
     *            the specific column location that needs to be checked
     * 
     * @return if there is a value in the specified location then true is
     *         returned, if there is no value in the location then false
     *         is returned
     */
    public boolean checkForValue(int row, int col)
    { 
        // if there is a value in the location then true is returned
        if (board[row][col] != 0)
        {
            return true;
        }
        return false;      
    }
}

//end of SudokuBoard class
