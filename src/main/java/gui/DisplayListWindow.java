package gui;

import bananas.DAOUtils;
import bananas.FoodItem;
import bananas.ListItem;
import bananas.ListOfItems;
import bananas.ShoppingList;
import bananas.ShoppingListDAO;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.Map;
import java.util.ArrayList;

/**
 * GUI class to display and modify items in a ShoppingList.
 */
public class DisplayListWindow extends JFrame implements WindowFocusListener {
    //Private Variables
    private JFrame mainWindow;
    private JPanel mainPanel, listPanel;
    private GridBagConstraints c;
    private JLabel title;
    private JList groceriesList;
    private DefaultListModel listModel;
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem closeMenuItem;
    private JButton addItemButton, removeItemButton;
    private ShoppingList shoppingList;
    private String listName;
    private ListOfItems hasItems;
    private ListOfItems noItems;
    private Map<String, ListOfItems> shoppingListItems;
    private ShoppingListDAO dao;
    private DAOUtils daoUtil = new DAOUtils();

    public DisplayListWindow (final String givenListName) {
    	//initialize DAO
    	dao = new ShoppingListDAO(daoUtil);
        //Get the list name
        //Test
    	listName = givenListName.toString();
        shoppingList = new ShoppingList(listName);

        //Create the JFrame
        mainWindow = new JFrame(listName);
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
        title = new JLabel(listName);
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10,10,10,10);
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

        groceriesList = new JList(listModel);
        groceriesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        groceriesList.setLayoutOrientation(JList.VERTICAL);
        JScrollPane listScroller = new JScrollPane(groceriesList);
        listScroller.setPreferredSize(new Dimension(250, 80));
        c.gridx = 0;
        c.gridy = 1;
        listPanel.add(groceriesList, c);

        shoppingListItems = shoppingList.getShoppingListFromDB(listName);
        hasItems = shoppingListItems.get("Pass");
        noItems = shoppingListItems.get("Fail");

        if (hasItems.getList().size() > 0){
            ArrayList <ListItem> showList = hasItems.getList();
            for (int idx = 0; idx < showList.size(); idx++){
                listModel.addElement(showList.get(idx).getName());
            }
        } else {
            listModel.addElement("No items in list.");
        }
        //Add item button
        addItemButton = new JButton("Add item");
        c.gridx = 0;
        c.gridy = 2;
        mainPanel.add(addItemButton, c);
        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                new AddFoodWindow(listName);
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
                String deleteItem = (String) groceriesList.getSelectedValue();
                if (deleteItem != null && hasItems.getList().size() > 0){
                    listModel.removeElement(deleteItem);
                    shoppingList.removeItemByName(deleteItem);
                    dao.deleteItemFromList(deleteItem, shoppingList.getName());
                    Boolean updated = shoppingList.saveShoppingListToDB();

                    if (updated) {
                        JOptionPane.showMessageDialog(null, "Item removed.");
                        listModel.removeElement(listName);
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to save item removal on DB");
                    }
                }

                mainWindow.repaint();

            }
        });

        mainWindow.addWindowFocusListener(this);

        //Show the window
        mainWindow.setVisible(true);
    }

    public JList<FoodItem> createJList() {
        listModel = new DefaultListModel();
        JList list = new JList(listModel);
        groceriesList = new JList(listModel);
        groceriesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        groceriesList.setLayoutOrientation(JList.VERTICAL);
        JScrollPane listScroller = new JScrollPane(groceriesList);
        listScroller.setPreferredSize(new Dimension(150, 200));

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
        listScroller.setPreferredSize(new Dimension(150, 200));

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

    public void windowGainedFocus(WindowEvent e) {
        //Code to get the list of lists
        listModel.removeAllElements();
        shoppingListItems = shoppingList.getShoppingListFromDB(listName);
        hasItems = shoppingListItems.get("Pass");
        noItems = shoppingListItems.get("Fail");
        if (hasItems.getList().size() > 0){
            ArrayList <ListItem> showList = hasItems.getList();
            for (int idx = 0; idx < showList.size(); idx++){
                listModel.addElement(showList.get(idx).getName());
            }
        } else {
            listModel.addElement("No items in list.");
        }
        mainPanel.repaint();
    }

    public void windowLostFocus(WindowEvent e) {}
}
