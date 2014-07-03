package gui;

import javax.swing.*;

/**
 * Created by Andy on 6/14/2014.
 */
public class FridgeWindow extends JFrame {

    public FridgeWindow () {
        //Create the JFrame
        JFrame groceriesWindow = new JFrame("Fridge Home");
        groceriesWindow.setVisible(true);
        groceriesWindow.setSize(300,400);
        groceriesWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);



    }

    public static void main (String[] args) {
        new FridgeWindow();
    }
}