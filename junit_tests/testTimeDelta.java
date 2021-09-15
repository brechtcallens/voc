import org.junit.Test;
import org.python.stdlib.datetime.TimeDelta;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class testTimeDelta {
    private final Map<java.lang.String, org.python.Object> empty_kwargs = Collections.emptyMap();

    private TimeDelta createDelta(int... values) {
        org.python.Object[] args = new org.python.Object[values.length];

        for (int i = 0; i < values.length; i++) {
            args[i] = org.python.types.Int.getInt(values[i]);
        }

        return new TimeDelta(args, empty_kwargs);
    }

    private TimeDelta createDelta(float... values) {
        org.python.Object[] args = new org.python.Object[values.length];

        for (int i = 0; i < values.length; i++) {
            args[i] = new org.python.types.Float(values[i]);
        }

        return new TimeDelta(args, empty_kwargs);
    }

    @Test
    public void testConstructorOneArguments() {
        TimeDelta delta = createDelta(1);

        assertEquals(org.python.types.Int.getInt(1), delta.days);
        assertEquals(org.python.types.Int.getInt(0), delta.seconds);
        assertEquals(org.python.types.Int.getInt(0), delta.microseconds);
    }

    @Test
    public void testConstructorTwoArguments() {
        TimeDelta delta = createDelta(1, 2);

        assertEquals(org.python.types.Int.getInt(1), delta.days);
        assertEquals(org.python.types.Int.getInt(2), delta.seconds);
        assertEquals(org.python.types.Int.getInt(0), delta.microseconds);
    }

    @Test
    public void testConstructorThreeArguments() {
        TimeDelta delta = createDelta(1, 2, 3);

        assertEquals(org.python.types.Int.getInt(1), delta.days);
        assertEquals(org.python.types.Int.getInt(2), delta.seconds);
        assertEquals(org.python.types.Int.getInt(3), delta.microseconds);
    }

    @Test
    public void testConstructorBadArguments() {
        org.python.Object[] args = new org.python.Object[]{org.python.types.Bool.getBool(false)};
        Exception exception = assertThrows(org.python.exceptions.TypeError.class, () -> {
            TimeDelta delta = new TimeDelta(args, empty_kwargs);
        });

        assertEquals("unsupported type for timedelta days component: bool", exception.getMessage());
    }

    @Test
    public void testConstructorTooManyArguments() {
        Exception exception = assertThrows(org.python.exceptions.TypeError.class, () -> {
            TimeDelta delta = createDelta(5, 5, 5, 5, 5, 5, 5, 5);
        });

        assertEquals("__new__() takes at most 7 arguments (8 given)", exception.getMessage());
    }

    @Test
    public void testTotalSeconds() {
        TimeDelta delta0 = createDelta(1, 2);
        assertEquals( "86402.0", delta0.totalSeconds().toString());

        TimeDelta delta1 = createDelta(1, 2, 3);
        assertEquals( "86402.000003", delta1.totalSeconds().toString());

        TimeDelta delta2 = createDelta(1, 2, 30);
        assertEquals("86402.000030", delta2.totalSeconds().toString());

        TimeDelta delta3 = createDelta(1, 2, 300);
        assertEquals("86402.000300", delta3.totalSeconds().toString());

        TimeDelta delta4 = createDelta(1, 2, 3000);
        assertEquals("86402.003000", delta4.totalSeconds().toString());

        TimeDelta delta5 = createDelta(1, 2, 30000);
        assertEquals("86402.030000", delta5.totalSeconds().toString());

        TimeDelta delta6 = createDelta(1, 2, 300000);
        assertEquals("86402.300000", delta6.totalSeconds().toString());
    }

    @Test
    public void testTotalSecondsNegative() {
        TimeDelta delta0 = createDelta(1, 2, -3);
        assertEquals( "86401.999997", delta0.totalSeconds().toString()); //fails

        TimeDelta delta1 = createDelta(1, -2);
        assertEquals( "86398.0", delta1.totalSeconds().toString());

        TimeDelta delta2 = createDelta(-1, 2);
        assertEquals( "-86398.0", delta2.totalSeconds().toString());
    }

    @Test
    public void test__add__Positive() {
        TimeDelta delta0 = createDelta(1, 2, 3);
        TimeDelta delta1 = createDelta(4, 5, 6);
        TimeDelta sum = (TimeDelta) delta0.__add__(delta1);

        assertEquals("5", sum.days.toString());
        assertEquals("7", sum.seconds.toString());
        assertEquals("9", sum.microseconds.toString());
    }

    @Test
    public void test__add__Negative() {
        TimeDelta delta0 = createDelta(-1, 2, -3);
        TimeDelta delta1 = createDelta(4, -5, 6);
        TimeDelta sum = (TimeDelta) delta0.__add__(delta1);

        assertEquals("3", sum.days.toString());
        assertEquals("-3", sum.seconds.toString());
        assertEquals("3", sum.microseconds.toString());
    }

    @Test
    public void test__pos__(){
        TimeDelta delta = createDelta(1, 2, 3);
        TimeDelta copy = (TimeDelta) delta.__pos__();

        assertEquals(delta.days, copy.days);
        assertEquals(delta.seconds, copy.seconds);
        assertEquals(delta.microseconds, copy.microseconds);
    }

    @Test
    public void test__str__(){
        TimeDelta delta1 = createDelta(1, 2, 3);
        assertEquals("days: 1, seconds: 2, microseconds: 3", delta1.__str__().toString());

        TimeDelta delta2 = createDelta(-1, -2, -3);
        assertEquals("days: -1, seconds: -2, microseconds: -3", delta2.__str__().toString());
    }
}
