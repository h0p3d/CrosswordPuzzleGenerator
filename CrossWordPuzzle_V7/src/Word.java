/**
 * This class basically stores information for the words. CrossWord objects
 * are basically ArrayLists of Words. The Word object contains each word's 
 * name, definition, position (before tempGrid is resized) and problem number.
 * This class provides various ways for other classes to access or change
 * information about a Word object.
 * 
 * @author Hope Dargab
 * @version 1.0 2/14/14
 */
public class Word
{
    // instance variables
    private String myWord;
    private String myDefinition;
    private int myX;
    private int myY;
    private boolean isVertical;
    private int myProblemNum;
    
    /**
     * This the constructor for the Word class. It initializes all the
     * default values (myX, myY, myProblemNum, and isVertical are all set to
     * default values).
     * 
     * @param word This is a string. It is the word placed in the crossword.
     * @param definition This is the definition, or the clue that is associated
     * with the word
     */
    public Word(String word, String definition)
    {
      myWord = word;
      myDefinition = definition;
      myX = -1;
      myY = -1;
      myProblemNum = -1;
      isVertical = false;
    }
    /**
     * This method is a basic mutator method. It is never used in the program
     * itself but it is provided in case someone wanted to test or develop
     * something and needed to change the word.
     * 
     * @param str This string is the word you would like to change the word to.
     */
    public void setWord (String str)
    {
        myWord = str;      
    }
    /**
     * Basic getter method that returns a String that is the word for that Word
     * object.
     * @return String word -returns a String that is the word for the given
     * Word object.
     */
    public String getWord ()
    {
        return myWord;
    }
    
    /**
     * This method is a basic mutator method. It is never used in the program
     * itself but it is provided in case someone wanted to test or develop
     * something and needed to change the definition.
     * 
     * @param str This string is the definition you would like to change the 
     * definition of the Word object to.
     */
    public void setDefinition(String str)
    {
        myDefinition = str;
    }
    
    /**
     * Basic getter method that returns a String that is the definition
     * for that Word object.
     * @return String definition -returns a String that is the definition for 
     * the given Word object.
     */
    public String getDefinition()
    {
        return myDefinition;
    }
    
    /**
     * Basic setter method that sets an int for the X-coordinate or column 
     * coordinate for the Word object in the TempGrid before TempGrid is 
     * resized.
     * @param x - an int that is the x (column) coordinate of the word in the
     * TempGrid before it is resized.
     */
    public void setX(int x)
    {
        myX = x;
    }
    /**
     * Basic getter method that returns an int that is the x-coordinate for 
     * that Word object.
     * @return int x -returns an int that is the x or column coordinate of the
     * Word object in the TempGrid (before TempGrid is resized).
     */
    public int getX()
    {
       return myX;
    }
    
    /**
     * Basic setter method that sets an int for the Y-coordinate or row 
     * coordinate for the Word object in the TempGrid object before the TempGrid
     * object is resized.
     * @param y - an int that is the y (row) coordinate of the word in the
     * TempGrid before it is resized.
     */
    public void setY(int y)
    {
        myY = y;
    }
    /**
     * Basic getter method that returns an int that is the y-coordinate for 
     * that Word object.
     * @return int y -returns an int that is the y or row coordinate of the
     * Word object in the TempGrid (before TempGrid is resized).
     */
    public int getY()
    {
       return myY;
    }
    
     /**
     * Basic setter method that sets an boolean value for the Word object.
     * If isVertical is true then the word will be placed in the TempGrid object 
     * vertically. If isVertical is false then the word will be placed
     * horizontally in the TempGrid object.
     * @param vertical - a boolean value that determines if the Word object is
     * placed vertically or horizontally
     */
    public void setVertical(boolean vertical)
    {
        isVertical = vertical;
    }
    /**
     * Basic getter method that return the isVertical value for the Word object.
     * If isVertical is true then the word will be placed in the TempGrid object 
     * vertically. If isVertical is false then the word will be placed
     * horizontally in the TempGrid object.
     * @return boolean isVertical - returns a boolean value that determines if 
     * the Word object is placed vertically or horizontally
     */
    public boolean getVertical()
    {
       return isVertical;
    }
    
    /**
     * Basic setter method that sets an int for the problem number 
     * for the Word object. 
     * @param num - an int that is assigned to be the problem number for the 
     * Word object.
     */
    public void setProblemNum(int num)
    {
        myProblemNum = num;
    }
    /**
     * Basic getter method that gets and returns the int set for the problem 
     * number of the Word object. 
     * @return myProblemNum - an int that is assigned to be the problem number 
     * for the Word object.
     */
    public int getProblemNum()
    {
       return myProblemNum;
    }
    
    /**
     * This method determines if this Word has been "placed" (or at least
     * assigned a place) in the TempGrid object (before the TempGrid object
     * is resized.
     * @return boolean - returns a boolean to see if both myX and myY are 
     * greater than -1. Default values are initialized to -1 for both ints.
     */
    public boolean beenPlaced()
    {
        return myY != -1 && myX != -1;
    }
    
    /**
     * This method is another way to change the values of both the myX
     * and myY (coordinate values for TempGrid object pre-resizing) 
     * 
     * @param coordinates an int array that contains an int to set as X in 
     * position 0 and an int to set as Y in position 1
     */
    public void setCoordinates(int[] coordinates)
    {
        if (coordinates.length>=2)
        {
            myX= coordinates[0];
            myY= coordinates[1];
        }
    }
}

