

/**
 * Anywhere in the program you see something related to text files, the FileRetriever
 * class handles it. It retrieves/creates the ArrayList of words 
 * that is used to make the crossword from the text file the user selects.
 * It also gathers the names of all the text files in the VocabLists folder for
 * use in the Main class (specifically JComboBox fileBox). It also retrieves
 * the instructions from a text file and turns it into a String that is used
 * in the Instructions class (an inner class of Main).
 * 
 * @author Hope Dargan
 * @version 1.0 2/14/14
 */

import java.io.File;
import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;
public class FileRetriever
{
    /**
     * This method takes the user's desired textFile and takes the text line
     * by line to create an ArrayList of Word objects until there are no more 
     * lines left in the text file. It is set up so the first line of the text 
     * file is supposed to be a word with the next line being that word's clue for the crossword
     * puzzle and so on and so forth for the vocabulary list used to generate the
     * crossword. This method also tries to protect the user from their own mistakes
     * such as leaving a blank line at the beginning or end of the CrossWord by
     * ignoring blank lines.
     * As long as the word is first and the definition is on the next line the 
     * program will work. (see tester.txt for an example of this). This method
     * also converts all words to lower case letters only because later
     * the use of .indexOf wouldn't recognize 'A' and 'a' as the same letter.
     * 
     * @param in in is a Scanner for the text file that gets each line of text
     * individually
     * @return list is an ArrayList of Word objects. This is basically the vocab
     * list that is sent to the CrossWord class to become a CrossWord object.
     */
    public static ArrayList<Word> getList(Scanner in)
    {
       ArrayList<Word> list = new ArrayList<Word>();
       int i=0;
       String token = "";
       while(in.hasNext())
       {
           token = in.nextLine();
           if (token.length()>0)//checks to make sure line isn't blank.
           {
                list.add(new Word(token.toLowerCase(),""));
                if (in.hasNext())
                {
                    list.get(i).setDefinition(in.nextLine());
                }
                i++;
           }
       }
       return list;
    }
   
    /**
     * This method gets all the text files in the VocabLists
     * folder (except for the text file containing the instructions) and returns 
     * the names of the text files. This method is only accessed by the Main
     * class in order to display the file names in the JComboBox fileBox
     * so that the user can choose which vocabulary list they wish to 
     * generate into a crossword.
     * The "source" code was just used as a template so I knew how to do it,
     * it was modified specifically to fit this program. 
     * Source: http://stackoverflow.com/questions/14997765/make-jbuttons-for-each-file-in-directory
     * 
     * @return String[] this method returns an array of Strings. Specifically 
     * the names of the text files found in the VocabLists folder 
     * (excluding Instructions.txt).
     */
    public static String[] getTextFileNames() 
    {
        File folder = new File("VocabLists/.");
        String file;
        File[] allFiles = folder.listFiles();
        String[] fileNames;
        ArrayList<File> tempFileList = new ArrayList<File>();
        for (int i = 0; i < allFiles.length; i++) 
        {
            file = allFiles[i].getName();
            if (file.substring(file.length()-4).equals(".txt") && !(file.equals("Instructions.txt")))
            {
                tempFileList.add(allFiles[i]);
            }
        }
        fileNames = new String[tempFileList.size()];
        for (int i=0; i<tempFileList.size(); i++)
        {
           fileNames[i] = tempFileList.get(i).getName();
        }
        return fileNames;
    }
    
    /**
     * This method gets the Instruction.txt file in the VocabLists
     * folder and returns the text in the form of a String. This method is
     * only accessed by the Instructions (inner class of Main)
     * class in order to display the Instructions in the Instruction screen. 
     * 
     * @return String this method returns a String of the text in the 
     * Instruction.txt file.
     */
    public static String getInstructions() throws IOException
    {
      File instructionFile = new File("VocabLists/Instructions.txt");
      Scanner in = new Scanner(instructionFile);
      String instructionText = "";
      while(in.hasNext())
       {
           instructionText += in.nextLine() + "\n";
       }
      in.close();
      return instructionText;
    }
}