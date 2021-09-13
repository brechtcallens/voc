import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateTest {

    @Test
    public void testToday() {
        org.python.stdlib.datetime.Date testedToday = (org.python.stdlib.datetime.Date) org.python.stdlib.datetime.Date.today();

        java.time.LocalDateTime realToday = java.time.LocalDateTime.now();

        assertEquals(realToday.getYear(), ((org.python.types.Int) testedToday.year).value);
        assertEquals(realToday.getMonthValue(), ((org.python.types.Int) testedToday.month).value);
        assertEquals(realToday.getDayOfMonth(), ((org.python.types.Int) testedToday.day).value);
    }

}
