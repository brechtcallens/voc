import org.junit.Test;
import org.python.stdlib.datetime.TimeDelta;

import java.sql.Time;
import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class testTimeDelta {
    private final Map<java.lang.String, org.python.Object> empty_kwargs = Collections.emptyMap();

    private org.python.Object[] createArgs(int[] values) {
        org.python.Object[] args = new org.python.Object[values.length];

        for (int i = 0; i < values.length; i++) {
            args[i] = org.python.types.Int.getInt(values[i]);
        }

        return args;
    }


    private org.python.Object[] createArgsFloat(float[] values) {
        org.python.Object[] args = new org.python.Object[values.length];

        for (int i = 0; i < values.length; i++) {
            args[i] = new org.python.types.Float(values[i]);
        }

        return args;
    }

    @Test
    public void testConstructorOneArguments() {
        org.python.Object[] args = createArgs(new int[]{1});
        TimeDelta delta = new TimeDelta(args, empty_kwargs);

        assertEquals(org.python.types.Int.getInt(1), delta.days);
        assertEquals(org.python.types.Int.getInt(0), delta.seconds);
        assertEquals(org.python.types.Int.getInt(0), delta.microseconds);
    }

    @Test
    public void testConstructorTwoArguments() {
        org.python.Object[] args = createArgs(new int[]{1, 2});
        TimeDelta delta = new TimeDelta(args, empty_kwargs);

        assertEquals(org.python.types.Int.getInt(1), delta.days);
        assertEquals(org.python.types.Int.getInt(2), delta.seconds);
        assertEquals(org.python.types.Int.getInt(0), delta.microseconds);
    }

    @Test
    public void testConstructorThreeArguments() {
        org.python.Object[] args = createArgs(new int[]{1, 2, 3});
        TimeDelta delta = new TimeDelta(args, empty_kwargs);

        assertEquals(org.python.types.Int.getInt(1), delta.days);
        assertEquals(org.python.types.Int.getInt(2), delta.seconds);
        assertEquals(org.python.types.Int.getInt(3), delta.microseconds);
    }

    @Test
    public void testConstructorBadArguments() {
        org.python.Object[] args = new org.python.Object[]{org.python.types.Bool.getBool(false)};
        Exception exception = assertThrows(org.python.exceptions.TypeError.class, () -> {   //fails, no error is thrown
            TimeDelta delta = new TimeDelta(args, empty_kwargs);
        });

        //assertEquals("__new__() takes at most 7 arguments (8 given)", exception.getMessage());
    }

    @Test
    public void testConstructorTooManyArguments() {
        org.python.Object[] args = createArgs(new int[]{5, 5, 5, 5, 5, 5, 5, 5});

        Exception exception = assertThrows(org.python.exceptions.TypeError.class, () -> {
            TimeDelta delta = new TimeDelta(args, empty_kwargs);
        });

        assertEquals("__new__() takes at most 7 arguments (8 given)", exception.getMessage());
    }

    @Test
    public void testTotalSeconds() {
        org.python.Object[] args0 = createArgs(new int[]{1, 2});
        TimeDelta delta0 = new TimeDelta(args0, empty_kwargs);
        assertEquals( "86402.0", delta0.totalSeconds().toString());

        org.python.Object[] args1 = createArgs(new int[]{1, 2, 3});
        TimeDelta delta1 = new TimeDelta(args1, empty_kwargs);
        assertEquals( "86402.000003", delta1.totalSeconds().toString());

        org.python.Object[] args2 = createArgs(new int[]{1, 2, 30});
        TimeDelta delta2 = new TimeDelta(args2, empty_kwargs);
        assertEquals("86402.000030", delta2.totalSeconds().toString());

        org.python.Object[] args3 = createArgs(new int[]{1, 2, 300});
        TimeDelta delta3 = new TimeDelta(args3, empty_kwargs);
        assertEquals("86402.000300", delta3.totalSeconds().toString());

        org.python.Object[] args4 = createArgs(new int[]{1, 2, 3000});
        TimeDelta delta4 = new TimeDelta(args4, empty_kwargs);
        assertEquals("86402.003000", delta4.totalSeconds().toString());

        org.python.Object[] args5 = createArgs(new int[]{1, 2, 30000});
        TimeDelta delta5 = new TimeDelta(args5, empty_kwargs);
        assertEquals("86402.030000", delta5.totalSeconds().toString());

        org.python.Object[] args6 = createArgs(new int[]{1, 2, 300000});
        TimeDelta delta6 = new TimeDelta(args6, empty_kwargs);
        assertEquals("86402.300000", delta6.totalSeconds().toString());
    }

    @Test
    public void testTotalSecondsNegative() {
        org.python.Object[] args0 = createArgs(new int[]{1, 2, -3});
        TimeDelta delta0 = new TimeDelta(args0, empty_kwargs);
        assertEquals( "86401.999997", delta0.totalSeconds().toString()); //fails

        org.python.Object[] args1 = createArgs(new int[]{1, -2});
        TimeDelta delta1 = new TimeDelta(args1, empty_kwargs);
        assertEquals( "86398.0", delta1.totalSeconds().toString());

        org.python.Object[] args2 = createArgs(new int[]{-1, 2});
        TimeDelta delta2 = new TimeDelta(args2, empty_kwargs);
        assertEquals( "-86398.0", delta2.totalSeconds().toString());
    }

    @Test
    public void test__add__Positive() {
        org.python.Object[] args0 = createArgs(new int[]{1, 2, 3});
        TimeDelta delta0 = new TimeDelta(args0, empty_kwargs);

        org.python.Object[] args1 = createArgs(new int[]{4, 5, 6});
        TimeDelta delta1 = new TimeDelta(args1, empty_kwargs);

        TimeDelta sum = (TimeDelta) delta0.__add__(delta1);

        assertEquals("5", sum.days.toString());
        assertEquals("7", sum.seconds.toString());
        assertEquals("9", sum.microseconds.toString());
    }

    @Test
    public void test__add__Negative() {
        org.python.Object[] args0 = createArgs(new int[]{-1, 2, -3});
        TimeDelta delta0 = new TimeDelta(args0, empty_kwargs);

        org.python.Object[] args1 = createArgs(new int[]{4, -5, 6});
        TimeDelta delta1 = new TimeDelta(args1, empty_kwargs);

        TimeDelta sum = (TimeDelta) delta0.__add__(delta1);

        assertEquals("3", sum.days.toString());
        assertEquals("-3", sum.seconds.toString());
        assertEquals("3", sum.microseconds.toString());
    }

    @Test
    public void test__pos__(){
        org.python.Object[] args = createArgs(new int[]{1, 2, 3});
        TimeDelta delta = new TimeDelta(args, empty_kwargs);
        TimeDelta copy = (TimeDelta) delta.__pos__();

        assertEquals(delta.days, copy.days);
        assertEquals(delta.seconds, copy.seconds);
        assertEquals(delta.microseconds, copy.microseconds);
    }

    @Test
    public void test__str__(){
        org.python.Object[] args1 = createArgs(new int[]{1, 2, 3});
        TimeDelta delta1 = new TimeDelta(args1, empty_kwargs);
        assertEquals("days: 1, seconds: 2, microseconds: 3", delta1.__str__().toString());

        org.python.Object[] args2 = createArgs(new int[]{-1, -2, -3});
        TimeDelta delta2 = new TimeDelta(args2, empty_kwargs);
        assertEquals("days: -1, seconds: -2, microseconds: -3", delta2.__str__().toString());
    }

}
