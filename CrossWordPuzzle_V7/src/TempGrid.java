

/**
 * This class stores information relating to TempGrid objects. Basically the
 * TempGrid is not what the user sees, but what the generated crossword is 
 * stored in until it can be displayed in the DisplayCrossWord class. This 
 * class contains garden variety setter and getter methods as well as methods
 * that deal specifically with the TempGrid. For instance resizing and actually
 * "placing" words in the TempGrid object (basically a 2-d array of char) are
 * taken care of here.
 * 
 * @author Hope Dargan
 * @version 1.0 2/14/14
 */
public class TempGrid
{
    // instance variables - default values
    private int myRows = 0;
    private int myColumns = 0;
    private char[][] myGrid;
    private final char DEFAULT_CHAR = '_';
    /**
     * Constructor for objects of class TempGrid
     * @param row - an int that determines how many rows myGrid has
     * @param column - an int that determines the how many columns myGrid will
     * have
     */
    public TempGrid(int row, int column)
    {
        myRows = row;
        myColumns = column;
        myGrid = new char[myRows][myColumns];
    }
    
    /**
     * This method returns a char value stored in the given myGrid location. If
     * the input parameters are not a valid location the value for a "blank" 
     * space on myGrid is returned.
     * @param x - an int that determines the desired column value in myGrid
     * @param y - an int that determines the desired row of the desired char in
     * myGrid
     * @return this method returns the char at the given location in myGrid
     */
    public char get(int x, int y)
    {
        return x>=0 && x<myColumns && y>=0 && y<myColumns ? myGrid[y][x] : DEFAULT_CHAR;
    }
    
    /**
     * A simple getter method that returns myGrid.
     * @return myGrid - this returns a 2d of chars that has the "solution" stored
     * in it
     */
    public char[][] getGrid()
    {
      return myGrid;
    }
    
    /**
     * A simple getter method that returns what TempGrid defines as a "blank" space.
     * @return DEFAULT_CHAR - this returns a char that TempGrid defines as a 
     * "blank" space
     */
    public char getDefaultChar()
    {
        return DEFAULT_CHAR;
    }
    
    /**
     * A simple setter method that changes the char value of a myGrid[y][x] location
     * from the set value to letter if the coordinates are in myGrid.
     * If the coordinates are not in myGrid the value does not change
     * @param y an int that gives the desired row location in myGrid
     * @param x an int that gives the desired column location in myGrid
     * @param letter a char that a value in myGrid is changed to
     */
    public void setLetter(int y, int x, char letter)
    {
        if (y>=0 && x>=0 && y<myRows && x<myColumns)
        {
            myGrid[y][x]= letter;
        }
    }
    
    /**
     * This method is a simple getter method that returns the number of rows
     * in myGrid.
     * @return myRows - an int that stores how many rows myGrid has. 
     */
    public int getRowLength()
    {
        return myRows;
    }
    
    /**
     * This method is a simple getter method that returns the number of columns
     * in myGrid.
     * @return myColumns - an int that stores how many columns myGrid has.
     */
    public int getColumnLength()
    {
        return myColumns;
    }
    
    /**
     * This method initializes myGrid so every "coordinate" or position in
     * myGrid is DEFAULT_CHAR, or in other words, a blank space.
     */
    public void makeBlankGrid()
    {
        for(int i=0; i<myRows; i++)
        {
            for (int k=0; k<myColumns; k++)
            {
                myGrid[i][k] = DEFAULT_CHAR;
            }
        }
    }
    
    /**
     * This method is used for testing purposes to check the contents of 
     * myGrid. It uses System.out.print() to display myGrid.
     */
    public void printTempGrid()
    {
        for (int i=0; i<myRows; i++)
        {
            for (int k=0; k<myColumns; k++)
            {
              System.out.print(myGrid[i][k]+"");               
            }
            System.out.println();
        }
    }
    
    /**
     * This method takes a Word object and places the word of the object into
     * myGrid at the given location vertically (given that the Word object has valid
     * location coordinates inside myGrid).
     * @param w - a Word object. The first letter of the Word object's word
     * is placed at the Word object's coordinates x and y. Then the next letter
     * is placed at the row below it in myGrid and so on and so forth.
     */
    public void placeWordVertical(Word w)
    {
        int y = w.getY();
        int x = w.getX();
        for (int i=0; i<(w.getWord()).length() && y+i>=0 && y+i<myRows 
                                               && x<myColumns && x>=0; i++)
        {
            myGrid [y+i][x] = (w.getWord()).charAt(i);
        }
    }
       /**
     * This method takes a Word object and places the word of the object into
     * myGrid at the given location horizontally (given that the Word object has valid
     * location coordinates inside myGrid).
     * @param w - a Word object. The first letter of the Word object's word
     * is placed at the Word object's coordinates x and y. Then the next letter
     * is placed at the column to the right of it in myGrid and so on and so forth.
     */
    public void placeWordHorizontal(Word w)
    {
        int y = w.getY();
        int x = w.getX();
        for (int i=0; i<(w.getWord()).length() && y>=0 && y<myRows 
                                               && x+i<myColumns && x+i>=0; i++)
        {
            myGrid[y][x+i] = (w.getWord()).charAt(i);
        }
    }
    
