package bananas;

import org.junit.Before;
import org.junit.Test;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

public class RepeatNumberTest {


    public void testGiveFirst(){
        RepeatNumber rn = new RepeatNumber(1,2);
        asserEquals("Trvial test just to test build.xml",rn.giveFirst(),1);
    }
}