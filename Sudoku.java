import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Program: Sudoku.java
 * 
 * Class used to create a new instance of a Sudoku game. This class
 * sets up the game by getting user input and presenting the user
 * with the board. The user then has the option to solve the board
 * themselves or have the AI solve it for them. The main method only 
 * creates a new instance of the class that is needed. If the user 
 * request to solve the game themselves then a new HumanPlayer instance
 * is created and the methods from that class are called. If the user
 * request to have an AI solve the game then a new AIPlayer instance is 
 * created and methods from that class are called. This class is used to
 * set up the game and use the other classes to do all the work. 
 * 
 * @author Bradley Cronce
 *         Jake Pollard
 *         
 * @version 3/24/2014
 */
public class Sudoku
{
    // fields for Sudoku class...
    private Scanner scan;
    private int boardSize;
    private SudokuBoard currentState;
    
    // used to see if a value is fixed in the
    // HumanPlayer class
    private SudokuBoard startState;  
    
    // used to see if a square value is fixed, 
    // only needed in the HumanPLayer class
    private boolean[][] fixedBoard;
    
    // possible board sizes, used to prevent magic numbers
    private final int minBoard = 4;
    private final int nineBoard = 9;
    private final int sixBoard = 16;
    private final int maxBoard = 25;
    
    
    /**
     * Constructor for the Sudoku game. The game is set up to ask the 
     * user if he or she wants to actually play the game, or have an AI
     * solve it for them. The board sizes allowed are 4, 9, 16, and 25. 
     * The user is also asked which board size he or she would like to
     * play with.
     * 
     */
    public Sudoku()
    {
        scan = new Scanner(System.in);
        startState = setStartState();
        currentState = startState;       
    }
    
    
    /**
     * Method get initialize a new board. The user is first asked 
     * if he or she would like to load a pre made moard. If they choose 
     * to then they will have to provide a fileName. The method will then
     * ask the user what size board he or she would like to play on, or the board 
     * size they would like to see solved.
     * 
     * @return
     *         the start state of the Sudoku board is returned
     */
    public SudokuBoard setStartState() 
    {
        System.out.println("Would you like to solve a "
                + "puzzle in our library?  (Yes || No)");
        
        while (scan.hasNext())
        {
            // case where user chooses the option to load a pre made 
            // board from a text file.
            String input = scan.next();
            if (input.startsWith("y") || input.startsWith("Y"))
            {
                System.out.println("Please enter the name of the "
                        + "file that the board is in.");
                input = scan.next();
                int[][] board = readBoard(input);
                
                return new SudokuBoard(boardSize, board);
                    
            }
            else if (input.startsWith("n") || input.startsWith("N"))
            {
                // case where the user chooses to have a random board made
                // for them
                
                // get the board size from the user
                System.out.print("What size Sudoku board would you like? "
                        + "Please enter a number (4, 9, 16, or 25): ");
                boardSize = scan.nextInt();
                
                // if an incorrect choice is entered then the user is prompted
                while (boardSize != minBoard && boardSize != nineBoard 
                        && boardSize != sixBoard && boardSize != maxBoard)
                {
                    System.out.print("You entered an incorrect choice! "
                            + "Please enter a number (4, 9, 16, or 25): ");
                    boardSize = scan.nextInt();
                }
                System.out.println();
                
                // if the user does not want to load a board then a random board
                // is presented to the user
                SudokuBoard random = new SudokuBoard(boardSize);
                random.randomFill();
                
                return random;
            }
            else
            {
                System.out.println("Please answer with "
                        + "an accepted answer string");
            }
        }
        return null;
    }
    

