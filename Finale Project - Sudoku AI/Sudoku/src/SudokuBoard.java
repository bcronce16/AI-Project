import java.util.ArrayList;

/**
 * Program: SudokuBoard.java
 * 
 * Description: 
 * 
 * 
 * @author Bradley Cronce
 * @version 3/24/2014
 */
public class SudokuBoard implements State
{

    private int[][] board;
    private int size;
    double squareSize;


    /**
     * Description:
     * 
     * @param boardSize
     */
    public SudokuBoard(int boardSize) 
    {
        board = new int[boardSize][boardSize];
        size = boardSize;
        squareSize = Math.sqrt((double) size);
    }


    /**
     * Description:
     * 
     * @param boardSize
     * @param otherBoard
     */
    public SudokuBoard(int boardSize, int otherBoard[][]) 
    {
       size = boardSize; 
       squareSize= Math.sqrt((double) size);
       board = new int[boardSize][boardSize];
       
       for (int i = 0; i < boardSize; i++) 
       {
           for (int j = 0; j < boardSize; j++)
           {
               board[i][j] = otherBoard[i][i];
           }
       }
       
    }


    /**
     * Description:
     * 
     * @param x
     * @param y
     * 
     * @return
     */
    public int getValue(int x, int y) 
    {
        return board[x][y];
    }


    /**
     * Description:
     *  
     * @param x
     * @param y
     * @param value
     * 
     */
    public void setValue(int x, int y, int value)
    {
        board[x][y] = value;        
    }


