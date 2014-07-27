package gui;

import bananas.FoodItem;
import bananas.ListItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * GUI class to facilitate selecting or creating FoodItems
 */
public class AddFoodWindow extends JFrame {
    //Private Variables
    private JFrame mainWindow;
    private JPanel mainPanel;
    private GridBagConstraints c;
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem closeMenuItem;
    private JLabel title, createItemHeading, nameLabel, categoryLabel;
    private JButton goButton, addItemButton;
    private DefaultListModel listModel;
    private JList<ListItem> savedFoods;
    private JTextField nameField, categoryField;
    private FoodItem food;

    public AddFoodWindow(final String shoppingListName) {

        //Create JFrame
        mainWindow = new JFrame("Add an item");

        mainWindow.setSize(500,400);
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
        title = new JLabel("Add an item");
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10,10,10,10);
        title.setFont(GUIColors.getHEADING_FONT());
        title.setForeground(GUIColors.getFONT_BROWN());
        mainPanel.add(title, c);

        //List of saved foods
        listModel = new DefaultListModel();
        savedFoods = new JList<ListItem>();
        savedFoods.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        savedFoods.setLayoutOrientation(JList.VERTICAL);
        JScrollPane listScroller = new JScrollPane(savedFoods);
        listScroller.setPreferredSize(new Dimension(250, 80));
        c.gridx = 0;
        c.gridy = 2;
        //Import list of saved foods

        mainPanel.add(savedFoods, c);

        //"Go" button (select highlighted list item)
        goButton = new JButton("Go");
        c.gridx = 1;
        c.gridy = 2;
        mainPanel.add(goButton, c);
        goButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                //add selected FoodItem to the dc list;
                //refresh list on the DisplayWindow;
                mainWindow.setVisible(false);
            }
        });

        //Provide the fields to create a new item
        createItemHeading = new JLabel("Create a new item");
        c.gridx = 0;
        c.gridy = 3;
        createItemHeading.setFont(GUIColors.getHEADING_FONT());
        createItemHeading.setForeground(GUIColors.getFONT_BROWN());
        mainPanel.add(createItemHeading, c);

        nameLabel = new JLabel("Item name:");
        c.gridx = 0;
        c.gridy = 4;
        mainPanel.add(nameLabel, c);

        nameField = new JTextField(20);
        c.gridx = 1;
        c.gridy = 4;
        nameField.setMinimumSize(nameField.getPreferredSize());
        mainPanel.add(nameField, c);

        categoryLabel = new JLabel("Category:");
        c.gridx = 0;
        c.gridy = 5;
        mainPanel.add(categoryLabel, c);

        categoryField = new JTextField(20);
        c.gridx = 1;
        c.gridy = 5;
        nameField.setMinimumSize(categoryField.getPreferredSize());
        mainPanel.add(categoryField, c);

        //Add Item button
        addItemButton = new JButton("Add item");
        c.gridx = 1;
        c.gridy = 6;
        mainPanel.add(addItemButton, c);

        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                String name = nameField.getText();
                String category = categoryField.getText();
                FoodItem food = new FoodItem(name, category);
                //add food to dc list
                //refresh list on the DisplayWindow;
                mainWindow.setVisible(false);
            }
        });

        mainWindow.setVisible(true);
    }




}