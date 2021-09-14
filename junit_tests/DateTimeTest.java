import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.python.Object;
import org.python.exceptions.TypeError;
import org.python.exceptions.ValueError;
import org.python.stdlib.datetime.DateTime;
import org.python.types.Int;
import org.python.types.Str;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    public void testConstructor() {
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
}
