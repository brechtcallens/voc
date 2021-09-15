import org.junit.jupiter.api.Test;
import org.python.Object;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateTest {


    @Test
    public void testCTime() {
        org.python.Object[] args = {org.python.types.Int.getInt(2021), org.python.types.Int.getInt(9), org.python.types.Int.getInt(15)};
        org.python.stdlib.datetime.Date testDate = new org.python.stdlib.datetime.Date(args, Collections.emptyMap());

        assertEquals("Wed Sep 15 00:00:00 2021", ((org.python.types.Str) testDate.ctime()).value);
    }

    @Test
    public void testToday() {
        org.python.stdlib.datetime.Date testedToday = (org.python.stdlib.datetime.Date) org.python.stdlib.datetime.Date.today();

        java.time.LocalDateTime realToday = java.time.LocalDateTime.now();

        assertEquals(realToday.getYear(), ((org.python.types.Int) testedToday.year).value);
        assertEquals(realToday.getMonthValue(), ((org.python.types.Int) testedToday.month).value);
        assertEquals(realToday.getDayOfMonth(), ((org.python.types.Int) testedToday.day).value);
    }

}
