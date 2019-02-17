/**
 * This class's main function is to generate (or solve) the crossword and have
 * the solution stored in a TempGrid object (basically a 2d array of char).
 * @author Hope Dargan
 * @version 1.0 2/15/14
 */
import java.util.ArrayList;
public class CrossWord
{
    //private variables initialized to defaults
    private ArrayList<Word> myList;
    private int bestLettersUsed = 0;
    private int lettersUsed = 0;
    private boolean solutionFound = false;
    final int SIZE = 80; //sets default row/column length of the TempGrid object that the Word objects will be placed in
    private TempGrid tempGrid = new TempGrid(SIZE, SIZE);
    private TempGrid bestGrid = new TempGrid(SIZE, SIZE);
    private String originalWordList;
    private int[][] bestCoordinates;
    /**
     * This constructor sets the ArrayList of words equal to an ArrayList of Word
     * objects that is passed in.
     * @param list an ArrayList of Words that contains the vocab list data from the 
     * text file
     */
    public CrossWord(ArrayList<Word> list)
    {
        myList = list;
    }
    /**
     * This is a simple getter method that returns the TempGrid object where
     * the final solution is stored.
     * @return tempGrid a TempGrid object where the final solution is stored.
     */
    public TempGrid getTempGrid()
    {
        return tempGrid;
    }
    
    /**
     * This returns n array of strings for all the definitions of words
     * placed vertically. This method is used to display the horizontal and
     * vertical clues in separate JList objects. Note that when this method
     * is called by the DisplayCrossWord Class that myList has been sorted
     * by problem number and horizontal/vertical (order is 1 vertical 2 vertical...
     * 1 horizontal...)
     * @return vertical an array of Strings that contains the clues or definitions
     * for all the words placed vertically in the CrossWord.
     */
    public String[] getVerticalClueList()
    {
        String[] vertical = new String[numVerticalWords()+1];
        vertical[0]= "Clues for Vertical Words: ";
        int index = 1;
        for(int i=0; i<myList.size() && index<vertical.length; i++)
        {
            if (myList.get(i).getVertical())
            {
                vertical[index] = myList.get(i).getProblemNum() + " - " + myList.get(i).getDefinition();
                index++;
            }
        }
        return vertical;        
    }
    
    /**
     * This returns n array of strings for all the definitions of words
     * placed horizontally. This method is used to display the horizontal and
     * vertical clues in separate JList objects. Note that when this method
     * is called by the DisplayCrossWord Class that myList has been sorted
     * by problem number and horizontal/vertical (order is 1 vertical 2 vertical...
     * 1 horizontal...)
     * @return horizontal an array of Strings that contains the clues or definitions
     * for all the words placed horizontally in the CrossWord.
     */
    public String[] getHorizontalClueList()
    {
        String[] horizontal = new String[myList.size()-numVerticalWords()+1];
        horizontal[0] = "Clues for Horizontal Words: ";
        int index = 1;
        for(int i=0; i<myList.size() && index<horizontal.length; i++)
        {
            if (!myList.get(i).getVertical())
            {
                horizontal[index] = myList.get(i).getProblemNum() + " - " + myList.get(i).getDefinition();
                index++;
            }
        }
        return horizontal;
    }
    
    /**
     * This method returns a String object that has all the words in their 
     * original order separated by a line. This method is only used to 
     * display the word list in the DisplayWordBank inner class of DisplayCrossWord.
     * @return originalWordList the Word object words from myList in their original
     * order separated by a new line for every word 
     * ex:
     * apple
     * banana
     */
    public String getOriginalWordList()
    {
      return originalWordList;
    }
     /**
     * This method makes a String that has all the words in their 
     * original order separated by a line. 
     * ex:
     * apple
     * banana
     */
    public void makeOriginalWordList()
    {
        originalWordList = "";
        for (int i=0; i<myList.size(); i++)
        {
            originalWordList += myList.get(i).getWord() + "\n";
        }
    }
    
