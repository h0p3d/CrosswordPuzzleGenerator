/**
 * This class has inner classes that handle anything graphics wise for actually displaying
 * the generated crossword screen and checking user input.
 * 
 * @author Hope Dargan
 * @version 1.0 2/15/14
 */
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class DisplayCrossWord
{
  private CrossWord vocab; 
  /**
   * This constructor initializes the CrossWord object vocab which can access
   * the TempGrid object that is the solution to the crossword. Vocab is a
   * CrossWord object that is basically an ArrayList of Word objects.
   */
  public DisplayCrossWord(String name) throws IOException
  {
     File vocabFile =  new File("VocabLists/"+name);
     Scanner in = new Scanner(vocabFile);
     vocab = new CrossWord(FileRetriever.getList(in));
     in.close();
     vocab.CrossWordSolver();
     new CrossWordGUI().setVisible(true);
  }
  
  /**
   * This inner class initializes and displays all the graphics related components.
   * Basically if the user will see it, it is in here.
   */
  private class CrossWordGUI extends JFrame
  {
        private CrossWordPanel cPanel;
        private JPanel mainPanel;
        private JPanel crossWordPanel;
        private JList horizontalList;
        private JList verticalList;
        private JScrollPane jScrollPane1;
        private JScrollPane jScrollPane2;
        private JButton menuButton;
        private JButton solutionButton;
        private JButton wordButton;
        /**
         * This constructor initializes all the graphic components that are used to make the crossword screen.
         * It also adds actionListeners to the JButtons that will be displayed
         * on this screen.
         */
        public CrossWordGUI()
        {
           super ("CrossWord Puzzle");
           super .setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
           cPanel = new CrossWordPanel();
           cPanel.setCrossWord();
           mainPanel = new JPanel();
           crossWordPanel = new JPanel(new FlowLayout());
           crossWordPanel.add(cPanel);
        
           solutionButton = new JButton("Check Solution");
           solutionButton.addActionListener(new aHandlerClass());
           wordButton = new JButton("Word Bank");
           wordButton.addActionListener(new aHandlerClass());
           menuButton = new JButton("Main Menu");
           menuButton.addActionListener(new aHandlerClass());
        
           jScrollPane1 = new JScrollPane();
           verticalList = new JList();
           jScrollPane2 = new JScrollPane();
           horizontalList = new JList();  
           initComponents();
        }
        /**
        * This code was generated using the NetBeans IDE. This method creates the layout of the
        * objects on the crossword screen. I edited this method a little to make it more "readable".
        */      
        private void initComponents() 
        {      
            verticalList.setModel(new AbstractListModel() 
            {
                 String[] strings = vocab.getVerticalClueList();
                 @Override
                 public int getSize() { return strings.length; }
                 @Override
                 public Object getElementAt(int i) { return strings[i]; }
            });
            jScrollPane1.setViewportView(verticalList);

            horizontalList.setModel(new AbstractListModel() 
            {
                String[] strings = vocab.getHorizontalClueList();
                @Override
                public int getSize() { return strings.length; }
                @Override
                public Object getElementAt(int i) { return strings[i]; }
            });
            jScrollPane2.setViewportView(horizontalList);      

            GroupLayout mainPanelLayout = new GroupLayout(mainPanel);
            mainPanel.setLayout(mainPanelLayout);
            mainPanelLayout.setHorizontalGroup
            (
                mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addComponent(solutionButton, GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                    .addComponent(wordButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(menuButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                    .addGap(18, 18, Short.MAX_VALUE)
                    .addComponent(crossWordPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            );
            mainPanelLayout.setVerticalGroup
            (
                mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(mainPanelLayout.createSequentialGroup()
                    .addGap(11, 11, 11)
                    .addComponent(menuButton, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(wordButton, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(solutionButton, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 94, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
                    .addComponent(crossWordPanel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );

            GroupLayout layout = new GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup
            (
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );
            layout.setVerticalGroup
            (
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );

            pack();     
        }       
        /**
        * The ActionHandlerClass implements the ActionListener class. Basically, it allows
        * the program to respond to the user clicking one of the buttons on the crossword screen.
        */ 
        private class aHandlerClass implements ActionListener
        {
            /**
            * This method was required to implement ActionListender. If the JButton solutionButton
            * is clicked, this method responds by calling on another method to calculate
            * the number of mistakes the user made. If the JButton wordButton is clicked
            * this method responds by making the word bank visible. If the JButton 
            * menuButton is selected the crossword screen "hides" and 
            * the main menu is made visible.
            * @param event Creates an event when one of the JButtons on then 
            * crossword screen has been clicked by the user.
            */
            public void actionPerformed(ActionEvent event)
            {
                if (event.getSource() == solutionButton)
                {
                    int numMistakes = cPanel.calcMistakes();
                    if (numMistakes == 0)
                    {
                        JOptionPane.showMessageDialog(null,"Congratulations! You have successfully completed the CrossWord!", "Congratulations!!!", JOptionPane.PLAIN_MESSAGE);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null,"There are " + numMistakes + " mistakes.", "Not Complete", JOptionPane.PLAIN_MESSAGE);
                    }
                }
                if (event.getSource() == wordButton)
                {
                    new DisplayWordBank().setVisible(true);
                }
                if (event.getSource() == menuButton)
                {
                    setVisible(false);
                    new Main().setVisible(true);
                }
            }
        }
        /**
         * This inner class deals with generating the crossword itself and
         * checking the user input to determine how many mistakes the user has
         * made. This class also has an inner class JTextFieldLimit that
         * will only allow the user to put one character in each JTextField.
         */
        private class CrossWordPanel extends JPanel
        {
            private JTextField textFields[][];
            private char[][] solution;
            private char[][] userInput;
            private final int numRows;
            private final int numColumns;
            private char blank;
            
            /**
            * This constructor initializes all the graphic components that are used to make and check the JPanel
            * containing the crossword.
            */
            public CrossWordPanel()
            {
                solution = vocab.getTempGrid().getGrid();
                blank = vocab.getTempGrid().getDefaultChar();
                numRows = solution.length;
                numColumns = solution[0].length;
                textFields = new JTextField[numRows][numColumns]; 
                userInput = new char[numRows][numColumns];
            }
            /**
             * This method adds "blank" JTextFields to the JCrossWordPanel to make
             * a crossword puzzle the user can solve. The idea for this method
             * was inspired by this source:
             * http://stackoverflow.com/questions/21755117/what-is-the-best-way-to-create-gui-for-a-crossword-puzzle-java
             */
            public void setCrossWord()
            {
                removeAll();
                setLayout(new GridLayout(numRows, numColumns));
                textFields = new JTextField[numRows][numColumns];

                for (int y=0; y<numRows; y++)
                {
                    for (int x=0; x<numColumns; x++)
                    {
                        char temp = solution[y][x];
                        if (temp != blank)
                        {
                            
                            textFields[y][x] = new JTextField(2);
                            textFields[y][x].setDocument(new JTextFieldLimit(1));
                            textFields[y][x].setFont(textFields[y][x].getFont().deriveFont(16.0f));
                            add(textFields[y][x]);
                        }
                        else
                        {
                            add(new JLabel());
                        }
                    }
                }
                repaint(); 
            }
            /**
             * This method gets the user input on the crossword puzzle and
             * stores it in a 2d array of characters. 
             * This is a modified method from this source:
             * http://stackoverflow.com/questions/21755117/what-is-the-best-way-to-create-gui-for-a-crossword-puzzle-java
             */
            public void getInput()
            {
                for (int r=0; r<numRows; r++)
                {
                    for (int c=0; c<numColumns; c++)
                    {
                        if (textFields[r][c]!= null)
                        {
                            String s = textFields[r][c].getText();
                            if (s.length() > 0)
                            {
                                userInput[r][c] = s.charAt(0);
                            }
                            else
                            {
                                userInput[r][c] = blank;
                            } 
                        }
                        else
                        {
                            userInput[r][c] = blank;
                        }
                    }
                }
            }
            /**
             * This method compares the user input to the actual solution for the
             * crossword and counts the number of user mistakes. It also
             * highlights the JTextFields containing mistakes in red and
             * colors the JTextFields with correct input in white.
             * @return numMistakes an int that represents the number of user mistakes
             */
            public int calcMistakes()
            {
                int numMistakes = 0;
                getInput();
                for (int r=0; r<numRows; r++)
                {
                    for (int c=0; c<numColumns; c++)
                    {
                        if (solution[r][c] != blank)
                        {
                            if ((userInput[r][c]+"").equalsIgnoreCase(solution[r][c]+""))
                            {
                                textFields[r][c].setBackground(Color.WHITE);
                            }
                            else
                            {
                                numMistakes++;
                                textFields[r][c].setBackground(new Color(255,125,125)); // a light red color
                            }   
                        }
                    }
                }
                repaint();
                return numMistakes;
            }
            /**
             * This inner (inner) class of sorts was generated by someone else. 
             * Its purpose is to limit the number of characters the user can put 
             * into the JTextField.
             * Source:
             * http://stackoverflow.com/questions/10136794/limiting-the-number-of-characters-in-a-jtextfield
             */
            private class JTextFieldLimit extends PlainDocument 
            {
                private int limit;
                JTextFieldLimit(int limit) 
                {
                    super();
                    this.limit = limit;
                }

                JTextFieldLimit(int limit, boolean upper) 
                {
                    super();
                    this.limit = limit;
                }

                public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException 
                {
                    if (str != null && (getLength() + str.length()) <= limit) 
                    {
                        super.insertString(offset, str, attr);
                    }
                }
            }
        }    
         /**
        * The inner class Instructions extends JFrame. It contains the code 
        * required to display word bank for the user.
        */
        private class DisplayWordBank extends JFrame
        {
       
            private JPanel panel;
            private GroupLayout panelLayout;
            private String wordString;
            private JScrollPane jScrollPane1;
            private JTextArea textArea;
            
            /**
            * This constructor initializes all the graphic components that are used to make the word bank screen.
            */
            public DisplayWordBank()
            {
                super ("Word Bank");
                super .setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                panel = new JPanel();
                panelLayout = new GroupLayout(panel);
                wordString = vocab.getOriginalWordList();
                jScrollPane1 = new javax.swing.JScrollPane();
                textArea = new javax.swing.JTextArea();
          
                textArea.setColumns(20);
                textArea.setRows(5);
                textArea.insert(wordString, 0);
                textArea.setEditable(false);
                textArea.setBackground(new Color(240,240,240));
                textArea.setFont(new Font("TimesNewRoman",Font.PLAIN,18));
                jScrollPane1.setViewportView(textArea);
                initWordBank();
            }
            /**
            * This code was generated using the NetBeans IDE. This method creates the layout of the
            * objects used to make the instruction screen. I edited this method a little to make it more "readable".
            */
            private void initWordBank()
            {
                GroupLayout panelLayout = new GroupLayout(panel);
                panel.setLayout(panelLayout);
                panelLayout.setHorizontalGroup
                (
                    panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                );
                panelLayout.setVerticalGroup
                (
                    panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
                );

                GroupLayout layout = new GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup
                (
                    layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                );
                layout.setVerticalGroup
                (
                    layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                );

                pack();
            }
        }
    }
}