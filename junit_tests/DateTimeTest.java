import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.python.Object;
import org.python.exceptions.SyntaxError;
import org.python.exceptions.TypeError;
import org.python.exceptions.ValueError;
import org.python.stdlib.datetime.DateTime;
import org.python.types.Bool;
import org.python.types.Float;
import org.python.types.Int;
import org.python.types.Str;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DateTimeTest {
    private DateTime dateTime;

    @BeforeEach
    public void initDatetime() {
        dateTime = new DateTime(new org.python.Object[]{
            Int.getInt(2021),
            Int.getInt(9),
            Int.getInt(14),
            Int.getInt(13),
            Int.getInt(43),
            Int.getInt(11),
            Int.getInt(432523)
        }, Collections.emptyMap());
    }

    @Test
    public void testToday() {
        DateTime.today(); // TODO: Mock?
    }

    @Test
    public void testDate() {
        //dateTime.date(); //TODO: Wait for Date to fix.
    }

    @Test
    public void testWeekday() {
        for (int i = 0; i < 7; i++) {
            dateTime = new DateTime(new org.python.Object[]{
                Int.getInt(2021),
                Int.getInt(9),
                Int.getInt(13 + i)
            }, Collections.emptyMap());

            assertEquals(Int.getInt(i), dateTime.weekday());
        }
    }

    @Test
    public void testStr() {
        assertEquals(new org.python.types.Str("2021-09-14 13:43:11.432523"), dateTime.__str__());
    }

    @Test
    public void testStrLowDateValues() {
        dateTime = new DateTime(new org.python.Object[]{
            Int.getInt(1),
            Int.getInt(1),
            Int.getInt(1),
            Int.getInt(1),
            Int.getInt(1),
            Int.getInt(1),
            Int.getInt(1)
        }, Collections.emptyMap());

        assertEquals(new org.python.types.Str("0001-01-01 01:01:01.000001"), dateTime.__str__());
    }

    @Test
    public void testConstructorInvalidYear() {
        String expectedMessage = "year 0 is out of range";
        org.python.Object[] args = {
            Int.getInt(0),
            Int.getInt(1),
            Int.getInt(1)
        };
        Exception exception = assertThrows(ValueError.class, () -> {
            new DateTime(args, Collections.emptyMap());
        });
        assertEquals(expectedMessage, exception.getMessage());

        expectedMessage = "year 10000 is out of range";
        args[0] = Int.getInt(10000);
        exception = assertThrows(ValueError.class, () -> {
            new DateTime(args, Collections.emptyMap());
        });
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void testConstructorInvalidMonth() {
        String expectedMessage = "month must be in 1..12";
        org.python.Object[] args = {
            Int.getInt(1),
            Int.getInt(0),
            Int.getInt(1)
        };
        Exception exception = assertThrows(ValueError.class, () -> {
            new DateTime(args, Collections.emptyMap());
        });
        assertEquals(expectedMessage, exception.getMessage());

        args[1] = Int.getInt(13);
        exception = assertThrows(ValueError.class, () -> {
            new DateTime(args, Collections.emptyMap());
        });
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void testConstructorInvalidDay() {
        // TODO: Check for February and stuff.
        String expectedMessage = "day is out of range for month";
        org.python.Object[] args = {
            Int.getInt(1),
            Int.getInt(1),
            Int.getInt(0)
        };
        Exception exception = assertThrows(ValueError.class, () -> {
            new DateTime(args, Collections.emptyMap());
        });
        assertEquals(expectedMessage, exception.getMessage());

        args[2] = Int.getInt(32);
        exception = assertThrows(ValueError.class, () -> {
            new DateTime(args, Collections.emptyMap());
        });
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void testConstructorInvalidHour() {
        String expectedMessage = "hour must be in 0..23";
        org.python.Object[] args = {
            Int.getInt(1),
            Int.getInt(1),
            Int.getInt(1),
            Int.getInt(-1)
        };
        Exception exception = assertThrows(ValueError.class, () -> {
            new DateTime(args, Collections.emptyMap());
        });
        assertEquals(expectedMessage, exception.getMessage());

        args[3] = Int.getInt(24);
        exception = assertThrows(ValueError.class, () -> {
            new DateTime(args, Collections.emptyMap());
        });
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void testConstructorInvalidMinute() {
        String expectedMessage = "minute must be in 0..59";
        org.python.Object[] args = {
            Int.getInt(1),
            Int.getInt(1),
            Int.getInt(1),
            Int.getInt(1),
            Int.getInt(-1)
        };
        Exception exception = assertThrows(ValueError.class, () -> {
            new DateTime(args, Collections.emptyMap());
        });
        assertEquals(expectedMessage, exception.getMessage());

        args[4] = Int.getInt(60);
        exception = assertThrows(ValueError.class, () -> {
            new DateTime(args, Collections.emptyMap());
        });
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void testConstructorInvalidSeconds() {
        String expectedMessage = "second must be in 0..59";
        org.python.Object[] args = {
            Int.getInt(1),
            Int.getInt(1),
            Int.getInt(1),
            Int.getInt(1),
            Int.getInt(1),
            Int.getInt(-1)
        };
        Exception exception = assertThrows(ValueError.class, () -> {
            new DateTime(args, Collections.emptyMap());
        });
        assertEquals(expectedMessage, exception.getMessage());

        args[5] = Int.getInt(60);
        exception = assertThrows(ValueError.class, () -> {
            new DateTime(args, Collections.emptyMap());
        });
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void testConstructorInvalidMicroseconds() {
        String expectedMessage = "microsecond must be in 0..999999";
        org.python.Object[] args = {
            Int.getInt(1),
            Int.getInt(1),
            Int.getInt(1),
            Int.getInt(1),
            Int.getInt(1),
            Int.getInt(1),
            Int.getInt(-1)
        };
        Exception exception = assertThrows(ValueError.class, () -> {
            new DateTime(args, Collections.emptyMap());
        });
        assertEquals(expectedMessage, exception.getMessage());

        args[6] = Int.getInt(1000000);
        exception = assertThrows(ValueError.class, () -> {
            new DateTime(args, Collections.emptyMap());
        });
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void testInvalidArgsType() {
        String expectedMessage = "an integer is required (got type str)";
        org.python.Object[] args = {
            Int.getInt(1),
            Int.getInt(1),
            Int.getInt(1),
            new Str("")
        };
        Map<String, Object> kwargs = new HashMap<>();
        Exception exception = assertThrows(TypeError.class, () -> {
            new DateTime(args, kwargs);
        });
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void testConstructorMissingRequiredArguments() {
        String expectedMessage = "function missing required argument 'year' (pos 1)";
        org.python.Object[] args0 = {};
        Exception exception = assertThrows(TypeError.class, () -> {
            new DateTime(args0, Collections.emptyMap());
        });
        assertEquals(expectedMessage, exception.getMessage());

        expectedMessage = "function missing required argument 'month' (pos 2)";
        org.python.Object[] args1 = {
            Int.getInt(1)
        };
        exception = assertThrows(TypeError.class, () -> {
            new DateTime(args1, Collections.emptyMap());
        });
        assertEquals(expectedMessage, exception.getMessage());

        expectedMessage = "function missing required argument 'day' (pos 3)";
        org.python.Object[] args2 = {
            Int.getInt(1),
            Int.getInt(1)
        };
        exception = assertThrows(TypeError.class, () -> {
            new DateTime(args2, Collections.emptyMap());
        });
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void testConstructorUsingKwargs() {
        org.python.Object[] args = {
            Int.getInt(1),
            Int.getInt(1),
            Int.getInt(1)
        };
        Map<String, Object> kwargs = new HashMap<>();
        kwargs.put("microsecond", Int.getInt(1));
        kwargs.put("second", Int.getInt(1));
        kwargs.put("minute", Int.getInt(1));
        kwargs.put("hour", Int.getInt(1));
        new DateTime(args, kwargs);
    }

    @Test
    public void testConstructorInvalidKwargs() {
        String expectedMessage = "'invalid' is an invalid keyword argument for this function";
        org.python.Object[] args = {
            Int.getInt(1),
            Int.getInt(1),
            Int.getInt(1)
        };
        Map<String, Object> kwargs = new HashMap<>();
        kwargs.put("invalid", Int.getInt(1));
        Exception exception = assertThrows(TypeError.class, () -> {
            new DateTime(args, kwargs);
        });
        assertEquals(expectedMessage, exception.getMessage());

        expectedMessage = "an integer is required (got type str)";
        Map<String, Object> kwargs2 = new HashMap<>();
        kwargs2.put("year", new Str(""));
        exception = assertThrows(TypeError.class, () -> {
            new DateTime(args, kwargs2);
        });
        assertEquals(expectedMessage, exception.getMessage());

    }

    private List<DateTime> getSortedDateTimes() {
        List<DateTime> sortedDateTimes = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            sortedDateTimes.add(
                new DateTime(new Object[]{
                    Int.getInt(1 + (i == 6 ? 1 : 0)),
                    Int.getInt(1 + (i == 5 ? 1 : 0)),
                    Int.getInt(1 + (i == 4 ? 1 : 0)),
                    Int.getInt(1 + (i == 3 ? 1 : 0)),
                    Int.getInt(1 + (i == 2 ? 1 : 0)),
                    Int.getInt(1 + (i == 1 ? 1 : 0)),
                    Int.getInt(1 + (i == 0 ? 1 : 0)),
                }, Collections.emptyMap())
            );
        }
        return sortedDateTimes;
    }

    @Test
    public void testLtCombinations() {
        List<DateTime> sortedDateTimes = getSortedDateTimes();
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (i < j) {
                    assertEquals(Bool.getBool(true), sortedDateTimes.get(i).__lt__(sortedDateTimes.get(j)));
                } else {
                    assertEquals(Bool.getBool(false), sortedDateTimes.get(i).__lt__(sortedDateTimes.get(j)));
                }
            }
        }
    }

    @Test
    public void testLtUnsupportedTypes() {
        String expectedMessage = "'<' not supported between instances of 'datetime.datetime' and 'str'";
        Str string = new Str("string");
        Exception exception = assertThrows(TypeError.class, () -> {
            dateTime.__lt__(string);
        });
        assertEquals(expectedMessage, exception.getMessage());

        expectedMessage = "'<' not supported between instances of 'datetime.datetime' and 'int'";
        Int integer = Int.getInt(1);
        exception = assertThrows(TypeError.class, () -> {
            dateTime.__lt__(integer);
        });
        assertEquals(expectedMessage, exception.getMessage());

        expectedMessage = "'<' not supported between instances of 'datetime.datetime' and 'float'";
        Float fl = new Float(1.0);
        exception = assertThrows(TypeError.class, () -> {
            dateTime.__lt__(fl);
        });
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void testLeCombinations() {
        List<DateTime> sortedDateTimes = getSortedDateTimes();
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (i <= j) {
                    assertEquals(Bool.getBool(true), sortedDateTimes.get(i).__le__(sortedDateTimes.get(j)));
                } else {
                    assertEquals(Bool.getBool(false), sortedDateTimes.get(i).__le__(sortedDateTimes.get(j)));
                }
            }
        }
    }

    @Test
    public void testLeUnsupportedTypes() {
        String expectedMessage = "'<=' not supported between instances of 'datetime.datetime' and 'str'";
        Str string = new Str("string");
        Exception exception = assertThrows(TypeError.class, () -> {
            dateTime.__le__(string);
        });
        assertEquals(expectedMessage, exception.getMessage());

        expectedMessage = "'<=' not supported between instances of 'datetime.datetime' and 'int'";
        Int integer = Int.getInt(1);
        exception = assertThrows(TypeError.class, () -> {
            dateTime.__le__(integer);
        });
        assertEquals(expectedMessage, exception.getMessage());

        expectedMessage = "'<=' not supported between instances of 'datetime.datetime' and 'float'";
        Float fl = new Float(1.0);
        exception = assertThrows(TypeError.class, () -> {
            dateTime.__le__(fl);
        });
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void testEqCombinations() {
        List<DateTime> sortedDateTimes = getSortedDateTimes();
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (i == j) {
                    assertEquals(Bool.getBool(true), sortedDateTimes.get(i).__eq__(sortedDateTimes.get(j)));
                } else {
                    assertEquals(Bool.getBool(false), sortedDateTimes.get(i).__eq__(sortedDateTimes.get(j)));
                }
            }
        }
    }

    @Test
    public void testEqUnsupportedTypes() {
        Str string = new Str("string");
        assertEquals(Bool.getBool(false), dateTime.__eq__(string));

        Int integer = Int.getInt(1);
        assertEquals(Bool.getBool(false), dateTime.__eq__(string));

        Float fl = new Float(1.0);
        assertEquals(Bool.getBool(false), dateTime.__eq__(string));
    }

    @Test
    public void testGtCombinations() {
        List<DateTime> sortedDateTimes = getSortedDateTimes();
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (i > j) {
                    assertEquals(Bool.getBool(true), sortedDateTimes.get(i).__gt__(sortedDateTimes.get(j)));
                } else {
                    assertEquals(Bool.getBool(false), sortedDateTimes.get(i).__gt__(sortedDateTimes.get(j)));
                }
            }
        }
    }

    @Test
    public void testGtUnsupportedTypes() {
        String expectedMessage = "'>' not supported between instances of 'datetime.datetime' and 'str'";
        Str string = new Str("string");
        Exception exception = assertThrows(TypeError.class, () -> {
            dateTime.__gt__(string);
        });
        assertEquals(expectedMessage, exception.getMessage());

        expectedMessage = "'>' not supported between instances of 'datetime.datetime' and 'int'";
        Int integer = Int.getInt(1);
        exception = assertThrows(TypeError.class, () -> {
            dateTime.__gt__(integer);
        });
        assertEquals(expectedMessage, exception.getMessage());

        expectedMessage = "'>' not supported between instances of 'datetime.datetime' and 'float'";
        Float fl = new Float(1.0);
        exception = assertThrows(TypeError.class, () -> {
            dateTime.__gt__(fl);
        });
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void testGeCombinations() {
        List<DateTime> sortedDateTimes = getSortedDateTimes();
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (i >= j) {
                    assertEquals(Bool.getBool(true), sortedDateTimes.get(i).__ge__(sortedDateTimes.get(j)));
                } else {
                    assertEquals(Bool.getBool(false), sortedDateTimes.get(i).__ge__(sortedDateTimes.get(j)));
                }
            }
        }
    }

    @Test
    public void testGeUnsupportedTypes() {
        String expectedMessage = "'>=' not supported between instances of 'datetime.datetime' and 'str'";
        Str string = new Str("string");
        Exception exception = assertThrows(TypeError.class, () -> {
            dateTime.__ge__(string);
        });
        assertEquals(expectedMessage, exception.getMessage());

        expectedMessage = "'>=' not supported between instances of 'datetime.datetime' and 'int'";
        Int integer = Int.getInt(1);
        exception = assertThrows(TypeError.class, () -> {
            dateTime.__ge__(integer);
        });
        assertEquals(expectedMessage, exception.getMessage());

        expectedMessage = "'>=' not supported between instances of 'datetime.datetime' and 'float'";
        Float fl = new Float(1.0);
        exception = assertThrows(TypeError.class, () -> {
            dateTime.__ge__(fl);
        });
        assertEquals(expectedMessage, exception.getMessage());
    }


    @Test
    public void testReplaceArs() {
        org.python.Object[] args = new org.python.Object[]{
            Int.getInt(1992),
            Int.getInt(12),
            Int.getInt(23)
        };
        DateTime dt = new DateTime(args, Collections.emptyMap());
        args[0] = Int.getInt(1991);
        args[1] = Int.getInt(1);
        DateTime dt2 = new DateTime(args, Collections.emptyMap());
        assertEquals(Bool.getBool(false), dt.__eq__(dt2));

        args[0] = Int.getInt(1992);
        args[1] = Int.getInt(12);
        dt2 = dt2.replace(args, Collections.emptyMap());
        assertEquals(Bool.getBool(true), dt.__eq__(dt2));

        org.python.Object[] args2 = new org.python.Object[]{
            Int.getInt(1992),
            Int.getInt(12),
            Int.getInt(23),
            Int.getInt(1),
            Int.getInt(1),
            Int.getInt(1),
            Int.getInt(1)
        };

        dt2 = dt2.replace(args2, Collections.emptyMap());
        assertEquals(Bool.getBool(false), dt.__eq__(dt2));


        for (int i = 3; i < 7; i++) {
            args2[i] = Int.getInt(0);
        }
        dt2 = dt2.replace(args2, Collections.emptyMap());
        assertEquals(Bool.getBool(true), dt.__eq__(dt2));
    }

    // Test replacing all values using kwargs
    // Swap existing attributes
    // Swap new attributes
    @Test
    public void testReplaceKwargs() {
        org.python.Object[] emptyArgs = new org.python.Object[]{};
        Map<String, Object> kwargs = new HashMap<>();
        kwargs.put("year", Int.getInt(1992));
        kwargs.put("month", Int.getInt(12));
        kwargs.put("day", Int.getInt(23));

        DateTime dt = new DateTime(new org.python.Object[]{}, kwargs);
        kwargs.put("year", Int.getInt(1991));
        DateTime dt2 = new DateTime(new org.python.Object[]{}, kwargs);
        assertEquals(Bool.getBool(false), dt.__eq__(dt2));

        kwargs.put("year", Int.getInt(1992));
        dt2 = dt2.replace(emptyArgs, kwargs);
        assertEquals(Bool.getBool(true), dt.__eq__(dt2));

        kwargs.put("hour", Int.getInt(11));
        kwargs.put("microsecond", Int.getInt(1));
        kwargs.put("second", Int.getInt(1));
        kwargs.put("minute", Int.getInt(1));
        dt2 = dt2.replace(emptyArgs, kwargs);
        assertEquals(Bool.getBool(false), dt.__eq__(dt2));

        kwargs.put("hour", Int.getInt(0));
        kwargs.put("microsecond", Int.getInt(0));
        kwargs.put("second", Int.getInt(0));
        kwargs.put("minute", Int.getInt(0));
        dt2 = dt2.replace(emptyArgs, kwargs);
        assertEquals(Bool.getBool(true), dt.__eq__(dt2));
    }

    // Test replacing all values using kwargs and args
    // Test empty replace
    @Test
    public void testReplaceKwargsAndArgs() {
        org.python.Object[] args = new org.python.Object[]{
            Int.getInt(1992),
            Int.getInt(12),
            Int.getInt(23)
        };
        Map<String, Object> kwargs = new HashMap<>();
        kwargs.put("hour", Int.getInt(0));
        kwargs.put("microsecond", Int.getInt(0));
        kwargs.put("second", Int.getInt(0));
        kwargs.put("minute", Int.getInt(0));


        DateTime dt = new DateTime(args, kwargs);
        DateTime dt2 = dt.replace(new org.python.Object[]{}, Collections.emptyMap());
        assertEquals(Bool.getBool(true), dt.__eq__(dt2));
        dt2 = dt.replace(args, kwargs);
        assertEquals(Bool.getBool(true), dt.__eq__(dt2));

        kwargs.put("hour", Int.getInt(4));
        kwargs.put("microsecond", Int.getInt(5));
        kwargs.put("second", Int.getInt(6));
        kwargs.put("minute", Int.getInt(7));
        args[0] = Int.getInt(1);
        args[1] = Int.getInt(2);
        args[2] = Int.getInt(3);
        dt2 = dt.replace(args, kwargs);
        assertEquals(Bool.getBool(false), dt.__eq__(dt2));

        dt = dt.replace(args, kwargs);
        assertEquals(Bool.getBool(true), dt.__eq__(dt2));

        kwargs = new HashMap<>();
        dt2 = new DateTime(args, kwargs);
        kwargs.put("microsecond", Int.getInt(5));
        dt = new DateTime(args, kwargs);
        assertEquals(Bool.getBool(false), dt.__eq__(dt2));
        dt2 = dt2.replace(args, kwargs);
        assertEquals(Bool.getBool(true), dt.__eq__(dt2));


    }

    // Invalid kwargs
    @Test
    public void testReplaceInvalidKwargs() {
        Map<String, Object> kwargs = new HashMap<>();
        kwargs.put("year", Int.getInt(1));
        org.python.Object[] args = new org.python.Object[]{
            Int.getInt(1)
        };
        Exception exception = assertThrows(SyntaxError.class, () -> {
            dateTime.replace(args, kwargs);
        });
        assertEquals("positional argument follows keyword argument", exception.getMessage());

        kwargs.put("hr", Int.getInt(0));
        exception = assertThrows(TypeError.class, () -> {
            dateTime.replace(new org.python.Object[]{}, kwargs);
        });
        assertEquals("'hr' is an invalid keyword argument for replace()", exception.getMessage());
    }

    @Test
    public void testFromIsoFormatCorrectFormat() {
        org.python.Object[] args = new org.python.Object[]{
            Int.getInt(1992),
            Int.getInt(12),
            Int.getInt(23),
            Int.getInt(0),
            Int.getInt(0),
            Int.getInt(0),
            Int.getInt(0)};
        dateTime = new DateTime(args, Collections.emptyMap());
        assertEquals(dateTime, DateTime.fromisoformat(new Str("1992-12-23")));

        args[3] = Int.getInt(1);
        dateTime = new DateTime(args, Collections.emptyMap());
        assertEquals(dateTime, DateTime.fromisoformat(new Str("1992-12-23 01")));

        args[4] = Int.getInt(1);
        dateTime = new DateTime(args, Collections.emptyMap());
        assertEquals(dateTime, DateTime.fromisoformat(new Str("1992-12-23 01:01")));

        args[5] = Int.getInt(1);
        dateTime = new DateTime(args, Collections.emptyMap());
        assertEquals(dateTime, DateTime.fromisoformat(new Str("1992-12-23 01:01:01")));

        args[6] = Int.getInt(1);
        dateTime = new DateTime(args, Collections.emptyMap());
        assertEquals(dateTime, DateTime.fromisoformat(new Str("1992-12-23 01:01:01.000001")));
    }

    @Test
    public void testFromisoformatInvalids() {
        Exception exception = assertThrows(TypeError.class, () -> {
            DateTime.fromisoformat(Int.getInt(1));
        });
        assertEquals("fromisoformat: argument must be str", exception.getMessage());

        Str datetimeString = new Str("");
        exception = assertThrows(ValueError.class, () -> {
            DateTime.fromisoformat(datetimeString);
        });
        assertEquals("Invalid isoformat string: '" + datetimeString + "'", exception.getMessage());

        Str datetimeString2 = new Str("27272727twentyseven27272727");
        exception = assertThrows(ValueError.class, () -> {
            DateTime.fromisoformat(datetimeString2);
        });
        assertEquals("Invalid isoformat string: '" + datetimeString2 + "'", exception.getMessage());

        Str datetimeString3 = new Str("1992:11:25:11-11-11-000011");
        exception = assertThrows(ValueError.class, () -> {
            DateTime.fromisoformat(datetimeString3);
        });
        assertEquals("Invalid isoformat string: '" + datetimeString3 + "'", exception.getMessage());
    }

}
