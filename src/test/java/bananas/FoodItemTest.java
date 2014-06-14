package bananas;

import org.junit.Before;
import org.junit.Test;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

/**
 * Created by Jen on 6/9/2014.
 */
public class FoodItemTest {
    private String testName;

    @Before
    public void setup(){
        testName = "broccoli";
    }

    @Test
    public void testGetName(){
        FoodItem food = new FoodItem(testName);
        assertEquals("Testing food name on creation:", testName,food.getName());
    }
}