    /**
     * This method takes the "original" myList and sorts the Word object's words in
     * it by length largest to smallest using a selection sort.
     */
    public void sortLength()
    {
      int maxIndex = 0;
      Word temp = new Word("","");
      for (int i=0; i<myList.size(); i++)
      {
        maxIndex = i; 
        for (int k=i; k<myList.size(); k++)
        {
            maxIndex = (myList.get(k).getWord()).length() > (myList.get(maxIndex).getWord()).length() ? k : maxIndex;
        }
        temp = myList.get(i);
        myList.set(i, myList.get(maxIndex));
        myList.set(maxIndex, temp);
       
      }
    }
    
    /**
     * This method sorts the Word objects in the myList ArrayList after they have
     * been placed in the grid. The order numerically and by whether the word
     * was placed vertically or horizontally. The word to the top left most
     * hand of the TempGrid object is considered 1. myList is sorted so that
     * the order is 1 vertical 2 vertical...1 horizontal 2 horizontal...
     */
    public void sortListByProblemNum()
    {
        int horizontalStartIndex = numVerticalWords();
        Word temp = new Word("","");
        for (int i=0; i<myList.size(); i++)
        {
            int probIndexV = 1;
            int probIndexH = 1;
            for (int k=0; k<myList.size(); k++)
            {
                if (myList.get(k).getVertical() && myList.get(k).getProblemNum() == probIndexV)
                {
                   temp = myList.get(probIndexV-1);
                   myList.set(probIndexV-1, myList.get(k));
                   myList.set(k, temp);
                   probIndexV ++;
                }
                if (!myList.get(k).getVertical() && myList.get(k).getProblemNum() == probIndexH)
                {
                   temp = myList.get(probIndexH-1+horizontalStartIndex);
                   myList.set(probIndexH-1+horizontalStartIndex, myList.get(k));
                   myList.set(k, temp);
                   probIndexH ++;
                }
                
            }
                
        }
    }
    /**
     * This method goes through myList and counts the number of words
     * placed vertically in the TempGrid object tempGrid.
     * @return numVertical returns the number of words "placed" vertically in
     * the TempGrid object (isVertical is true for that Word object)
     */
    public int numVerticalWords()
    {
        int numVertical = 0;
        for (int i=0; i<myList.size(); i++)
        {
            numVertical += myList.get(i).getVertical() ? 1 : 0;
        }
        return numVertical;
    }
    
    /**
     * This is almost the "main" method of the CrossWord class - this is
     * where the CrossWord is generated. I am going to comment the code as well
     * as explain exactly what this method does in order to generate a crossword.
     * 1) initializes everything, gets everything prepared for generating the
     * crossword
     * 2) goes through myList from largest to smallest. The Word object at index
     * i is placed first at a default location every time. The idea is that
     * if the longest word doesn't result in a solution being found maybe the
     * next longest one will.
     * 3) loops through all the words in myList, looking for the largest word
     * that hasn't been placed yet. Also checks to see if all the words have 
     * been placed (if solutionFound is true there is no need to continue looking
     * for a solution). Once it finds the largest Word object's word that hasn't been placed
     * it tries to place it (see placeWord method).
     * 4) For every crossWord that is generated using index i as the firstWord,
     * the program keeps track of the letters used. The solution with the most 
     * letters used is considered the "best" solution until a solution that uses all
     * words is found. This is a backup in case a solution using all the words 
     * cannot be found, then the best solution will be displayed.
     * 5) if a solution isn't found for using index i as the first word placed
     * this method "clears the board" and starts all over again
     * 6) if a complete solution isn't found then the best solution is chosen
     * as the solution. Any words that haven't been placed will have a default
     * problem number of -1 and will not be displayed on the crossword puzzle.
     * 7) This is kind of the clean up part of the method. It resizes the 
     * grid to be as compact as possible (see TempGrid class), it assigns problem
     * numbers and it sorts myList by those problem numbers/ whether the Word
     * object's word was placed horizontally or vertically.
     */
    public void CrossWordSolver()
    {
       //1 
       makeOriginalWordList();
       tempGrid.makeBlankGrid();
       sortLength();
       //2
       for (int i=0; i<myList.size() && !solutionFound; i++)
       {
           myList.get(i).setCoordinates(new int[] {20,20});
           myList.get(i).setVertical(false);
           tempGrid.placeWordHorizontal(myList.get(i));
           lettersUsed += (myList.get(i).getWord()).length();
           //3
           for (int k=0; k<myList.size() && !solutionFound; k++)
           {
               solutionFound = solutionFound? solutionFound : allPlaced();
               for (int m=0; m<myList.size() && !solutionFound; m++)
               {
                   if ( !(myList.get(m).beenPlaced()) )
                   {
                       placeWord(m);
                   }
               }
           }
           //4
           if (lettersUsed > bestLettersUsed)
           {
               bestLettersUsed = lettersUsed;
               bestGrid.copy(tempGrid);
               setBestCoordinates();
           }
           //5
           lettersUsed = 0;
           if (!solutionFound)
           {
               tempGrid.makeBlankGrid();
               clearCoordinates();
           }
       }
       //6
       if (!solutionFound)
       {
           tempGrid = bestGrid;
           for (int i=0; i<myList.size(); i++)
           {
             myList.get(i).setCoordinates(bestCoordinates[i]);
           }
       }
       //7
       tempGrid.resizeGrid();
       assignProblemNumbers();
       sortListByProblemNum();
    }
    
