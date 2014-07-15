package gui;

import bananas.ShoppingList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * GUI window for creating new ShoppingList objects
 */
public class CreateListWindow {
    //Private Variables
    private JFrame mainWindow;
    private JPanel mainPanel;
    private GridBagConstraints c;
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem closeMenuItem;
    private JLabel title, nameLabel;
    private JButton addItemButton;
    private JTextField nameField;
    private ShoppingList shoppingList;

    public CreateListWindow() {

        //Create JFrame
        mainWindow = new JFrame("Create a new shopping list");

        mainWindow.setSize(400,300);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
        title = new JLabel("Create a shopping list:");
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10,10,10,10);
        title.setFont(GUIColors.getHEADING_FONT());
        title.setForeground(GUIColors.getFONT_BROWN());
        mainPanel.add(title, c);

        nameLabel = new JLabel("List name:");
        c.gridx = 0;
        c.gridy = 1;
        mainPanel.add(nameLabel, c);

        nameField = new JTextField(20);
        c.gridx = 0;
        c.gridy = 2;
        nameField.setMinimumSize(nameField.getPreferredSize());
        mainPanel.add(nameField, c);

        //Create list button
        addItemButton = new JButton("Create list");
        c.gridx = 0;
        c.gridy = 3;
        mainPanel.add(addItemButton, c);

        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                String name = nameField.getText();
                shoppingList = new ShoppingList(name);
                //add list to dc list
                //refresh list on the DisplayWindow;
                mainWindow.setVisible(false);
            }
        });

        mainWindow.setVisible(true);
    }


    public static void main (String[] args) {
        new CreateListWindow();
    }
}
