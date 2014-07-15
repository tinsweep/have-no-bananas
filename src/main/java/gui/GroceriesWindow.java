package gui;

import bananas.FoodItem;
import bananas.ListItem;
import bananas.ShoppingList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A GUI class to display Lists of groceries
 *
 * Created by Andy on 6/14/2014.
 */
public class GroceriesWindow extends JFrame {

    //Private Variables
    private JFrame mainWindow;
    private JPanel mainPanel;
    private GridBagConstraints c;
    private JLabel title;
    private JList groceriesList;
    private DefaultListModel listModel;
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem closeMenuItem;
    private JButton goButton, createListButton;
    private ShoppingList shoppingList;

    public GroceriesWindow () {
        //Create the JFrame
        mainWindow = new JFrame("Groceries Home");
        mainWindow.setSize(300,400);
        mainWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Create the main JPanel
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(GUIColors.getBANANA_COLOR());
        mainWindow.getContentPane().add(mainPanel);
        c = new GridBagConstraints();

        //Create the top menu
        menuBar = new JMenuBar();
        menu = new JMenu("File");
        menuBar.add(menu);

        //Exit option
        closeMenuItem = new JMenuItem("Close");
        menu.add(closeMenuItem);
        closeMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                mainWindow.setVisible(false);
            }
        });

        mainWindow.setJMenuBar(menuBar);

        //Window title
        title = new JLabel("Your Grocery Lists");
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10,10,10,10);
        title.setFont(GUIColors.getHEADING_FONT());
        title.setForeground(GUIColors.getFONT_BROWN());
        mainPanel.add(title, c);
        //Groceries List
        listModel = new DefaultListModel();

        groceriesList = new JList(listModel);
        groceriesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        groceriesList.setLayoutOrientation(JList.VERTICAL);
        JScrollPane listScroller = new JScrollPane(groceriesList);
        listScroller.setPreferredSize(new Dimension(250, 80));
        c.gridx = 0;
        c.gridy = 1;
        mainPanel.add(groceriesList, c);

        //"Go" button (select highlighted list item)
        goButton = new JButton("Go");
        c.gridx = 0;
        c.gridy = 2;
        mainPanel.add(goButton, c);
        goButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                //open selected ShoppingList
                //new DisplayListWindow(shoppingList);
                mainWindow.setVisible(false);
            }
        });

        //create list button
        createListButton = new JButton("Create list");
        c.gridx = 0;
        c.gridy = 3;
        mainPanel.add(createListButton, c);
        createListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                new CreateListWindow();
            }
        });

        //Show the window
        mainWindow.setVisible(true);
    }
}