    /**
     * Like the CrossWordSolver method I am going to explain and comment the 
     * code so you can see how this method places words.
     * 1 - sets the coordinates the Word object's word will be placed at outside
     * the grid. Coordinates refer to the location where a Word object's
     * word's first letter will be placed.
     * 2- it loops through every letter in the word, looking for a word that 
     * has been placed with a common letter.
     * 3- if a common letter is found the method tracks the index of the Word
     * object that the letter is shared with and where that common letter
     * was found and calculates the coordinates with the above information (see
     * calcCoordinates method).
     * 4- the looping will stop if the calculated coordinates are a valid place 
     * where the Word object's word can be placed (see canPlace method)
     * 5 - once a valid place for the Word object's word has been found the
     * word is placed there and the number of letters placed on the grid increases
     * @param index an int that represents an index in myList that contains the
     * word object this method is trying to place
     */
    public void placeWord(int index)
    {
        int [] coordinates = new int[]{-1,-1}; //1
        //2
        for (int i=0; i<(myList.get(index).getWord()).length() && !canPlace(index, coordinates); i++)//4
        {
            for (int k=0; k<myList.size() && !canPlace(index, coordinates); k++)//4
            {
                char searchingFor = (myList.get(index).getWord()).charAt(i);
                if (myList.get(k).beenPlaced() )
                {
                    int letterIndex = (myList.get(k).getWord()).indexOf(searchingFor+"");
                    int wordIndex = k;
                    //3
                    coordinates = letterIndex>-1 ? calcCoordinates(index, wordIndex, letterIndex) : coordinates;
                }
            }
        }
        //5
        if (canPlace(index, coordinates))
        {
          myList.get(index).setCoordinates(coordinates);
          if (myList.get(index).getVertical())
          {
            tempGrid.placeWordVertical(myList.get(index));
          }
          else
          {
            tempGrid.placeWordHorizontal(myList.get(index));
          }
          lettersUsed += (myList.get(index).getWord()).length();
        }
    }
    /**
     * Given the index in myList of the Word object you are trying to place,
     * the index of the Word object that the word is intersecting, and the
     * location on the placed Word object's word that the intersection is going
     * to happen this method calculates where the first letter of 
     * the Word object's word you are trying to place will be placed.
     * @param placingWord an int that represents an index in myList that represents
     * the Word object's word that you are trying to place in tempGrid.
     * @param placedWord an int that is an index of a Word object's word that
     * has already been placed in tempGrid
     * @param placedWordLetterIndex an int that represents the index of a letter
     * in a Word object's word. This is the proposed point of intersection for
     * the myList's Word object at index placingWord and  myList's Word
     * object at index placedWord.
     */
    public int[] calcCoordinates(int placingWord, int placedWord, int placedWordLetterIndex)
    {
        int[] coordinates = new int[] {-1,-1};
        myList.get(placingWord).setVertical(!(myList.get(placedWord).getVertical()));
        if (myList.get(placedWord).getVertical())
        {
            coordinates[1] = myList.get(placedWord).getY()+placedWordLetterIndex;
            coordinates[0] = myList.get(placedWord).getX()- 
                      (myList.get(placingWord).getWord()).indexOf(myList.get(placedWord).getWord().charAt(placedWordLetterIndex)+"");
        }
        else
        {
            coordinates[0] = myList.get(placedWord).getX()+placedWordLetterIndex;
            coordinates[1] = myList.get(placedWord).getY()- 
                      (myList.get(placingWord).getWord()).indexOf(myList.get(placedWord).getWord().charAt(placedWordLetterIndex)+"");
        }
        return coordinates;
    }
    
