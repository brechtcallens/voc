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
    public void test__sub__Positive() {
        TimeDelta delta0 = createDelta(1, 2, 3);
        TimeDelta delta1 = createDelta(4, 5, 6);
        TimeDelta sum = (TimeDelta) delta1.__sub__(delta0);

        assertEquals("3", sum.days.toString());
        assertEquals("3", sum.seconds.toString());
        assertEquals("3", sum.microseconds.toString());
    }

    @Test
    public void test__sub__Negative() {
        TimeDelta delta0 = createDelta(-1, 2, -3);
        TimeDelta delta1 = createDelta(4, -5, 6);
        TimeDelta sum = (TimeDelta) delta0.__sub__(delta1);

        assertEquals("-5", sum.days.toString());
        assertEquals("7", sum.seconds.toString());
        assertEquals("-9", sum.microseconds.toString());
    }

    @Test
    public void test__mul__Int() {
        TimeDelta delta = createDelta(1, 2, 3);
        TimeDelta sum = (TimeDelta) delta.__mul__(org.python.types.Int.getInt(5));

        assertEquals("5", sum.days.toString());
        assertEquals("10", sum.seconds.toString());
        assertEquals("15", sum.microseconds.toString());

    }

    @Test
    public void test__mul__Float() {
        TimeDelta delta = createDelta(1, 2, 3);
        TimeDelta sum = (TimeDelta) delta.__mul__(new org.python.types.Float(5.8));

        assertEquals("5", sum.days.toString());
        assertEquals("11", sum.seconds.toString());
        assertEquals("17", sum.microseconds.toString());

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

    @Test
    public void test__lt__days(){
        TimeDelta delta1 = createDelta(1, 2, 3);
        TimeDelta delta2 = createDelta(0, 3, 4);

        assertEquals(org.python.types.Bool.getBool(false), delta1.__lt__(delta2));
        assertEquals(org.python.types.Bool.getBool(true), delta2.__lt__(delta1));
    }

    @Test
    public void test__lt__seconds(){
        TimeDelta delta1 = createDelta(1, 2, 3);
        TimeDelta delta2 = createDelta(1, 1, 4);

        assertEquals(org.python.types.Bool.getBool(false), delta1.__lt__(delta2));
        assertEquals(org.python.types.Bool.getBool(true), delta2.__lt__(delta1));
    }

    @Test
    public void test__lt__microseconds(){
        TimeDelta delta1 = createDelta(1, 1, 2);
        TimeDelta delta2 = createDelta(1, 1, 1);

        assertEquals(org.python.types.Bool.getBool(false), delta1.__lt__(delta2));
        assertEquals(org.python.types.Bool.getBool(true), delta2.__lt__(delta1));
    }

    @Test
    public void test__le__days(){
        TimeDelta delta1 = createDelta(1, 2, 3);
        TimeDelta delta2 = createDelta(0, 3, 4);
        TimeDelta delta3 = createDelta(1, 2, 3);

        assertEquals(org.python.types.Bool.getBool(false), delta1.__le__(delta2));
        assertEquals(org.python.types.Bool.getBool(true), delta2.__le__(delta1));
        assertEquals(org.python.types.Bool.getBool(true), delta1.__le__(delta3));
    }

    @Test
    public void test__le__seconds(){
        TimeDelta delta1 = createDelta(1, 2, 3);
        TimeDelta delta2 = createDelta(1, 1, 4);
        TimeDelta delta3 = createDelta(1, 2, 3);

        assertEquals(org.python.types.Bool.getBool(false), delta1.__le__(delta2));
        assertEquals(org.python.types.Bool.getBool(true), delta2.__le__(delta1));
        assertEquals(org.python.types.Bool.getBool(true), delta1.__le__(delta3));
    }

    @Test
    public void test__le__microseconds(){
        TimeDelta delta1 = createDelta(1, 1, 2);
        TimeDelta delta2 = createDelta(1, 1, 1);
        TimeDelta delta3 = createDelta(1, 1, 2);

        assertEquals(org.python.types.Bool.getBool(false), delta1.__le__(delta2));
        assertEquals(org.python.types.Bool.getBool(true), delta2.__le__(delta1));
        assertEquals(org.python.types.Bool.getBool(true), delta1.__le__(delta3));
    }

    @Test
    public void test__eq__(){
        TimeDelta delta1 = createDelta(1, 2, 3);
        TimeDelta delta2 = createDelta(0, 3, 4);
        TimeDelta delta3 = createDelta(1, 2, 3);

        assertEquals(org.python.types.Bool.getBool(false), delta1.__eq__(delta2));
        assertEquals(org.python.types.Bool.getBool(true), delta1.__eq__(delta3));
    }

    @Test
    public void test__neq__(){
        TimeDelta delta1 = createDelta(1, 2, 3);
        TimeDelta delta2 = createDelta(0, 3, 4);
        TimeDelta delta3 = createDelta(1, 2, 3);

        assertEquals(org.python.types.Bool.getBool(true), delta1.__neq__(delta2));
        assertEquals(org.python.types.Bool.getBool(false), delta1.__neq__(delta3));
    }


    @Test
    public void test__ge__days(){
        TimeDelta delta1 = createDelta(1, 2, 3);
        TimeDelta delta2 = createDelta(0, 3, 4);
        TimeDelta delta3 = createDelta(1, 2, 3);

        assertEquals(org.python.types.Bool.getBool(true), delta1.__ge__(delta2));
        assertEquals(org.python.types.Bool.getBool(false), delta2.__ge__(delta1));
        assertEquals(org.python.types.Bool.getBool(true), delta1.__ge__(delta3));
    }

    @Test
    public void test__ge__seconds(){
        TimeDelta delta1 = createDelta(1, 2, 3);
        TimeDelta delta2 = createDelta(1, 1, 4);
        TimeDelta delta3 = createDelta(1, 2, 3);

        assertEquals(org.python.types.Bool.getBool(true), delta1.__ge__(delta2));
        assertEquals(org.python.types.Bool.getBool(false), delta2.__ge__(delta1));
        assertEquals(org.python.types.Bool.getBool(true), delta1.__ge__(delta3));
    }

    @Test
    public void test__ge__microseconds(){
        TimeDelta delta1 = createDelta(1, 2, 3);
        TimeDelta delta2 = createDelta(1, 2, 1);
        TimeDelta delta3 = createDelta(1, 2, 3);

        assertEquals(org.python.types.Bool.getBool(true), delta1.__ge__(delta2));
        assertEquals(org.python.types.Bool.getBool(false), delta2.__ge__(delta1));
        assertEquals(org.python.types.Bool.getBool(true), delta1.__ge__(delta3));
    }

    @Test
    public void test__gt__days(){
        TimeDelta delta1 = createDelta(1, 2, 3);
        TimeDelta delta2 = createDelta(0, 3, 4);

        assertEquals(org.python.types.Bool.getBool(true), delta1.__gt__(delta2));
        assertEquals(org.python.types.Bool.getBool(false), delta2.__gt__(delta1));
    }

    @Test
    public void test__gt__seconds(){
        TimeDelta delta1 = createDelta(1, 2, 3);
        TimeDelta delta2 = createDelta(1, 1, 4);

        assertEquals(org.python.types.Bool.getBool(true), delta1.__gt__(delta2));
        assertEquals(org.python.types.Bool.getBool(false), delta2.__gt__(delta1));
    }

    @Test
    public void test__gt__microseconds(){
        TimeDelta delta1 = createDelta(1, 1, 2);
        TimeDelta delta2 = createDelta(1, 1, 1);

        assertEquals(org.python.types.Bool.getBool(true), delta1.__gt__(delta2));
        assertEquals(org.python.types.Bool.getBool(false), delta2.__gt__(delta1));
    }



}
