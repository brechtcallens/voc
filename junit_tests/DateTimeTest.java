import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.python.Object;
import org.python.exceptions.TypeError;
import org.python.exceptions.ValueError;
import org.python.stdlib.datetime.DateTime;
import org.python.types.Bool;
import org.python.types.Float;
import org.python.types.Int;
import org.python.types.Str;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class DateTimeTest {
    private DateTime dateTime;

    @BeforeEach
    public void initDatetime() {
        dateTime = new DateTime(new org.python.Object[] {
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
        dateTime.today(); // TODO: Mock?
    }

    @Test
    public void testDate() {
        //dateTime.date(); //TODO: Wait for Date to fix.
    }

    @Test
    public void testWeekday() {
        for (int i = 0; i < 7; i++) {
            dateTime = new DateTime(new org.python.Object[] {
                Int.getInt(2021),
                Int.getInt(9),
                Int.getInt(13+i)
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
        dateTime = new DateTime(new org.python.Object[] {
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
    }

    private List<DateTime> getSortedDateTimes() {
        List<DateTime> sortedDateTimes = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            sortedDateTimes.add(
                new DateTime(new Object[] {
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
    public void testTimestamp() {
        // A generic DateTime.
        assertEquals(new Float(1631619791.432523), dateTime.timestamp().__float__());

        // Max DateTime value.
        dateTime = new DateTime(new org.python.Object[] {
            Int.getInt(9999),
            Int.getInt(12),
            Int.getInt(31),
            Int.getInt(22),
            Int.getInt(59),
            Int.getInt(59),
            Int.getInt(999999)
        }, Collections.emptyMap());
        assertEquals(new Float(253402293600.0), dateTime.timestamp().__float__());

        // Min DateTime value.
        dateTime = new DateTime(new org.python.Object[] {
            Int.getInt(1),
            Int.getInt(1),
            Int.getInt(2),
            Int.getInt(0),
            Int.getInt(0),
            Int.getInt(0),
            Int.getInt(0)
        }, Collections.emptyMap());
        assertEquals(new Float(-62135511450.0), dateTime.timestamp().__float__());
    }

    @Test
    public void TimestampOutOfReach() {
        // The minimum DateTime that will go out of range above max.
        String expectedMessage = "year 10000 is out of range";
        dateTime = new DateTime(new org.python.Object[] {
            Int.getInt(9999),
            Int.getInt(12),
            Int.getInt(31),
            Int.getInt(23),
            Int.getInt(0),
            Int.getInt(0),
            Int.getInt(0)
        }, Collections.emptyMap());
        Exception exception = assertThrows(ValueError.class, () -> {
            dateTime.timestamp();
        });
        assertEquals(expectedMessage, exception.getMessage());

        // The maximum DateTime that will go out of range below min.
        expectedMessage = "year 0 is out of range";
        dateTime = new DateTime(new org.python.Object[] {
            Int.getInt(1),
            Int.getInt(1),
            Int.getInt(1),
            Int.getInt(23),
            Int.getInt(59),
            Int.getInt(59),
            Int.getInt(999999)
        }, Collections.emptyMap());
        exception = assertThrows(ValueError.class, () -> {
            dateTime.timestamp();
        });
        assertEquals(expectedMessage, exception.getMessage());
    }
}
