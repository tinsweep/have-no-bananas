package bananas;

import org.junit.Before;
import org.junit.Test;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

/**
 * Created by Jen on 6/9/2014.
 */

public class ListItemTest {

    private String testStartName;
    private String testSetName;
    private String testCategory;
    private Double testQuantity;
    private String testUnit;
    private Double testPrice;
    private FoodItem testFoodItem;

    @Before
    public void setup(){
        testStartName = "Milk";
        testCategory = "Dairy";
        testQuantity = 1.0;
        testUnit = "Gallon";
        testPrice = 4.79;
        testFoodItem = new FoodItem(testStartName, testCategory);
   }

    @Test
    public void testMakeListItem(){
        ListItem listItem = new ListItem.CreateListItem(testFoodItem).create();
        assertTrue("Check that listItem created by passing testFoodItem only: ",(listItem instanceof ListItem));
    }

    @Test
    public void testMakeListItemQty(){
        ListItem listItem = new ListItem.CreateListItem(testFoodItem).quantity(testQuantity).create();
        assertTrue("Check that listItem created by passing testFoodItem, quantity only: ",(listItem instanceof ListItem));
    }

    @Test
    public void testMakeListItemQtyUnit(){
        ListItem listItem = new ListItem.CreateListItem(testFoodItem).quantity(testQuantity).unit(testUnit).create();
        assertTrue("Check that listItem created by passing testFoodItem, quantity, unit: ",(listItem instanceof ListItem));
    }

    @Test
    public void testMakeListItemQtyUnitPrice(){
        ListItem listItem = new ListItem.CreateListItem(testFoodItem).quantity(testQuantity).unit(testUnit).price(testPrice).create();
        assertTrue("Check that listItem created by passing testFoodItem, quantity, unit, price: ",(listItem instanceof ListItem));
    }

    @Test
    public  void testGetName(){
        ListItem listItem = new ListItem.CreateListItem(testFoodItem).create();
        assertEquals("Test getName: ", testStartName, listItem.getName());
    }

    /* Is it really a good idea to change the name?
    @Test
    public void testSetName(){
        ListItem listItem = new ListItem(testFoodItem);
        listItem.setName("Ice Cream");
        assertEquals("Test getName: ", "Ice Cream", listItem.getName());
    }
    */

    @Test
    public  void testGetQuantity(){
        ListItem listItem = new ListItem.CreateListItem(testFoodItem).quantity(testQuantity).create();
        assertEquals("Test that listItem qty set: ", testQuantity, listItem.getQuantity());
    }

    @Test
    public void testSetQuantity(){
        ListItem listItem = new ListItem.CreateListItem(testFoodItem).create();
        listItem.setQuantity(testQuantity);
        assertEquals("Test that listItem qty set: ", testQuantity, listItem.getQuantity());
    }

    @Test
    public void  testGetUnit(){
        ListItem listItem = new ListItem.CreateListItem(testFoodItem).unit(testUnit).create();
        assertEquals("Test getUnit", testUnit, listItem.getUnit());
    }

    @Test
    public void testSetUnit(){
        ListItem listItem = new ListItem.CreateListItem(testFoodItem).create();
        listItem.setUnit(testUnit);
        assertEquals("Test setUnit: ", testUnit, listItem.getUnit());
    }
    @Test
    public void  testGetPrice(){
        ListItem listItem = new ListItem.CreateListItem(testFoodItem).price(testPrice).create();
        assertEquals("Test getPrice", testPrice, listItem.getPrice());
    }

    @Test
    public void testSetPrice(){
        ListItem listItem = new ListItem.CreateListItem(testFoodItem).create();
        listItem.setPrice(testPrice);
        assertEquals("Test setPrice: ", testPrice, listItem.getPrice());
    }

    @Test
    public void testGetCategory(){
        ListItem listItem = new ListItem.CreateListItem(testFoodItem).category(testCategory).create();
        assertEquals("Test getCategory: ",testCategory, listItem.getCategory());
    }

    @Test
    public void testSetCategory(){
        ListItem listItem = new ListItem.CreateListItem(testFoodItem).create();
        listItem.setCategory(testCategory);
        assertEquals("Test setCategory: ", testCategory, listItem.getCategory());
    }

}