    /**
     * This method determines if the Word object's word at wordIndex myList
     * can be placed in the TempGrid object tempGrid at the coordinated location.
     * (Coordinates represent where the Word object's word first letter is placed)
     * @param wordIndex int the index in myList of the Word object's word that is 
     * trying to be placed at location given by coordinates
     * @param coordinates an int array with two positions coordinates[0] is the "x" or column coordinate in tempGrid, 
     * coordinates[1] is 
     * @return boolean this method returns a boolean. If it is true that means that the Word object's word can safely be
     * placed at the coordinates location in tempGrid. 
     */
    public boolean canPlace(int wordIndex, int[] coordinates)
    {
        int badIntersections = 0;
        int tooClose = 0;
        boolean validCoordinates = coordinates[0] != -1 && coordinates[1] != -1;
        boolean goodIntersection = false;
        for (int i=0; validCoordinates && i<myList.get(wordIndex).getWord().length() && tooClose == 0 && badIntersections == 0; i++)
        {
            if ( myList.get(wordIndex).getVertical())
            {
                goodIntersection = tempGrid.get(coordinates[0], coordinates[1]+i) == (myList.get(wordIndex).getWord()).charAt(i);
                badIntersections += (tempGrid.get(coordinates[0], coordinates[1]+i) == tempGrid.getDefaultChar() || goodIntersection) ? 0:1;
                tooClose += goodIntersection || tempGrid.get(coordinates[0]-1, coordinates[1]+i) == tempGrid.getDefaultChar() ? 0 : 1;
                tooClose += goodIntersection || tempGrid.get(coordinates[0]+1, coordinates[1]+i) == tempGrid.getDefaultChar() ? 0 : 1;
                if (i==0)
                {
                    tooClose += tempGrid.get(coordinates[0], coordinates[1]-1) == tempGrid.getDefaultChar()? 0 : 1;
                    tooClose += tempGrid.get(coordinates[0], coordinates[1]+(myList.get(wordIndex).getWord()).length()) 
                                                                                                    == tempGrid.getDefaultChar()? 0 : 1;
                }                 
                goodIntersection = false;
            }
            else
            {
                goodIntersection = tempGrid.get(coordinates[0]+i, coordinates[1]) == (myList.get(wordIndex).getWord()).charAt(i);
                badIntersections += (tempGrid.get(coordinates[0]+i, coordinates[1]) == tempGrid.getDefaultChar() || goodIntersection) ? 0 : 1;
                tooClose += goodIntersection || tempGrid.get(coordinates[0]+i, coordinates[1]-1) == tempGrid.getDefaultChar() ? 0 : 1;
                tooClose += goodIntersection || tempGrid.get(coordinates[0]+i, coordinates[1]+1) == tempGrid.getDefaultChar() ? 0 : 1;
                if (i==0)
                {
                    tooClose += tempGrid.get(coordinates[0]-1, coordinates[1]) == tempGrid.getDefaultChar()? 0 : 1;
                    tooClose += tempGrid.get(coordinates[0]+(myList.get(wordIndex).getWord()).length(), coordinates[1])
                                                                                                    == tempGrid.getDefaultChar()? 0 : 1;
                }
                goodIntersection = false;
            }
                
        }
        return validCoordinates && tooClose == 0 && badIntersections == 0;
    }
    /**
     * This method stores the coordinates of all the myList's Word object's words 
     * for the best solution in a 2d array of ints. This is so in case all the
     * words cannot be placed that the best grid can still be shown and problem
     * numbers can still be assigned.
     */
    public void setBestCoordinates()
    {
        bestCoordinates = new int[myList.size()][2];
        for (int i=0; i<bestCoordinates.length; i++)
        {
          bestCoordinates[i][0] = myList.get(i).getX();
          bestCoordinates[i][1] = myList.get(i).getY();
        }
    }
    