    /**
     * Method to read in hard coded boards. Once the game is started
     * the user is asked if they would like to load a board from our
     * library or use a randomly generated board. If the user chooses
     * to load a board then they will have to provide a file name.
     * 
     * @param fileName
     *                 the name of the file that contains the hard coded board.
     *                 
     * @return a new two dimensional array is returned that contains the board
     *         which was read in from a file.
     */
    public int[][] readBoard(String fileName)
    {    
        Scanner sc = null;
        // values to represent indices in the board
        int i = 0;
        int j = 0;
         
        // try / catch block used to attempt to open the file.
        try
        {
            sc = new Scanner(new File(fileName));
        }
        catch (FileNotFoundException e)
        {
            System.out.println("The file could not be found!");
        }        
        
        // gets the size of the board
        boardSize = sc.nextInt();
        
        // 2d array to hold the new board
        int[][] board = new int[boardSize][boardSize];
        
        // repeat while there are more lines to read from the file.
        while (sc.hasNextLine())
        {
            String line = sc.nextLine();
            
            // checks for  empty lines in the file
            if (line.isEmpty())
            {
                continue;
            }
            j = 0;
            
            // split on spaces
            String[] tokens = line.split("\\s");
            
            // gets the values from the text file and places them into the 
            // 2d array that will be returned.
            if (i < maxBoard)
            {
                for (String token : tokens) 
                {
                    if (token.isEmpty())
                    {
                        continue;
                    }
                    
                    board[i][j] = Integer.valueOf(token);
                    j++;
                }
            }
            i++;
        }
        sc.close();
        return board;
    }
    
    
    /**
     * Method to ask the user if he or she wants to play the game
     * or have the AI solve the game for them. This method is called 
     * at the beginning of every game.
     *              
     * @return the choice the user makes is returned
     */
    public int playOrSolve() 
    {
        System.out.println();
        System.out.print("Would you like to play the game or have the "
                + "AI solve it for you? Enter a number: \n(1) to play "
                + "\n(2) to solve ");
        int playOrSolve = scan.nextInt();
        
        // if an incorrect choice is entered then the user is prompted
        while (playOrSolve < 1 || playOrSolve > 2)
        {
            System.out.print("You entered an incorrect choice! "
                    + "Enter a number: \n(1) to play \n(2) to solve: ");
            playOrSolve = scan.nextInt();
        }
        System.out.println();
        
        return playOrSolve;
    }
   
    
    /**
     * Main method to start a new Sudoku game. When the game starts the user
     * is asked if they would like to load a premade board from our library, 
     * or have a random board made for them. The main method only 
     * creates a new instance of the class that is needed. If the user 
     * request to solve the game themselves then a new HumanPlayer instance
     * is created and the methods from that class are called. If the user
     * request to have an AI solve the game then a new AIPlayer instance is 
     * created and methods from that class are called.  
     * 
     * @param args
     *             command line arguments
     */
    public static void main(String[] args) 
    { 
        Boolean keepPlaying = true;
        
        // repeat while the user wishes to play
        while (keepPlaying)
        {
            Sudoku game = new Sudoku();
    
            game.currentState.printBoard();
            
            // keep this here. It ask the user for an AI or Human Player
            int playOrSolve = game.playOrSolve();
            
            // case where the user chooses to play themselves
            if (playOrSolve == 1)
            {
                HumanPlayer humanPlayer = new HumanPlayer(game.currentState);
                humanPlayer.play();
            }
            // case where the user chooses to have the game solved for them
            else
            {
                AIPlayer aiPlayer = new AIPlayer(game.currentState);
                aiPlayer.play();
            }
            
            // after the user has completed the board or the AI has solved
            // it, the user is given the option to play again or quit.
            System.out.println("Would you like to play again?");
            
            while (game.scan.hasNext())
            {
                String input = game.scan.next();
                if (input.startsWith("y") || input.startsWith("Y"))
                {
                    break;
                }
                if (input.startsWith("n") || input.startsWith("N"))
                {
                    keepPlaying = false;
                    System.exit(0);
                }
                else
                {
                    System.out.println("Please answer with either yes or no.");
                }
            }
        }
    }
}

// end of Sudoku class
