package gui;

import javax.swing.*;
import java.awt.*;

/**
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


    public GroceriesWindow () {
        //Create the JFrame
        groceriesWindow = new JFrame("Groceries Home");
        groceriesWindow.setVisible(true);
        groceriesWindow.setSize(300,400);
        groceriesWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Create the main JPanel
        mainPanel = new JPanel(new GridBagLayout());
        groceriesWindow.getContentPane().add(mainPanel);
        c = new GridBagConstraints();

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
    }

    //Add items to the list
    public void addItem(String item) {
        listModel.addElement(item);
    }

    //Remove items from the list
    public void removeItem(String item) {
        listModel.removeElement(item);
    }

    public static void main (String[] args) {
        GroceriesWindow test;
        test = new GroceriesWindow();
        test.addItem("Bananas");
        test.addItem("Eggs");
        test.addItem("Don't show me");
        test.removeItem("Don't show me");
    }
}
