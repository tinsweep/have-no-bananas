package gui;

import bananas.FoodItem;
import bananas.ListItem;
import bananas.ShoppingList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A GUI class to display an inventory of foods
 *
 * Created by Andy on 6/14/2014.
 */
public class FridgeWindow extends JFrame {

    //Private Variables
    private JFrame fridgeWindow;
    private JPanel mainPanel, listPanel;
    private GridBagConstraints c;
    private JLabel title;
    private JList fridgeList;
    private DefaultListModel listModel;
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem closeMenuItem;
    private JButton addItemButton, removeItemButton;


    public FridgeWindow() {
        //Create the JFrame
        fridgeWindow = new JFrame("Your Fridge");
        fridgeWindow.setSize(300, 400);
        fridgeWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Create the main JPanel
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(GUIColors.getBANANA_COLOR()); //banana yellow
        fridgeWindow.getContentPane().add(mainPanel);
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
                fridgeWindow.setVisible(false);
            }
        });

        fridgeWindow.setJMenuBar(menuBar);

        //Window title
        title = new JLabel("Your Fridge");
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10, 10, 10, 10);
        title.setFont(GUIColors.getHEADING_FONT());
        title.setForeground(GUIColors.getFONT_BROWN());
        mainPanel.add(title, c);

        //JPanel for groceries
        listPanel = new JPanel();
        listPanel.setPreferredSize(new Dimension(150,200));
        listPanel.setMinimumSize(new Dimension(150,200));
        c.gridx = 0;
        c.gridy = 1;
        mainPanel.add(listPanel, c);

        //Groceries List
        listModel = new DefaultListModel();

        fridgeList = new JList(listModel);
        fridgeList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        fridgeList.setLayoutOrientation(JList.VERTICAL);
        JScrollPane listScroller = new JScrollPane(fridgeList);
        listScroller.setPreferredSize(new Dimension(150, 200));
        c.gridx = 0;
        c.gridy = 1;
        listModel.addElement("You have no food!");
        listPanel.add(fridgeList, c);

        //Add item button
        addItemButton = new JButton("Add item");
        c.gridx = 0;
        c.gridy = 2;
        mainPanel.add(addItemButton, c);
        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
            	
                new AddFoodWindow("dummyName");
            }
        });

        //Remove item button
        removeItemButton = new JButton("Remove item");
        c.gridx = 0;
        c.gridy = 3;
        mainPanel.add(removeItemButton, c);
        removeItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                //Remove item from list
            }
        });

        //Show the window
        fridgeWindow.setVisible(true);
    }

    public JList<FoodItem> createJList() {
        listModel = new DefaultListModel();
        JList list = new JList(listModel);
        fridgeList = new JList(listModel);
        fridgeList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        fridgeList.setLayoutOrientation(JList.VERTICAL);
        JScrollPane listScroller = new JScrollPane(fridgeList);
        listScroller.setPreferredSize(new Dimension(250, 80));

        mainPanel.add(list);
        mainPanel.repaint();

        return list;
    }

    public JList<FoodItem> createJList(ShoppingList shoppingList) {
        listModel = new DefaultListModel();
        JList list = new JList(listModel);
        fridgeList = new JList(listModel);
        fridgeList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        fridgeList.setLayoutOrientation(JList.VERTICAL);
        JScrollPane listScroller = new JScrollPane(fridgeList);
        listScroller.setPreferredSize(new Dimension(250, 80));

        for (ListItem item : shoppingList.getList()) {
            listModel.addElement(item);
        }
        mainPanel.add(list);
        mainPanel.repaint();

        return list;
    }

    public JList getGroceriesList() {
        return fridgeList;
    }

    //Add items to the list
    public void addItem(ListItem item) {
        listModel.addElement(item);
        mainPanel.repaint();
    }

    //Remove items from the list
    public void removeItem(ListItem item) {
        listModel.removeElement(item);
    }

    public static void main(String[] args) {
        FridgeWindow test;
        test = new FridgeWindow();
    }
}