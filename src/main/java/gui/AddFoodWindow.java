package gui;

import bananas.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * GUI class to facilitate creating FoodItems
 */
public class AddFoodWindow extends JFrame {
    //Private Variables
    private JFrame mainWindow;
    private JPanel mainPanel, createPanel;
    private GridBagConstraints c;
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem closeMenuItem;
    private JLabel title, nameLabel, categoryLabel, quantityLabel;
    private JButton addItemButton;
    private DefaultListModel listModel;
    private JTextField nameField, categoryField, quantityField;
    private FoodItem food;
    private ListItem listItem;

    public AddFoodWindow(final String shoppingListName) {

        //Create JFrame
        mainWindow = new JFrame("Add an item");

        mainWindow.setSize(350,350);
        mainWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Create the main JPanel
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(GUIColors.getBANANA_COLOR()); //banana yellow
        mainWindow.getContentPane().add(mainPanel);
        c = new GridBagConstraints();

        //Create the top menu
        menuBar = new JMenuBar();
        menu = new JMenu("File");
        menuBar.add(menu);

        //Close option
        closeMenuItem = new JMenuItem("Close");
        menu.add(closeMenuItem);
        closeMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                mainWindow.setVisible(false);
                mainWindow.dispose();
            }
        });

        mainWindow.setJMenuBar(menuBar);

        //Window title
        title = new JLabel("Add an item");
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10,10,10,10);

        title.setFont(GUIColors.getHEADING_FONT());
        title.setForeground(GUIColors.getFONT_BROWN());
        mainPanel.add(title, c);

        //JPanel for holding the create item components
        createPanel = new JPanel(new GridBagLayout());
        createPanel.setPreferredSize(new Dimension(300,100));
        createPanel.setBackground(GUIColors.getBANANA_COLOR()); //banana yellow
        c.gridx = 0;
        c.gridy = 1;
        mainPanel.add(createPanel, c);

        nameLabel = new JLabel("Item name:");
        c.gridx = 0;
        c.gridy = 0;
        createPanel.add(nameLabel, c);

        nameField = new JTextField(15);
        c.gridx = 1;
        c.gridy = 0;
        nameField.setMinimumSize(nameField.getPreferredSize());
        createPanel.add(nameField, c);

        categoryLabel = new JLabel("Category:");
        c.gridx = 0;
        c.gridy = 1;
        createPanel.add(categoryLabel, c);

        categoryField = new JTextField(15);
        c.gridx = 1;
        c.gridy = 1;
        categoryField.setMinimumSize(categoryField.getPreferredSize());
        createPanel.add(categoryField, c);

        quantityLabel = new JLabel("Quantity:");
        c.gridx = 0;
        c.gridy = 2;
        createPanel.add(quantityLabel, c);

        quantityField = new JTextField(5);
        c.gridx = 1;
        c.gridy = 2;
        quantityField.setMinimumSize(quantityField.getPreferredSize());
        createPanel.add(quantityField, c);

        //Add Item button
        addItemButton = new JButton("Add item");
        c.gridx = 0;
        c.gridy = 2;
        mainPanel.add(addItemButton, c);

        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                String name = nameField.getText();
                String category = categoryField.getText();
                Double quantity;
                try {
                    quantity = Double.parseDouble(quantityField.getText());
                    if (quantity < 1.0) {
                        quantity = 1.0;
                    }
                }
                catch (NumberFormatException e) {
                    quantity = 1.0;
                }
                food = new FoodItem(name, category);
                listItem = new ListItem.CreateListItem(food).quantity(quantity).create();
                //add to calling list
                DAOUtils daoUtil = new DAOUtils();
                //the add the DAOUtils to the constructor
                ShoppingListDAO shoppingListDAO = new ShoppingListDAO(daoUtil);
                shoppingListDAO.addItemToList(listItem, shoppingListName);

                mainWindow.setVisible(false);
            }
        });

        mainWindow.setVisible(true);
    }


    public static void main (String[] args) {
        new AddFoodWindow();
    }

}