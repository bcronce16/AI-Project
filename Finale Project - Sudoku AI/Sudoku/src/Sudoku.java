import java.util.Scanner;

/**
 * 
 * 
 * @author Bradley Cronce
 * @version 3/24/2014
 */
public class Sudoku
{
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) 
    {

        System.out.print("What size Sudoku board would you like? Please enter a number (4, 9, 16, or 25): ");
        int boardSize = scan.nextInt();
        System.out.println();

        SudokuBoard game = new SudokuBoard(boardSize);

        for (int i = 0; i < boardSize; i++) {

            for (int j = 0; j < boardSize; j++) {

                game.setValue(i, j, 0);
            }
        }
        
        game.setValue(0,  0,  1);
        game.setValue(1,  1,  1);
        game.setValue(0,  2,  2);
        game.setValue(3,  1,  3);
        game.setValue(2,  0,  3);
        game.setValue(2,  2,  4);
        game.setValue(2,  3,  3);
        game.setValue(3,  2,  1);
        game.setValue(3,  3,  2);
        game.setValue(3,  3,  2);
        game.printBoard();

    }

}
