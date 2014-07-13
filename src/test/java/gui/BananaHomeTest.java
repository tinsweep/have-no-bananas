package gui;

import org.junit.Before;
import org.junit.Test;

import javax.swing.*;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by Andy on 6/30/2014.
 */
public class BananaHomeTest {
    /*private BananaHome bananaHome = null;

    public BananaHomeTest( String name) {
        super(name);
    }

    protected void SetUp() throws Exception {
        super.setUp();

        setHelper(new JFCTestHelper());

        bananaHome = new BananaHome("BananaHomeTest: " + getName());
    }
    private BananaHome bananaHome;

    public void setUp() throws Exception {
        super.setUp();
        bananaHome = new BananaHome();   }
*/


    private BananaHome bananaHome = new BananaHome();

    @Test
    public void createWindow() {
        assertNotNull("Could not find the main window", bananaHome);
    }

}
