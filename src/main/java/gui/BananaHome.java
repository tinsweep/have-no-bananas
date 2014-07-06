package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Andy on 6/14/2014.
 */
public class BananaHome extends JFrame {

    //Private Variables
    private JFrame mainWindow;
    private JPanel mainPanel;
    private GridBagConstraints c;
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem exitMenuItem;
    private JLabel title;
    private JButton groceriesButton, fridgeButton;

    //Colors
    private Color BANANA_COLOR = new Color(255, 255, 184);
    private Color FONT_BROWN = new Color(61, 41, 30);
    private Font TITLE_FONT = new Font("Cooper Black", Font.BOLD, 36);

    public BananaHome() {

        //Create JFrame
        mainWindow = new JFrame("...Have No Bananas");

        mainWindow.setSize(500,500);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create the main JPanel
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(BANANA_COLOR); //banana yellow
        mainWindow.getContentPane().add(mainPanel);
        c = new GridBagConstraints();

        //Create the top menu
        menuBar = new JMenuBar();
        menu = new JMenu("File");
        menuBar.add(menu);

        //Exit option
        exitMenuItem = new JMenuItem("Exit");
        menu.add(exitMenuItem);
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });

        mainWindow.setJMenuBar(menuBar);

        //Window title
        title = new JLabel("...have no bananas");
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10,10,10,10);
        title.setFont(TITLE_FONT);
        title.setForeground(FONT_BROWN);
        mainPanel.add(title, c);

        //Grocery List button
        groceriesButton = new JButton("Groceries");
        c.gridx = 0;
        c.gridy = 1;
        mainPanel.add(groceriesButton, c);
        groceriesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                new GroceriesWindow();
            }
        });

        //Fridge (Inventory) button
        fridgeButton = new JButton("Your Fridge");
        c.gridx = 0;
        c.gridy = 2;
        mainPanel.add(fridgeButton, c);
        fridgeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                new FridgeWindow();
            }
        });

        SetComponentNames();
        mainWindow.setVisible(true);
    }

    private void SetComponentNames(){
        mainWindow.setName("mainWindow");
        mainPanel.setName("mainPanel");

    }
    public static void main (String[] args) {
        new BananaHome();
    }

}
