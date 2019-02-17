/**
 * This class is the main class of the program. It contains code related to displaying the main screen.
 * It extends JFrame.
 * @author Hope Dargan
 * @version 1.0 2/14/14
 */

//import statements
import java.io.IOException;
import java.awt.*;
import java.io.File;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;

public class Main extends JFrame
{
    private JButton start;
    private JButton instructions;
    private JComboBox fileBox;
    private JLabel fileNameLabel;
    private JLabel title;
    private String[] fileNames = FileRetriever.getTextFileNames(); //see file retriever class
    private JPanel contentPane = new JPanel();
    private GroupLayout contentPaneLayout = new GroupLayout(contentPane);
    
    /**
     * This constructor initializes all the graphic components that are used to make the main menu.
     * It also adds actionListeners or itemListeners to the JButtons and the JComboBox that will be displayed
     * on the main menu.
     */
    public Main() 
    {
        super("CrossWord Puzzle!!!"); // sets title of JFrame
        super .setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        start = new JButton("Start");
        instructions = new JButton("Instructions");
        fileBox = new JComboBox(fileNames);
        fileBox.addItemListener(new ItemHandlerClass()); 
        start.addActionListener(new ActionHandlerClass());
        instructions.addActionListener(new ActionHandlerClass());             
        fileNameLabel = new JLabel(fileNames[0]);
        title = new JLabel("CrossWord Puzzle!!!");

        initComponents();
    } 
    
    /**
    * This is the main method. It just creates the main menu and makes it visible.
    * @param args Not used
    */
    public static void main(String[] args)
    {
      java.awt.EventQueue.invokeLater(new Runnable() 
      {
            public void run() 
            {
                new Main().setVisible(true);
            }
      });
    }
    
    /**
     * This code was generated using the NetBeans IDE. This method creates the layout of the
     * objects on the main screen. I edited this method to make it more "readable".
     */
    private void initComponents()
    {
        title.setFont(new java.awt.Font("Tahoma", 0, 36)); 
        title.setForeground(new java.awt.Color(78, 22, 234));
        
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup
        (
            contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                .addContainerGap(83, Short.MAX_VALUE)
                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(instructions, GroupLayout.PREFERRED_SIZE, 282, GroupLayout.PREFERRED_SIZE)
                    .addGroup(contentPaneLayout.createSequentialGroup()
                        .addComponent(fileBox, GroupLayout.PREFERRED_SIZE, 186, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(start, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE))
                    .addComponent(title))
                .addGap(76, 76, 76))
        );
         contentPaneLayout.setVerticalGroup
        (
            contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(contentPaneLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(title)
                .addGap(18, 18, 18)
                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(fileBox)
                    .addComponent(start, GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE))
                .addGap(35, 35, 35)
                .addComponent(instructions, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(124, Short.MAX_VALUE))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup
        (
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(contentPane, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup
        (
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(contentPane, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }
    
    /**
    * The ItemHandlerClass implements the ItemListener class. Basically, it allows the user
    * to select which text file they want to generate a CrossWord for and displays the name
    * of the text file on a JLabel.
    */    
    private class ItemHandlerClass implements ItemListener
    {
      /**
       * This method was required to implement ItemListender. Basically it just sets the 
       * JLabel fileNameLabel on the JComboBox fileBox to whatever text file the user selected.
       * @param event Creates an event when the JComboBox has been selected or changed from user Input.
       */
      public void itemStateChanged(ItemEvent event)
      {
          if (event.getStateChange()==ItemEvent.SELECTED)
          {
              fileNameLabel.setText(fileNames[fileBox.getSelectedIndex()]);
          }
      }
    }
    
     /**
    * The ActionHandlerClass implements the ActionListener class. Basically, it allows
    * the program to respond to the user clicking one of the buttons on the main menu.
    */ 
    private class ActionHandlerClass implements ActionListener
    {
        
        /**
       * This method was required to implement ActionListender. If the JButton instructions
       * is clicked by the user, this method responds and opens the instructions screen. 
       * If the JButton start is clicked by the user, the main menu disappears and 
       * generates a crossword using the text file selected -- see DisplayCrossword class.
       * @param event Creates an event when one of the JButtons on the main menu has 
       * been clicked by the user.
       */
	public void actionPerformed(ActionEvent event)
	{
	   if (event.getSource() == instructions)
	   {
                new Instructions().setVisible(true);	        
	   }
           if (event.getSource() == start)
           {
               setVisible(false);
               try 
               {
                   new DisplayCrossWord(fileNames[fileBox.getSelectedIndex()]);
               } 
               catch (IOException ex) 
               {
                  JOptionPane.showMessageDialog(null,"Unable to make CrossWord Puzzle.", "Error!!!", JOptionPane.PLAIN_MESSAGE);
               }
               
           }
	}
    }
    
    /**
    * The inner class Instructions extends JFrame. It contains the code required to display the instructions string.
    */
    private class Instructions extends JFrame
    {
      private JLabel instructionText;
      private JPanel panel;
      private JScrollPane jScrollPane1;
      private JTextArea instructionArea;
      private GroupLayout panelLayout;
      private String instructionString;
      {
        try
        {
            instructionString = FileRetriever.getInstructions(); 
            //program tries to get instructions from the file "Instructions.txt". See FileRetriever class.
        }
        catch(IOException ex) 
        {
            instructionString = "No such file called Instructions found. (That is a problem).";
            // if the program fails to do this it initializes the string to show that something went wrong.
        }
      }
        
        /**
        * This constructor initializes all the graphic components that are used to make the instructions screen.
        */
        public Instructions()
        {
          super ("CrossWord Puzzle!!!--- Instructions");
          super .setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
          panel = new JPanel();
          panelLayout = new GroupLayout(panel);
          jScrollPane1 = new javax.swing.JScrollPane();
          instructionArea = new javax.swing.JTextArea();
          
          instructionArea.setColumns(20);
          instructionArea.setRows(5);
          instructionArea.insert(instructionString, 0);
          instructionArea.setEditable(false);
          instructionArea.setBackground(new Color(240,240,240));
          instructionArea.setFont(new Font("TimesNewRoman",Font.PLAIN,18));
          jScrollPane1.setViewportView(instructionArea);
          initInstructions();
        }
        
        /**
        * This code was generated using the NetBeans IDE. This method creates the layout of the
        * objects used to make the instruction screen. I edited this method a little to make it more "readable".
        */
        private void initInstructions()
        {
            GroupLayout panelLayout = new GroupLayout(panel);
            panel.setLayout(panelLayout);
            panelLayout.setHorizontalGroup
            (
                 panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 614, Short.MAX_VALUE)
            );
            panelLayout.setVerticalGroup
            (
                panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
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
 