    /** 
     * This method assigns the problem numbers to the vertical and horizontally placed words.
     * The word that is vertical and is placed in the highest position is considered 1.
     * The word that is horizontal and is placed in the highest position is also considered 1.
     * If a word was not placed in TempGrid object tempGrid (x and y coordinates are -1), then its
     * problem number is -1.
     * If two words are both placed on the same row, then
     * the leftmost word is the lower problem number.
     */
    public void assignProblemNumbers()
    {
        int verticalNum = 1;
        int horizontalNum = 1;
        int lowHorizontal = -1;
        int lowVertical = -1;
        int numAssigned = 0;
        for(int i=0; i<myList.size(); i++)
        {
          if (myList.get(i).getX() == -1 && myList.get(i).getY() == -1)
          {
              numAssigned ++;
          }
        }
        while (numAssigned < myList.size())
        {
          for (int i=0; i<myList.size(); i++)
          {
              if (myList.get(i).getVertical() && myList.get(i).getProblemNum() == -1 
                                              && myList.get(i).getX() != -1  && myList.get(i).getY()!=-1)
              {
                  lowVertical = lowVertical == -1? i : lowVertical;
                  if (myList.get(lowVertical).getY() == myList.get(i).getY())
                  {
                      lowVertical = myList.get(lowVertical).getX() > myList.get(i).getX() ? i : lowVertical;
                  }
                  else
                  {
                      lowVertical = myList.get(lowVertical).getY() > myList.get(i).getY() ? i : lowVertical;
                  }
              }
              if ( !(myList.get(i).getVertical()) && myList.get(i).getProblemNum() == -1
                                                  && myList.get(i).getX() != -1  && myList.get(i).getY()!=-1)
              {
                  lowHorizontal = lowHorizontal == -1? i : lowHorizontal;
                  if (myList.get(lowHorizontal).getY() == myList.get(i).getY())
                  {
                      lowHorizontal = myList.get(lowHorizontal).getX() > myList.get(i).getX() ? i : lowHorizontal;
                  }
                  else
                  {
                      lowHorizontal = myList.get(lowHorizontal).getY() > myList.get(i).getY() ? i : lowHorizontal;
                  }
              }
          }
          if (lowVertical != -1)
          {
              myList.get(lowVertical).setProblemNum(verticalNum);
              verticalNum ++;
              lowVertical = -1;
              numAssigned++;
          }
          if (lowHorizontal != -1)
          {
            myList.get(lowHorizontal).setProblemNum(horizontalNum);
            horizontalNum++;
            lowHorizontal = -1;
            numAssigned ++;
          }
        }
        
    }
    /**
     * This method "resets" all the Word object's coordinates in myList
     * by setting all the coordinates to their default values.
     */
    public void clearCoordinates()
    {
        for (int i=0; i<myList.size(); i++)
        {
            myList.get(i).setCoordinates(new int[] {-1,-1});
        }
    }
    
    /**
     * This method checks to see if all the Word objects in myList have been
     * placed as in they all have coordinates that are not the default value.
     * @return placed a boolean value. If placed is true then it means that all
     * the Word objects in myList have coordinates that are not default values
     * (see beenPlaced method in class Word).
     */
    public boolean allPlaced()
    {
        boolean placed = true;
        for (int i=0; i<myList.size(); i++)
        {
            placed = placed ? myList.get(i).beenPlaced() : placed;
        }
        return placed;
    }
    
    /**
     * This is a method that was used for testing purposes to see that the
     * originalWordList had the correct value. Basically a tester method
     * for makeOriginalWordList method.
     */
    public void printOriginalWordList()
    {
        System.out.println(originalWordList);
    }
}

