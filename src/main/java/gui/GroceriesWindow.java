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
    private JFrame groceriesWindow;
    private JPanel mainPanel;
    private GridBagConstraints c;
    private JLabel title;
    private JList groceriesList;
    private DefaultListModel listModel;
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem closeMenuItem;


    public GroceriesWindow () {
        //Create the JFrame
        groceriesWindow = new JFrame("Groceries Home");
        groceriesWindow.setSize(300,400);
        groceriesWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Create the main JPanel
        mainPanel = new JPanel(new GridBagLayout());
        groceriesWindow.getContentPane().add(mainPanel);
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
                groceriesWindow.setVisible(false);
            }
        });

        groceriesWindow.setJMenuBar(menuBar);

        //Window title
        title = new JLabel("Your Grocery List");
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10,10,10,10);
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

        //Show the window
        groceriesWindow.setVisible(true);
    }

    public JList<FoodItem> createJList() {
        listModel = new DefaultListModel();
        JList list = new JList(listModel);
        groceriesList = new JList(listModel);
        groceriesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        groceriesList.setLayoutOrientation(JList.VERTICAL);
        JScrollPane listScroller = new JScrollPane(groceriesList);
        listScroller.setPreferredSize(new Dimension(250, 80));

        mainPanel.add(list);
        mainPanel.repaint();

        return list;
    }
    public JList<FoodItem> createJList(ShoppingList shoppingList) {
        listModel = new DefaultListModel();
        JList list = new JList(listModel);
        groceriesList = new JList(listModel);
        groceriesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        groceriesList.setLayoutOrientation(JList.VERTICAL);
        JScrollPane listScroller = new JScrollPane(groceriesList);
        listScroller.setPreferredSize(new Dimension(250, 80));

        for (ListItem item : shoppingList.getList()) {
            listModel.addElement(item);
        }
        mainPanel.add(list);
        mainPanel.repaint();

        return list;
    }

    public JList getGroceriesList() {
        return groceriesList;
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

    public static void main (String[] args) {
        GroceriesWindow test;
        test = new GroceriesWindow();


    }
}
