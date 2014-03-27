
public interface State
{
    public boolean equals(Object obj);
    public int hashCode();
    public void copy(State anotherState);
    public SudokuBoard clone();
    public boolean checkMove(int row, int col, int value);
    public boolean checkRow(int row, int value);
    public boolean checkColumn(int col, int value);
    public boolean checkSquare(int row, int col, int value);
}