    /**
     * Description:
     * 
     */
    public boolean equals(Object obj) 
    {
        if (obj instanceof SudokuBoard)
        {
            SudokuBoard data = (SudokuBoard) obj;

            for (int row = 0; row < size; row++)
            {
                for (int col = 0; col < size; col++)
                {
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
     * Description: 
     * 
     */
    public int hashCode() 
    {
        int hashVal = 0;

        for (int i = 0; i < board.length; i++)
        {
            for (int j = 0; j < board[i].length; j++)
            {
                hashVal = 37 * hashVal + (board[i][j] + board[j][i]);
            }
        }
        return hashVal;
    }


    /**
     * Description:
     *  
     * @param anotherState
     */
    public void copy(State anotherState)
    {
        SudokuBoard data = (SudokuBoard) anotherState;

        for(int i = 0; i < size; i++) 
        {
            for(int j = 0; j < size; j++) 
            {
                board[i][j] = data.board[i][j];
            }
        }
    }


    /**
     * Description:
     * 
     */
    public SudokuBoard clone()
    {

        SudokuBoard newState = new SudokuBoard(size);
        newState.copy(this);
        return newState;

    }


    /**
     * Description:
     * 
     * set up for multiple board sizes
     */
    public void printBoard() 
    {
        // gets iteration size based on the board size and square size
        int newSize = size + (int)squareSize;
        int rowCount = (int) squareSize;
        int colCount = (int) squareSize;
        int row = 0;
        int col = 0;

        for (int i = 0; i < newSize; i++) {
            
            if (row == size) {
                
                break;    
            }

            if(rowCount != 0 && rowCount == i) {
                
                for (int x = 0; x < newSize - 1; x++) {
                    
                    if (colCount == x && colCount != size) {
                        
                        System.out.print("+");
                        colCount += squareSize + 1;
                        
                    } else if (x == 6 || x == 11 || x == 15 || x == 20) { // check for specific values so the board prints correctly
                        
                        System.out.print("---");
                        
                    } else {
                        
                        System.out.print("--");
                    }
                }
                rowCount += squareSize;
                colCount = (int) squareSize;
                System.out.println();
            }

            col = 0;
            int colIndex = (int) squareSize;
            for (int j = 0; j < newSize; j++) {
                
                if (col == size) {

                    continue;
                }
             
                if(colIndex == j && j != newSize) {
                    
                    System.out.print("| ");
                    colIndex += squareSize + 1;
                    continue;
                }
                
                if (board[row][col] != 0) {
                    
                    System.out.print(board[row][col] + " ");
                    
                } else {
                    
                    // if the value at board[row][col] equals 0, a '*' is printed
                    System.out.print("* ");
                    
                }
                col++;
            }
            row++;
            System.out.println();
        }
    }
    
    
    /**
     * Description: Method to check to see if a move is legal. The method will check
     *              the specified row, column, and square. The method also checks the 
     *              exact location to see if a value already exist.
     * 
     * @param row
     * @param col
     * @param value
     * 
     * @return
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
     * Description: Method to go to a specific row and column location,
     *              and get all of the possible values that can be put into
     *              the specified location.
     * 
     * @param row
     * @param col
     * 
     * @return
     */
    public ArrayList<Integer> getPossibleValues(int row, int col)
    {
        // array to hold the possible values
        ArrayList<Integer> values = new ArrayList<Integer>();
        
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
     * Description: Method to take the column location of a potential value, and the value
     *              itself, and check specified column for the value.
     * 
     * @param col
     * @param value
     * 
     * @return
     */
    public boolean checkColumn(int col, int value) 
    {
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
     * Description: Method to take the row location of a potential value, and the value
     *              itself, and check specified row for the value.
     * 
     * @param row
     * @param value
     * 
     * @return
     */
    public boolean checkRow(int row, int value)
    {
        for (int i = 0; i < size; i++)
        { 
            if (board[row][i] == value)
            {
                return false;
            }
        }
        return true;        
    }
    
    
    /**
     * Description: Method to take the location of a potential value, and the value
     *              itself, and check the square containing the location for the value.
     * 
     * @param row
     * @param col
     * @param value
     * 
     * @return
     */
    public boolean checkSquare(int row, int col, int value) 
    {
        int squareNumber = getSquareNumber(row, col);
        int[][] square = getSquare(squareNumber);
        
        for (int i = 0; i < square.length; i++)
        {
            for (int j = 0; j < square[i].length; j++)
            {
                if(square[i][j] == value)
                {
                    return false;
                }
            }
        }
        return true;        
    }
    
    
    /**
     * Description: Method to get the indices of each square on the board.
     * 
     * @return an ArrayList containing all of the indices of each square 
     *         is returned. Each index of the array corresponds to a 
     *         specific square on the board.
     */
    public ArrayList<String> getSquareIndices()
    {
        ArrayList<String> indices = new ArrayList<String>();
        int innerX = 0;
        int innerY = 0;
        int outterX = (int) squareSize;
        int outterY = (int) squareSize;
        
        for (int i = 0; i < squareSize; i++)
        {
            outterY = (int) squareSize;
            innerY = 0;
            for (int j = 0; j < squareSize; j++)
            {
                indices.add(innerX + "" + innerY + " " + outterX + "" + outterY);
                outterY += squareSize;
                innerY += squareSize;
            } 
            outterX += squareSize;
            innerX += squareSize;
        }
        return indices;
    }
    
    
    /**
     * Description: Method which takes a 'x' and 'y' and returns the number of the square
     *              that matched the location of board[x][y].
     * 
     * @param x
     * @param y
     * 
     * @return
     */
    public int getSquareNumber(int x, int y)
    {
        ArrayList<String> indices = getSquareIndices();
        int square = 0;
        
        for (int i = 0; i < indices.size(); i++)
        {
            String xandy = indices.get(i);
            int innerX = xandy.charAt(0) - 48;
            int innerY = xandy.charAt(1) - 48;
            int outterX = xandy.charAt(3) - 48; 
            int outterY = xandy.charAt(4) - 48;
            
            
            if (x >= innerX && y >= innerY && x < outterX && y < outterY)
            {
                square = i;
            }
        }
        return square;
    }
    
    
    /**
     * Description: Method that takes a square number and returns a 2D array
     *              of that square.
     * 
     * @param squareNumber  the number of the square that is needed
     * 
     * @return              a 2D array of the specified square is returned
     */
    public int[][] getSquare(int squareNumber)
    {
        int[][] square = new int[(int) squareSize][(int) squareSize];
        ArrayList<String> indices = getSquareIndices();
        
        String squareNeeded = indices.get(squareNumber);
        
        int rowLocation = squareNeeded.charAt(0) - 48;
        int colLocation = squareNeeded.charAt(1) - 48;
        
        for (int row = 0; row < squareSize; row++)
        {
            for (int col = 0; col < squareSize; col++)
            {
                square[row][col] = board[rowLocation][colLocation];
                colLocation++;
            }
            rowLocation++;
            colLocation = squareNeeded.charAt(1) - 48;
        }
        return square;      
    }
    
    
    /**
     * Description: Method to see if there is a value in a specific location
     * 
     * @param row   the specific row location that needs to be checked
     * @param col   the specific column location that needs to be checked
     * 
     * @return      if there is a value in the specified location then true is
     *              returned, if there is no value in the location then false
     *              is returned
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
