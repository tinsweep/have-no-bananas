package gui;

import bananas.FoodItem;
import bananas.ListItem;
import bananas.ShoppingList;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;

import static junit.framework.Assert.assertNotNull;

/**
 * This class contains a few tests for the methods contained in the GroceriesWindow GUI class
 *
 * Created by Andy on 7/3/2014.
 */
public class GroceriesWindowTest {

    private GroceriesWindow groceriesWindow = new GroceriesWindow();
    private ShoppingList shoppingList = new ShoppingList("Test");
    private DefaultListModel listModel= new DefaultListModel();
    private JList<FoodItem> testJList;


    private FoodItem banana = new FoodItem("banana", "produce");
    private ListItem listBanana = new ListItem.CreateListItem(banana).create();

    @Before
    public void setup() {
        groceriesWindow.setVisible(true);
        shoppingList.addItem(listBanana);
        testJList = groceriesWindow.createJList(shoppingList);

    }

    @Test
    public void testCreateJList() {
        assertNotNull("Test List could not be found", testJList);
    }

    @Test
    public void testCreateJListEmpty() {
        JList<FoodItem> emptyJList = new JList<FoodItem>();
        assertNotNull("The empty JList could not be found", emptyJList);
    }

    /*
    @Test
    public void testAddItem() {
        JList<FoodItem> testJListAdd = new JList<FoodItem>(listModel);
        listModel.addElement(listBanana);

        testJList = groceriesWindow.createJList();
        groceriesWindow.addItem(listBanana);
        testJList = groceriesWindow.getGroceriesList();

        assertEquals("Testing adding item to JList", testJListAdd, testJList);
    }
    */
}
