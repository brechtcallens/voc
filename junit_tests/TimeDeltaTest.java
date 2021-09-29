import org.junit.Test;
import org.python.stdlib.datetime.TimeDelta;
import org.python.types.Bool;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.python.types.Int.getInt;

public class TimeDeltaTest {
    private final Map<java.lang.String, org.python.Object> empty_kwargs = Collections.emptyMap();

    private TimeDelta createDelta(int... values) {
        return createDelta(empty_kwargs, values);
    }

    private TimeDelta createDelta(Map<java.lang.String, org.python.Object> kwargs, int... values) {
        org.python.Object[] args = new org.python.Object[values.length];

        for (int i = 0; i < values.length; i++) {
            args[i] = getInt(values[i]);
        }

        return new TimeDelta(args, kwargs);
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
        assertEquals(getInt(1), delta.days);
        assertEquals(getInt(0), delta.seconds);
        assertEquals(getInt(0), delta.microseconds);
    }

    @Test
    public void testConstructorTwoArguments() {
        TimeDelta delta = createDelta(1, 2);

        assertEquals(getInt(1), delta.days);
        assertEquals(getInt(2), delta.seconds);
        assertEquals(getInt(0), delta.microseconds);
    }

    @Test
    public void testConstructorThreeArguments() {
        TimeDelta delta = createDelta(1, 2, 3);

        assertEquals(getInt(1), delta.days);
        assertEquals(getInt(2), delta.seconds);
        assertEquals(getInt(3), delta.microseconds);
    }

