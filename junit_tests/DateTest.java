import org.junit.jupiter.api.Test;
import org.python.Object;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DateTest {

    private void checkDate(org.python.stdlib.datetime.Date date, long year, long month, long day) {
        assertEquals(year, ((org.python.types.Int) date.year).value);
        assertEquals(month, ((org.python.types.Int) date.month).value);
        assertEquals(day, ((org.python.types.Int) date.day).value);
    }

    @Test
    public void testDateConstructor_NoArgs() {
        Throwable t = assertThrows(org.python.exceptions.TypeError.class, () -> new org.python.stdlib.datetime.Date(new Object[]{}, Collections.emptyMap()));
        assertEquals("function missing required argument 'year' (pos 1)", t.getMessage());
    }

    @Test
    public void testDateConstructor_TooManyArgs() {
        org.python.Object[] args = {org.python.types.Int.getInt(1), org.python.types.Int.getInt(2)};
        HashMap<String, org.python.Object> kwargs = new HashMap<>();
        kwargs.put("month", org.python.types.Int.getInt(9));
        kwargs.put("day", org.python.types.Int.getInt(13));
        Throwable t = assertThrows(org.python.exceptions.TypeError.class, () -> new org.python.stdlib.datetime.Date(args, kwargs));
        assertEquals("function takes at most 3 arguments (4 given)", t.getMessage());
    }

    @Test
    public void testDateConstrutor_InvalidDayForMonth() {
        org.python.Object[] args = {org.python.types.Int.getInt(2021), org.python.types.Int.getInt(2), org.python.types.Int.getInt((29))};
        Throwable t = assertThrows(org.python.exceptions.ValueError.class, () -> new org.python.stdlib.datetime.Date(args, Collections.emptyMap()));
        assertEquals("day is out of range for month", t.getMessage());
    }

    @Test
    public void testDateConstrutor_ValidDayForMonth() {
        org.python.Object[] args = {org.python.types.Int.getInt(2020), org.python.types.Int.getInt(2), org.python.types.Int.getInt((29))};
        new org.python.stdlib.datetime.Date(args, Collections.emptyMap());
    }

    @Test
    public void testDateConstructor_invalidInput() {
        org.python.Object[] args = {new org.python.types.Str("invalid"), org.python.types.Int.getInt(2), org.python.types.Int.getInt(3)};
        Throwable t = assertThrows(org.python.exceptions.TypeError.class, () -> new org.python.stdlib.datetime.Date(args, Collections.emptyMap()));
        assertEquals("an integer is required (got type str)", t.getMessage());

        org.python.Object[] args2 = {org.python.types.Int.getInt(2021), new org.python.types.Str("invalid"), org.python.types.Int.getInt(3)};
        Throwable t2 = assertThrows(org.python.exceptions.TypeError.class, () -> new org.python.stdlib.datetime.Date(args2, Collections.emptyMap()));
        assertEquals("an integer is required (got type str)", t2.getMessage());

        org.python.Object[] args3 = {org.python.types.Int.getInt(2021), org.python.types.Int.getInt(3), new org.python.types.Str("invalid"),};
        Throwable t3 = assertThrows(org.python.exceptions.TypeError.class, () -> new org.python.stdlib.datetime.Date(args3, Collections.emptyMap()));
        assertEquals("an integer is required (got type str)", t3.getMessage());
    }

    @Test
    public void testDateConstructor_3Args() {
        org.python.Object[] args = {org.python.types.Int.getInt(2021), org.python.types.Int.getInt(2), org.python.types.Int.getInt(12)};
        org.python.stdlib.datetime.Date testDate = new org.python.stdlib.datetime.Date(args, Collections.emptyMap());
        checkDate(testDate, 2021, 2, 12);
    }

    @Test
    public void testDateConstructor_3Kwargs() {
        org.python.Object[] args = {};
        HashMap<String, org.python.Object> kwargs = new HashMap<>();
        kwargs.put("year", org.python.types.Int.getInt(2013));
        kwargs.put("month", org.python.types.Int.getInt(9));
        kwargs.put("day", org.python.types.Int.getInt(13));
        org.python.stdlib.datetime.Date testDate = new org.python.stdlib.datetime.Date(args, kwargs);
        checkDate(testDate, 2013, 9, 13);
    }

    @Test
    public void testDateConstructor_3MixedArgs() {
        org.python.Object[] args = {org.python.types.Int.getInt(2017)};
        HashMap<String, org.python.Object> kwargs = new HashMap<>();
        kwargs.put("month", org.python.types.Int.getInt(4));
        kwargs.put("day", org.python.types.Int.getInt(1));
        org.python.stdlib.datetime.Date testDate = new org.python.stdlib.datetime.Date(args, kwargs);
        checkDate(testDate, 2017, 4, 1);
    }

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

    @Test
    public void testWeekday() {
        org.python.Object[] args = {org.python.types.Int.getInt(2021), org.python.types.Int.getInt(9), org.python.types.Int.getInt(15)};
        org.python.stdlib.datetime.Date testDate = new org.python.stdlib.datetime.Date(args, Collections.emptyMap());
        assertEquals(2, ((org.python.types.Int) testDate.weekday()).value);

        org.python.Object[] args2 = {org.python.types.Int.getInt(2020), org.python.types.Int.getInt(2), org.python.types.Int.getInt(29)};
        org.python.stdlib.datetime.Date testLeapDay = new org.python.stdlib.datetime.Date(args2, Collections.emptyMap());
        assertEquals(5, ((org.python.types.Int) testLeapDay.weekday()).value);
    }

}
