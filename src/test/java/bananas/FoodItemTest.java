package bananas;

//import main.java.bananas.FoodItem;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

/**
 * Created by Jen on 6/9/2014.
 */
public class FoodItemTest {
    private String testStartName;
    private String testSetName;
    private String testCategory;

    @Before
    public void setup(){
        testStartName = "broccoli";
        testSetName = "asparagus";
        testCategory = "Produce";
    }

    @Test
    public void testGetName(){
        FoodItem food = new FoodItem(testStartName);
        assertEquals("Testing food name on creation:", testStartName,food.getName());
    }

    @Test
    public void testSetName(){
        FoodItem food = new FoodItem(testStartName);
        food.setName(testSetName);
        assertEquals("Testing setName(): ",food.getName(), testSetName);
    }

    @Test
    public void testGetCategory(){
        FoodItem food = new FoodItem(testStartName, testCategory);
        assertEquals("Test getCategory: ", food.getCategory(),testCategory);
    }

    @Test
    public void testSetCategory(){
        FoodItem food = new FoodItem(testStartName, "Dairy");
        food.setCategory(testCategory);
        assertEquals("Test setCategory: ",food.getCategory(), testCategory);
    }
}