    @Test
    public void testConstructorBadArguments() {
        org.python.Object[] args = new org.python.Object[]{Bool.FALSE};
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
    public void testConstructorKwargs() {
        Map<java.lang.String, org.python.Object> kwargs = new HashMap<>();
        kwargs.put("days", getInt(1));
        kwargs.put("seconds", getInt(2));
        kwargs.put("microseconds", getInt(3));
        kwargs.put("weeks", getInt(4));
        kwargs.put("hours", getInt(5));
        kwargs.put("minutes", getInt(6));
        kwargs.put("milliseconds", getInt(7));
        TimeDelta delta = createDelta(kwargs);

        assertEquals(getInt(1 + 4 * 7), delta.days);
        assertEquals(getInt(2 + 5 * 3600 + 6 * 60), delta.seconds);
        assertEquals(getInt(3 + 7 * 1000), delta.microseconds);
    }

    @Test
    public void testConstructorMixedKwargs() {
        Map<java.lang.String, org.python.Object> kwargs = new HashMap<>();
        kwargs.put("weeks", getInt(4));
        kwargs.put("hours", getInt(5));
        kwargs.put("minutes", getInt(6));
        kwargs.put("milliseconds", getInt(7));
        TimeDelta delta = createDelta(kwargs, 1, 2, 3);

        assertEquals(getInt(1 + 4 * 7), delta.days);
        assertEquals(getInt(2 + 5 * 3600 + 6 * 60), delta.seconds);
        assertEquals(getInt(3 + 7 * 1000), delta.microseconds);
    }

    @Test
    public void testConstructorBadKwargs() {
        Map<java.lang.String, org.python.Object> kwargs = new HashMap<>();
        kwargs.put("months", getInt(5));

        Exception exception = assertThrows(org.python.exceptions.TypeError.class, () -> {
            TimeDelta delta = createDelta(kwargs);
        });
        assertEquals("months is an invalid keyword argument for this function", exception.getMessage());

    }

    @Test
    public void testConstructorDuplicateKwargs() {
        Map<java.lang.String, org.python.Object> kwargs1 = new HashMap<>();
        kwargs1.put("days", getInt(5));

        Exception exception1 = assertThrows(org.python.exceptions.TypeError.class, () -> {
            TimeDelta delta = createDelta(kwargs1, 1);
        });
        assertEquals("Argument given by name ('days') and position (1)", exception1.getMessage());

        Map<java.lang.String, org.python.Object> kwargs2 = new HashMap<>();
        kwargs2.put("seconds", getInt(5));

        Exception exception2 = assertThrows(org.python.exceptions.TypeError.class, () -> {
            TimeDelta delta = createDelta(kwargs2, 1, 2);
        });
        assertEquals("Argument given by name ('seconds') and position (2)", exception2.getMessage());

        Map<java.lang.String, org.python.Object> kwargs3 = new HashMap<>();
        kwargs3.put("microseconds", getInt(5));

        Exception exception3 = assertThrows(org.python.exceptions.TypeError.class, () -> {
            TimeDelta delta = createDelta(kwargs3, 1, 2, 3);
        });
        assertEquals("Argument given by name ('microseconds') and position (3)", exception3.getMessage());
    }

    @Test
    public void testConstructorOverflow() {
        Exception exception = assertThrows(org.python.exceptions.OverflowError.class, () -> {
            TimeDelta delta = createDelta(999999999, 90000);
        });
        assertEquals("Python int to large to convert to C int", exception.getMessage());

    }

    @Test
    public void testTotal_seconds() {
        TimeDelta delta0 = createDelta(1, 2);
        assertEquals(new org.python.types.Float(86402.0), delta0.total_seconds());

        TimeDelta delta1 = createDelta(1, 2, 3);
        assertEquals(new org.python.types.Float(86402.000003), delta1.total_seconds());

        TimeDelta delta2 = createDelta(1, 2, 30);
        assertEquals(new org.python.types.Float(86402.00003), delta2.total_seconds());

        TimeDelta delta3 = createDelta(1, 2, 300);
        assertEquals(new org.python.types.Float(86402.0003), delta3.total_seconds());

        TimeDelta delta4 = createDelta(1, 2, 3000);
        assertEquals(new org.python.types.Float(86402.003), delta4.total_seconds());

        TimeDelta delta5 = createDelta(1, 2, 30000);
        assertEquals(new org.python.types.Float(86402.03), delta5.total_seconds());

        TimeDelta delta6 = createDelta(1, 2, 300000);
        assertEquals(new org.python.types.Float(86402.3), delta6.total_seconds());
    }

    @Test
    public void testTotal_secondsNegative() {
        TimeDelta delta0 = createDelta(1, 2, -3);
        assertEquals(new org.python.types.Float(86401.999997), delta0.total_seconds());

        TimeDelta delta1 = createDelta(1, -2);
        assertEquals(new org.python.types.Float(86398.0), delta1.total_seconds());

        TimeDelta delta2 = createDelta(-1, 2);
        assertEquals(new org.python.types.Float(-86398.0), delta2.total_seconds());
    }

    @Test
    public void test__add__Positive() {
        TimeDelta delta0 = createDelta(1, 2, 3);
        TimeDelta delta1 = createDelta(4, 5, 6);
        TimeDelta sum = (TimeDelta) delta0.__add__(delta1);

        assertEquals(getInt(5), sum.days);
        assertEquals(getInt(7), sum.seconds);
        assertEquals(getInt(9), sum.microseconds);
    }

    @Test
    public void test__add__Negative() {
        TimeDelta delta0 = createDelta(-1, 2, -3);
        TimeDelta delta1 = createDelta(4, -5, 6);
        TimeDelta sum = (TimeDelta) delta0.__add__(delta1);

        assertEquals(getInt(2), sum.days);
        assertEquals(getInt(86397), sum.seconds);
        assertEquals(getInt(3), sum.microseconds);
    }

    @Test
    public void test__add__Invalid() {
        TimeDelta delta0 = createDelta(1, 2, 3);
        Exception exception = assertThrows(org.python.exceptions.TypeError.class, () -> {
            TimeDelta sum = (TimeDelta) delta0.__add__(getInt(5));
        });
        assertEquals("unsupported operand type(s) for +: 'datetime.timedelta' and 'int'", exception.getMessage());
    }

    @Test
    public void test__sub__Positive() {
        TimeDelta delta0 = createDelta(1, 2, 3);
        TimeDelta delta1 = createDelta(4, 5, 6);
        TimeDelta difference = (TimeDelta) delta1.__sub__(delta0);

        assertEquals(getInt(3), difference.days);
        assertEquals(getInt(3), difference.seconds);
        assertEquals(getInt(3), difference.microseconds);
    }

    @Test
    public void test__sub__Negative() {
        TimeDelta delta0 = createDelta(-1, 2, -3); // -1 1 999997
        TimeDelta delta1 = createDelta(4, -5, 6); // 3 86395 6
        TimeDelta difference = (TimeDelta) delta0.__sub__(delta1);

        assertEquals(getInt(-5), difference.days);
        assertEquals(getInt(6), difference.seconds);
        assertEquals(getInt(999991), difference.microseconds);
    }

    @Test
    public void test__sub__Invalid() {
        TimeDelta delta0 = createDelta(1, 2, 3);
        Exception exception = assertThrows(org.python.exceptions.TypeError.class, () -> {
            TimeDelta sum = (TimeDelta) delta0.__sub__(getInt(5));
        });
        assertEquals("unsupported operand type(s) for -: 'datetime.timedelta' and 'int'", exception.getMessage());
    }

    @Test
    public void test__mul__Int() {
        TimeDelta delta = createDelta(1, 2, 3);
        TimeDelta product = (TimeDelta) delta.__mul__(getInt(5));

        assertEquals(getInt(5), product.days);
        assertEquals(getInt(10), product.seconds);
        assertEquals(getInt(15), product.microseconds);

    }

    @Test
    public void test__mul__Float() {
        TimeDelta delta = createDelta(1, 2, 3);
        TimeDelta product = (TimeDelta) delta.__mul__(new org.python.types.Float(5.8));

        assertEquals(getInt(5), product.days);
        assertEquals(getInt(11), product.seconds);
        assertEquals(getInt(17), product.microseconds);

    }

    @Test
    public void test__mul__IncorrectType() {
        TimeDelta delta = createDelta(1, 2, 3);
        Exception exception = assertThrows(org.python.exceptions.TypeError.class, () -> {
            TimeDelta product = (TimeDelta) delta.__mul__(new org.python.types.Str("Hi"));
        });
        assertEquals("can't multiply sequence by non-int of type 'datetime.timedelta'", exception.getMessage());
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
        TimeDelta delta0 = createDelta(1, 2, 0);
        assertEquals("1 day, 00:00:02", delta0.__str__().toString());

        TimeDelta delta1 = createDelta(1, 20, 3);
        assertEquals("1 day, 00:00:20.000003", delta1.__str__().toString());

        TimeDelta delta2 = createDelta(1, 200, 30);
        assertEquals("1 day, 00:03:20.000030", delta2.__str__().toString());

        TimeDelta delta3 = createDelta(1, 2000, 300);
        assertEquals("1 day, 00:33:20.000300", delta3.__str__().toString());

        TimeDelta delta4 = createDelta(1, 20000, 3000);
        assertEquals("1 day, 05:33:20.003000", delta4.__str__().toString());

        TimeDelta delta5 = createDelta(1, 200000, 30000);
        assertEquals("3 days, 07:33:20.030000", delta5.__str__().toString());

        TimeDelta delta6 = createDelta(1, 2000000, 300000);
        assertEquals("24 days, 03:33:20.300000", delta6.__str__().toString());

        TimeDelta delta7 = createDelta(-1, -2, -3);
        assertEquals("-2 days, 23:59:57.999997", delta7.__str__().toString());
    }

    @Test
    public void test__lt__days(){
        TimeDelta delta1 = createDelta(1, 2, 3);
        TimeDelta delta2 = createDelta(0, 3, 4);

        assertEquals(Bool.FALSE, delta1.__lt__(delta2));
        assertEquals(Bool.TRUE, delta2.__lt__(delta1));
    }

    @Test
    public void test__lt__seconds(){
        TimeDelta delta1 = createDelta(1, 2, 3);
        TimeDelta delta2 = createDelta(1, 1, 4);

        assertEquals(Bool.FALSE, delta1.__lt__(delta2));
        assertEquals(Bool.TRUE, delta2.__lt__(delta1));
    }

    @Test
    public void test__lt__microseconds(){
        TimeDelta delta1 = createDelta(1, 1, 2);
        TimeDelta delta2 = createDelta(1, 1, 1);

        assertEquals(Bool.FALSE, delta1.__lt__(delta2));
        assertEquals(Bool.TRUE, delta2.__lt__(delta1));
    }

    @Test
    public void test__lt__Invalid() {
        TimeDelta delta0 = createDelta(1, 2, 3);
        Exception exception = assertThrows(org.python.exceptions.TypeError.class, () -> {
            TimeDelta sum = (TimeDelta) delta0.__lt__(getInt(5));
        });
        assertEquals("unsupported operand type(s) for <: 'datetime.timedelta' and 'int'", exception.getMessage());
    }

    @Test
    public void test__le__days(){
        TimeDelta delta1 = createDelta(1, 2, 3);
        TimeDelta delta2 = createDelta(0, 3, 4);
        TimeDelta delta3 = createDelta(1, 2, 3);

        assertEquals(Bool.FALSE, delta1.__le__(delta2));
        assertEquals(Bool.TRUE, delta2.__le__(delta1));
        assertEquals(Bool.TRUE, delta1.__le__(delta3));
    }

    @Test
    public void test__le__seconds(){
        TimeDelta delta1 = createDelta(1, 2, 3);
        TimeDelta delta2 = createDelta(1, 1, 4);
        TimeDelta delta3 = createDelta(1, 2, 3);

        assertEquals(Bool.FALSE, delta1.__le__(delta2));
        assertEquals(Bool.TRUE, delta2.__le__(delta1));
        assertEquals(Bool.TRUE, delta1.__le__(delta3));
    }

    @Test
    public void test__le__microseconds(){
        TimeDelta delta1 = createDelta(1, 1, 2);
        TimeDelta delta2 = createDelta(1, 1, 1);
        TimeDelta delta3 = createDelta(1, 1, 2);

        assertEquals(Bool.FALSE, delta1.__le__(delta2));
        assertEquals(Bool.TRUE, delta2.__le__(delta1));
        assertEquals(Bool.TRUE, delta1.__le__(delta3));
    }

    @Test
    public void test__le__Invalid() {
        TimeDelta delta0 = createDelta(1, 2, 3);
        Exception exception = assertThrows(org.python.exceptions.TypeError.class, () -> {
            TimeDelta sum = (TimeDelta) delta0.__le__(getInt(5));
        });
        assertEquals("unsupported operand type(s) for <=: 'datetime.timedelta' and 'int'", exception.getMessage());
    }

    @Test
    public void test__eq__(){
        TimeDelta delta1 = createDelta(1, 2, 3);
        TimeDelta delta2 = createDelta(0, 3, 4);
        TimeDelta delta3 = createDelta(1, 2, 3);

        assertEquals(Bool.FALSE, delta1.__eq__(delta2));
        assertEquals(Bool.TRUE, delta1.__eq__(delta3));
    }

    @Test
    public void test__eq__Invalid() {
        TimeDelta delta0 = createDelta(1, 2, 3);
        Exception exception = assertThrows(org.python.exceptions.TypeError.class, () -> {
            TimeDelta sum = (TimeDelta) delta0.__eq__(getInt(5));
        });
        assertEquals("unsupported operand type(s) for =: 'datetime.timedelta' and 'int'", exception.getMessage());
    }

    @Test
    public void test__ne__(){
        TimeDelta delta1 = createDelta(1, 2, 3);
        TimeDelta delta2 = createDelta(0, 3, 4);
        TimeDelta delta3 = createDelta(1, 2, 3);

        assertEquals(Bool.TRUE, delta1.__ne__(delta2));
        assertEquals(Bool.FALSE, delta1.__ne__(delta3));
    }

    @Test
    public void test__ne__Invalid() {
        TimeDelta delta0 = createDelta(1, 2, 3);
        Exception exception = assertThrows(org.python.exceptions.TypeError.class, () -> {
            TimeDelta sum = (TimeDelta) delta0.__ne__(getInt(5));
        });
        assertEquals("unsupported operand type(s) for !=: 'datetime.timedelta' and 'int'", exception.getMessage());
    }


    @Test
    public void test__ge__days(){
        TimeDelta delta1 = createDelta(1, 2, 3);
        TimeDelta delta2 = createDelta(0, 3, 4);
        TimeDelta delta3 = createDelta(1, 2, 3);

        assertEquals(Bool.TRUE, delta1.__ge__(delta2));
        assertEquals(Bool.FALSE, delta2.__ge__(delta1));
        assertEquals(Bool.TRUE, delta1.__ge__(delta3));
    }

    @Test
    public void test__ge__seconds(){
        TimeDelta delta1 = createDelta(1, 2, 3);
        TimeDelta delta2 = createDelta(1, 1, 4);
        TimeDelta delta3 = createDelta(1, 2, 3);

        assertEquals(Bool.TRUE, delta1.__ge__(delta2));
        assertEquals(Bool.FALSE, delta2.__ge__(delta1));
        assertEquals(Bool.TRUE, delta1.__ge__(delta3));
    }

    @Test
    public void test__ge__microseconds(){
        TimeDelta delta1 = createDelta(1, 2, 3);
        TimeDelta delta2 = createDelta(1, 2, 1);
        TimeDelta delta3 = createDelta(1, 2, 3);

        assertEquals(Bool.TRUE, delta1.__ge__(delta2));
        assertEquals(Bool.FALSE, delta2.__ge__(delta1));
        assertEquals(Bool.TRUE, delta1.__ge__(delta3));
    }

    @Test
    public void test__ge__Invalid() {
        TimeDelta delta0 = createDelta(1, 2, 3);
        Exception exception = assertThrows(org.python.exceptions.TypeError.class, () -> {
            TimeDelta sum = (TimeDelta) delta0.__ge__(getInt(5));
        });
        assertEquals("unsupported operand type(s) for >=: 'datetime.timedelta' and 'int'", exception.getMessage());
    }

    @Test
    public void test__gt__days(){
        TimeDelta delta1 = createDelta(1, 2, 3);
        TimeDelta delta2 = createDelta(0, 3, 4);

        assertEquals(Bool.TRUE, delta1.__gt__(delta2));
        assertEquals(Bool.FALSE, delta2.__gt__(delta1));
    }

    @Test
    public void test__gt__seconds(){
        TimeDelta delta1 = createDelta(1, 2, 3);
        TimeDelta delta2 = createDelta(1, 1, 4);

        assertEquals(Bool.TRUE, delta1.__gt__(delta2));
        assertEquals(Bool.FALSE, delta2.__gt__(delta1));
    }

    @Test
    public void test__gt__microseconds(){
        TimeDelta delta1 = createDelta(1, 1, 2);
        TimeDelta delta2 = createDelta(1, 1, 1);

        assertEquals(Bool.TRUE, delta1.__gt__(delta2));
        assertEquals(Bool.FALSE, delta2.__gt__(delta1));
    }

    @Test
    public void test__gt__Invalid() {
        TimeDelta delta0 = createDelta(1, 2, 3);
        Exception exception = assertThrows(org.python.exceptions.TypeError.class, () -> {
            TimeDelta sum = (TimeDelta) delta0.__gt__(getInt(5));
        });
        assertEquals("unsupported operand type(s) for >: 'datetime.timedelta' and 'int'", exception.getMessage());
    }

    @Test
    public void testBenchmark() {
        TimeDelta delta1 = createDelta(1, 2, 3);
        TimeDelta delta2 = createDelta(1, 2, 3);
        for(int i=0; i<100000; i++) {
            delta1.total_seconds();
            delta1.__add__(delta2);
            delta1.__sub__(delta2);
            delta1.__mul__(getInt(5));
            delta1.__pos__();
            delta1.__str__();
            delta1.__lt__(delta2);
            delta1.__le__(delta2);
            delta1.__eq__(delta2);
            delta1.__ne__(delta2);
            delta1.__ge__(delta2);
            delta1.__gt__(delta2);
        }
    }


}
