import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

public class DateTest {

    private void checkDate(org.python.stdlib.datetime.Date date, long year, long month, long day) {
        assertEquals(year, ((org.python.types.Int) date.year).value);
        assertEquals(month, ((org.python.types.Int) date.month).value);
        assertEquals(day, ((org.python.types.Int) date.day).value);
    }

    @Test
    public void testCTime() {
        org.python.Object[] args = {org.python.types.Int.getInt(2021), org.python.types.Int.getInt(9), org.python.types.Int.getInt(15)};
        org.python.stdlib.datetime.Date testDate = new org.python.stdlib.datetime.Date(args, Collections.emptyMap());

        assertEquals("Wed Sep 15 00:00:00 2021", ((org.python.types.Str) testDate.ctime()).value);
    }

    @Test
    public void testToday() {
        LocalDate todayBefore = null;
        org.python.stdlib.datetime.Date testedToday = null;

        // Make sure that the actual date is the same before and after calling the tested today() method
        // Otherwise we could get two different 'todays' when running this test at midnight
        while (!LocalDate.now().equals(todayBefore)) {
            todayBefore = LocalDate.now();
            testedToday = (org.python.stdlib.datetime.Date) org.python.stdlib.datetime.Date.today();
        }

        assertEquals(todayBefore.getYear(), ((org.python.types.Int) testedToday.year).value);
        assertEquals(todayBefore.getMonthValue(), ((org.python.types.Int) testedToday.month).value);
        assertEquals(todayBefore.getDayOfMonth(), ((org.python.types.Int) testedToday.day).value);
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

    @Test
    public void testFromIsoFormat_Correct() {
        org.python.stdlib.datetime.Date testDate = org.python.stdlib.datetime.Date.fromisoformat(new org.python.types.Str("2019-12-04"));
        checkDate(testDate, 2019, 12, 4);
    }

    @Test
    public void testFromIsoFormat_WrongType() {
        Throwable t = assertThrows(org.python.exceptions.TypeError.class, () -> org.python.stdlib.datetime.Date.fromisoformat(org.python.types.Int.getInt(2019)));
        assertEquals("fromisoformat: argument must be str", t.getMessage());
    }

    @Test
    public void testFromIsoFormat_WrongFormat() {
        Throwable t = assertThrows(org.python.exceptions.ValueError.class, () -> org.python.stdlib.datetime.Date.fromisoformat(new org.python.types.Str("2019/12/04")));
        assertEquals("Invalid isoformat string: '2019/12/04'", t.getMessage());

        Throwable t2 = assertThrows(org.python.exceptions.ValueError.class, () -> org.python.stdlib.datetime.Date.fromisoformat(new org.python.types.Str("2019ab123")));
        assertEquals("Invalid isoformat string: '2019ab123'", t2.getMessage());

        Throwable t3 = assertThrows(org.python.exceptions.ValueError.class, () -> org.python.stdlib.datetime.Date.fromisoformat(new org.python.types.Str("2019-12+30")));
        assertEquals("Invalid isoformat string: '2019-12+30'", t3.getMessage());
    }

    @Test
    public void testIsoFormat() {
        org.python.Object[] args = {org.python.types.Int.getInt(2021), org.python.types.Int.getInt(2), org.python.types.Int.getInt(12)};
        org.python.stdlib.datetime.Date testDate = new org.python.stdlib.datetime.Date(args, Collections.emptyMap());
        assertEquals("2021-02-12", ((org.python.types.Str) testDate.isoformat()).value);
    }

    @Test
    public void testIsoFormat_Padding() {
        org.python.Object[] args = {org.python.types.Int.getInt(1), org.python.types.Int.getInt(1), org.python.types.Int.getInt(1)};
        org.python.stdlib.datetime.Date testDate = new org.python.stdlib.datetime.Date(args, Collections.emptyMap());
        assertEquals("0001-01-01", ((org.python.types.Str) testDate.isoformat()).value);
    }

    @Test
    public void testStr() {
        org.python.Object[] args = {org.python.types.Int.getInt(2021), org.python.types.Int.getInt(2), org.python.types.Int.getInt(12)};
        org.python.stdlib.datetime.Date testDate = new org.python.stdlib.datetime.Date(args, Collections.emptyMap());
        assertEquals("2021-02-12", testDate.__str__().value);
    }

    @Test
    public void testStr_Padding() {
        org.python.Object[] args = {org.python.types.Int.getInt(1), org.python.types.Int.getInt(1), org.python.types.Int.getInt(1)};
        org.python.stdlib.datetime.Date testDate = new org.python.stdlib.datetime.Date(args, Collections.emptyMap());
        assertEquals("0001-01-01", testDate.__str__().value);
    }

    @Test
    public void testRepr() {
        org.python.Object[] args = {org.python.types.Int.getInt(2021), org.python.types.Int.getInt(2), org.python.types.Int.getInt(12)};
        org.python.stdlib.datetime.Date testDate = new org.python.stdlib.datetime.Date(args, Collections.emptyMap());
        assertEquals("datetime.date(2021, 2, 12)", testDate.__repr__().value);
    }

    @TestFactory
    public Collection<DynamicTest> comparisonTestFactory() {
        org.python.Object[] args = {org.python.types.Int.getInt(2020), org.python.types.Int.getInt(8), org.python.types.Int.getInt(14)};
        org.python.stdlib.datetime.Date testDate = new org.python.stdlib.datetime.Date(args, Collections.emptyMap());
        org.python.Object[] args2 = {org.python.types.Int.getInt(2020), org.python.types.Int.getInt(8), org.python.types.Int.getInt(14)};
        org.python.stdlib.datetime.Date sameDate = new org.python.stdlib.datetime.Date(args2, Collections.emptyMap());
        org.python.Object[] args3 = {org.python.types.Int.getInt(2020), org.python.types.Int.getInt(8), org.python.types.Int.getInt(16)};
        org.python.stdlib.datetime.Date greaterDate = new org.python.stdlib.datetime.Date(args3, Collections.emptyMap());
        org.python.Object[] args4 = {org.python.types.Int.getInt(2019), org.python.types.Int.getInt(8), org.python.types.Int.getInt(16)};
        org.python.stdlib.datetime.Date smallerDate = new org.python.stdlib.datetime.Date(args4, Collections.emptyMap());
        org.python.types.Str notADate = new org.python.types.Str("I am not a Date");

        List<Map.Entry<String, Function<org.python.Object, org.python.Object>>> functions = new ArrayList<>();
        functions.add(new AbstractMap.SimpleImmutableEntry<>("Eq", testDate::__eq__));
        functions.add(new AbstractMap.SimpleImmutableEntry<>("Ne", testDate::__ne__));
        functions.add(new AbstractMap.SimpleImmutableEntry<>("Le", testDate::__le__));
        functions.add(new AbstractMap.SimpleImmutableEntry<>("Lt", testDate::__lt__));
        functions.add(new AbstractMap.SimpleImmutableEntry<>("Ge", testDate::__ge__));
        functions.add(new AbstractMap.SimpleImmutableEntry<>("Gt", testDate::__gt__));

        boolean[] resultsSameDate = {true, false, true, false, true, false};
        boolean[] resultsGreaterDate = {false, true, true, true, false, false};
        boolean[] resultsSmallerDate = {false, true, false, false, true, true};

        List<DynamicTest> tests = new ArrayList<>(functions.size());
        for (int i = 0; i < functions.size(); i++) {
            var entry = functions.get(i);
            var funcName = entry.getKey();
            var function = entry.getValue();
            int j = i;

            tests.add(DynamicTest.dynamicTest(
                "test" + funcName + "_Same",
                () -> assertEquals(resultsSameDate[j], ((org.python.types.Bool) function.apply(sameDate)).value)));
            tests.add(DynamicTest.dynamicTest(
                "test" + funcName + "_Greater",
                () -> assertEquals(resultsGreaterDate[j], ((org.python.types.Bool) function.apply(greaterDate)).value)));
            tests.add(DynamicTest.dynamicTest(
                "test" + funcName + "_Smaller",
                () -> assertEquals(resultsSmallerDate[j], ((org.python.types.Bool) function.apply(smallerDate)).value)));
            tests.add(DynamicTest.dynamicTest(
                "test" + funcName + "_Invalid",
                () ->assertEquals(org.python.types.NotImplementedType.NOT_IMPLEMENTED, function.apply(notADate))));
        }
        return tests;
    }


    @Nested
    class DateConstructorTest {
        @Test
        public void testDateConstructor_NoArgs() {
            Throwable t = assertThrows(org.python.exceptions.TypeError.class, () -> new org.python.stdlib.datetime.Date(new org.python.Object[]{}, Collections.emptyMap()));
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
        public void testDateConstructor_SyntaxError() {
            org.python.Object[] args = {org.python.types.Int.getInt(1), org.python.types.Int.getInt(2)};
            HashMap<String, org.python.Object> kwargs = new HashMap<>();
            kwargs.put("year", org.python.types.Int.getInt(9));
            Throwable t = assertThrows(org.python.exceptions.SyntaxError.class, () -> new org.python.stdlib.datetime.Date(args, kwargs));
            assertEquals("positional argument follows keyword argument", t.getMessage());
        }

        @Test
        public void testDateConstructor_SyntaxError_YearKwargWith2Args() {
            org.python.Object[] args = {org.python.types.Int.getInt(1), org.python.types.Int.getInt(2)};
            HashMap<String, org.python.Object> kwargs = new HashMap<>();
            kwargs.put("year", org.python.types.Int.getInt(9));
            Throwable t = assertThrows(org.python.exceptions.SyntaxError.class, () -> new org.python.stdlib.datetime.Date(args, kwargs));
            assertEquals("positional argument follows keyword argument", t.getMessage());
        }

        @Test
        public void testDateConstructor_SyntaxError_MonthKwargWith2Args() {
            org.python.Object[] args = {org.python.types.Int.getInt(1), org.python.types.Int.getInt(2)};
            HashMap<String, org.python.Object> kwargs = new HashMap<>();
            kwargs.put("month", org.python.types.Int.getInt(9));
            Throwable t = assertThrows(org.python.exceptions.SyntaxError.class, () -> new org.python.stdlib.datetime.Date(args, kwargs));
            assertEquals("positional argument follows keyword argument", t.getMessage());
        }

        @Test
        public void testDateConstructor_SyntaxError_YearKwargWith1Arg() {
            org.python.Object[] args = {org.python.types.Int.getInt(1)};
            HashMap<String, org.python.Object> kwargs = new HashMap<>();
            kwargs.put("year", org.python.types.Int.getInt(9));
            Throwable t = assertThrows(org.python.exceptions.SyntaxError.class, () -> new org.python.stdlib.datetime.Date(args, kwargs));
            assertEquals("positional argument follows keyword argument", t.getMessage());
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
            org.python.stdlib.datetime.Date testedDate = new org.python.stdlib.datetime.Date(args, Collections.emptyMap());
            checkDate(testedDate, 2020, 2, 29);
        }

        @Test
        public void testDateConstrutor_InvalidMonth() {
            org.python.Object[] args = {org.python.types.Int.getInt(2021), org.python.types.Int.getInt(22), org.python.types.Int.getInt((29))};
            Throwable t = assertThrows(org.python.exceptions.ValueError.class, () -> new org.python.stdlib.datetime.Date(args, Collections.emptyMap()));
            assertEquals("month must be in 1..12", t.getMessage());
        }

        @Test
        public void testDateConstrutor_InvalidYear() {
            org.python.Object[] args = {org.python.types.Int.getInt(12345), org.python.types.Int.getInt(1), org.python.types.Int.getInt((1))};
            Throwable t = assertThrows(org.python.exceptions.ValueError.class, () -> new org.python.stdlib.datetime.Date(args, Collections.emptyMap()));
            assertEquals("year 12345 is out of range", t.getMessage());
        }

        @Test
        public void testDateConstructor_3ArgsInvalidInput() {
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
        public void testDateConstructor_IrrelevantKeyword() {
            org.python.Object[] args = {org.python.types.Int.getInt(2017), org.python.types.Int.getInt(5)};
            HashMap<String, org.python.Object> kwargs = new HashMap<>();
            kwargs.put("foo", org.python.types.Int.getInt(4));
            Throwable t = assertThrows(org.python.exceptions.TypeError.class, () -> new org.python.stdlib.datetime.Date(args, kwargs));
            assertEquals("function missing required argument 'day' (pos 3)", t.getMessage());
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
        public void testDateConstructor_1Arg() {
            org.python.Object[] args = {org.python.types.Int.getInt(5)};
            Throwable t = assertThrows(org.python.exceptions.TypeError.class, () -> new org.python.stdlib.datetime.Date(args, Collections.emptyMap()));
            assertEquals("function missing required argument 'month' (pos 2)", t.getMessage());
        }

        @Test
        public void testDateConstructor_1ArgInvalid() {
            org.python.Object[] args = {new org.python.types.Str("null")};
            Throwable t = assertThrows(org.python.exceptions.TypeError.class, () -> new org.python.stdlib.datetime.Date(args, Collections.emptyMap()));
            assertEquals("an integer is required (got type str)", t.getMessage());
        }

        @Test
        public void testDateConstructor_1KwargYear() {
            org.python.Object[] args = {};
            HashMap<String, org.python.Object> kwargs = new HashMap<>();
            kwargs.put("year", org.python.types.Int.getInt(2013));
            Throwable t = assertThrows(org.python.exceptions.TypeError.class, () -> new org.python.stdlib.datetime.Date(args, kwargs));
            assertEquals("function missing required argument 'month' (pos 2)", t.getMessage());
        }

        @Test
        public void testDateConstructor_1KwargYearInvalid() {
            org.python.Object[] args = {};
            HashMap<String, org.python.Object> kwargs = new HashMap<>();
            kwargs.put("year", new org.python.types.Str("null"));
            Throwable t = assertThrows(org.python.exceptions.TypeError.class, () -> new org.python.stdlib.datetime.Date(args, kwargs));
            assertEquals("an integer is required (got type str)", t.getMessage());
        }

        @Test
        public void testDateConstructor_1KwargNotYear() {
            org.python.Object[] args = {};
            HashMap<String, org.python.Object> kwargs = new HashMap<>();
            kwargs.put("month", org.python.types.Int.getInt(4));
            Throwable t = assertThrows(org.python.exceptions.TypeError.class, () -> new org.python.stdlib.datetime.Date(args, kwargs));
            assertEquals("function missing required argument 'year' (pos 1)", t.getMessage());
        }

        @Test
        public void testDateConstructor_1KwargNotYearInvalid() {
            org.python.Object[] args = {};
            HashMap<String, org.python.Object> kwargs = new HashMap<>();
            kwargs.put("month", new org.python.types.Str("null"));
            Throwable t = assertThrows(org.python.exceptions.TypeError.class, () -> new org.python.stdlib.datetime.Date(args, kwargs));
            assertEquals("function missing required argument 'year' (pos 1)", t.getMessage());
        }

        @Test
        public void testDateConstructor_2ArgsYearInvalid() {
            org.python.Object[] args = {};
            HashMap<String, org.python.Object> kwargs = new HashMap<>();
            kwargs.put("year", new org.python.types.Str("null"));
            kwargs.put("month", org.python.types.Int.getInt(9));
            Throwable t = assertThrows(org.python.exceptions.TypeError.class, () -> new org.python.stdlib.datetime.Date(args, kwargs));
            assertEquals("an integer is required (got type str)", t.getMessage());
        }

        @Test
        public void testDateConstructor_2ArgsMonthInvalid() {
            org.python.Object[] args = {};
            HashMap<String, org.python.Object> kwargs = new HashMap<>();
            kwargs.put("year", org.python.types.Int.getInt(9));
            kwargs.put("month", new org.python.types.Str("null"));
            Throwable t = assertThrows(org.python.exceptions.TypeError.class, () -> new org.python.stdlib.datetime.Date(args, kwargs));
            assertEquals("an integer is required (got type str)", t.getMessage());
        }

        @Test
        public void testDateConstructor_2ArgsNoYear() {
            org.python.Object[] args = {};
            HashMap<String, org.python.Object> kwargs = new HashMap<>();
            kwargs.put("month", org.python.types.Int.getInt(9));
            kwargs.put("day", org.python.types.Int.getInt(9));
            Throwable t = assertThrows(org.python.exceptions.TypeError.class, () -> new org.python.stdlib.datetime.Date(args, kwargs));
            assertEquals("function missing required argument 'year' (pos 1)", t.getMessage());
        }

        @Test
        public void testDateConstructor_2ArgsNoMonth() {
            org.python.Object[] args = {org.python.types.Int.getInt(2010)};
            HashMap<String, org.python.Object> kwargs = new HashMap<>();
            kwargs.put("day", org.python.types.Int.getInt(9));
            Throwable t = assertThrows(org.python.exceptions.TypeError.class, () -> new org.python.stdlib.datetime.Date(args, kwargs));
            assertEquals("function missing required argument 'month' (pos 2)", t.getMessage());
        }

        @Test
        public void testDateConstructor_2ArgsNoDay() {
            org.python.Object[] args = {org.python.types.Int.getInt(2010)};
            HashMap<String, org.python.Object> kwargs = new HashMap<>();
            kwargs.put("month", org.python.types.Int.getInt(9));
            Throwable t = assertThrows(org.python.exceptions.TypeError.class, () -> new org.python.stdlib.datetime.Date(args, kwargs));
            assertEquals("function missing required argument 'day' (pos 3)", t.getMessage());
        }
    }
}