    /**
     * After the best solution is found for the CrossWord object (an ArrayList of
     * Word objects). myGrid is resized to be as compact as possible before
     * being displayed on the screen for the user to solve.
     */
    public void resizeGrid() 
    {
        int column= 0;
        int row = 0;
        while (column < myColumns && myColumns>5)
        {
            if (columnIsEmpty(column))
            {
                removeColumn(column);
            }
            else
            {
                column++;
            }
        }
        while (row < myRows && myRows>5)
        {
            if (rowIsEmpty(row))
            {
                removeRow(row);
            }
            else
            {
                row++;
            }
        }
    }
    /**
     * Given a value for a column within myGrid, this method checks
     * to see if the column is empty - that it only contains the
     * DEFAULT_CHAR value.
     * @param cIndex - an int that is a column index for myGrid to check to see 
     * if that column is "empty"
     * @return empty - boolean value. If empty is true this column at cIndex
     * only contains DEFAULT_CHAR values. If empty is false that means it contains
     * a char that is part of a Word that was placed on the grid.
     */
    public boolean columnIsEmpty(int cIndex) 
    {
        boolean empty = true;
        for (int i=0; i<myRows && empty; i++)
        {
            empty = myGrid[i][cIndex] == DEFAULT_CHAR;
        }
        return empty;
    }
    
      /**
     * Given a value for a row within myGrid, this method checks
     * to see if the row is empty - that it only contains the
     * DEFAULT_CHAR value.
     * @param rIndex - an int that is a row index for myGrid to check to see 
     * if that row is "empty"
     * @return empty - boolean value. If empty is true this entire row at rIndex
     * only contains DEFAULT_CHAR values. If empty is false that means it contains
     * a char that is part of a Word that was placed on the grid.
     */
    public boolean rowIsEmpty(int rIndex) //Works
    {
        boolean empty = true;
        for (int i=0; i<myColumns && empty; i++)
        {
            empty = myGrid[rIndex][i] == DEFAULT_CHAR;
        }
        return empty;
    }
    
     /**
      * This method basically makes a new myGrid that has one less column. The
      * "new" myGrid doesn't contain any values from column x.
      * @param x - an int that is a column index for myGrid to "remove" (leave
      * out of the "new" myGrid)
      */    
     public void removeColumn(int x)
     {
        char[][] newGrid = new char [myRows] [myColumns-1];
        int columnIndex = 0;
        for (int i=0; i<myRows; i++)
        {
            for (int k=0; k<myColumns; k++)
            {
               if (k != x)
               {
                 newGrid[i][columnIndex] = myGrid[i][k];
                 columnIndex ++;
               }
            }
            columnIndex = 0;
        }
        myGrid = new char [myRows][myColumns-1];
        myGrid = newGrid;
        myColumns --;
    }
    
    /**
      * This method basically makes a new myGrid that has one less row. The "new"
      * myGrid doesn't contain any values from row x.
      * @param x - an int that is a row index for myGrid to "remove" 
      */  
    public void removeRow(int x)
    {
        char [][] newGrid = new char[myRows-1][myColumns];
        int rowIndex = 0;
        for (int i=0; i<myRows; i++)
        {
            if (i!=x)
            {
              for (int k=0; k<myColumns; k++)
              {
                 newGrid[rowIndex][k] = myGrid[i][k];
              }
              rowIndex++;
            }
        }
        myGrid = new char [myRows-1][myColumns];
        myGrid = newGrid;
        myRows --;
    }
    /**
     * This method copies tempGrid to bestGrid in the CrossWord class.
     * Basically it saves the best solution to bestGrid in case all
     * words can't be placed.
     * @param copy The TempGrid object that we want to copy to this myGrid.
     */
    public void copy(TempGrid copy)
    {
        for (int i=0; i<myRows; i++)
        {
            for (int k=0; k<myColumns; k++)
            {
              myGrid[i][k] = copy.get(k,i);               
            }
        }
    }
}

