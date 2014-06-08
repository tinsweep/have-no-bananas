package bananas;

import org.junit.Before;
import org.junit.Test;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

public class RepeatNumberTest {


    public void testHello(){
        HelloWorld hw = new HelloWorld("Hello", "World!");
        asserFalse("Trvial test just to test build.xml",hw.hello().equalsTo("Hello"));
    }
}