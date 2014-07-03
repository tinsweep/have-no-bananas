package gui;

import org.junit.Test;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by Andy on 6/30/2014.
 */
public class BananaHomeTest {

    @Test
    public void CreateWindow() {
        BananaHome testWindow = new BananaHome();
        assertNotNull("The window has been created", testWindow);
    }
}
