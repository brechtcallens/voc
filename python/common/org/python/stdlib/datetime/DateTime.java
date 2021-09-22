package org.python.stdlib.datetime;

import org.python.types.Bool;
import org.python.types.Int;
import org.python.types.Str;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DateTime extends org.python.types.Object {
    private final int YEAR_INDEX = 0;
    private final int MONTH_INDEX = 1;
    private final int DAY_INDEX = 2;
    private final int HOUR_INDEX = 3;
    private final int MINUTE_INDEX = 4;
    private final int SECOND_INDEX = 5;
    private final int MICROSECOND_INDEX = 6;

    private final int MIN_YEAR = 1;
    private final int MAX_YEAR = 9999;

    private final Long[] timeUnits = {0l, 0l, 0l, 0l, 0l, 0l, 0l};

    @org.python.Attribute
    public final org.python.Object year;

    @org.python.Attribute
    public final org.python.Object month;

    @org.python.Attribute
    public final org.python.Object day;

    @org.python.Attribute
    public final org.python.Object hour;

    @org.python.Attribute
    public final org.python.Object minute;

    @org.python.Attribute
    public final org.python.Object second;

    @org.python.Attribute
    public final org.python.Object microsecond;

    @org.python.Attribute
    public static final org.python.Object min = __min__();

    @org.python.Attribute
    public static final org.python.Object max = __max__();

    @org.python.Method(__doc__ = "The year, month and day arguments are required. tzinfo may be None, or an instance " +
        "of a tzinfo subclass. The remaining arguments must be integers in the following ranges:\n" +
        "* MINYEAR <= year <= MAXYEAR,\n" +
        "* 1 <= month <= 12,\n" +
        "* 1 <= day <= number of days in the given month and year,\n" +
        "* 0 <= hour < 24,\n" +
        "* 0 <= minute < 60,\n" +
        "* 0 <= second < 60,\n" +
        "* 0 <= microsecond < 1000000,\n" +
        "* fold in [0, 1].\n" +
        "If an argument outside those ranges is given, ValueError is raised.",
        args = {"args", "kwargs"})
    public DateTime(org.python.Object[] args, java.util.Map<java.lang.String, org.python.Object> kwargs) {
        super();
        checkContentsTypeMap(kwargs, Int.getInt(0));
        checkContentsTypeArray(args, args.length, Int.getInt(0));
        String[] keys = {"year", "month", "day", "hour", "minute", "second", "microsecond"};
        boolean kwargsIsUsed = false;
        int keyIndex = 0;
        int argIndex = 0;

        for (String key : keys) {
            if (kwargs.get(key) != null) {
                this.timeUnits[keyIndex] = ((org.python.types.Int) kwargs.get(key)).value;
                kwargsIsUsed = true;
            } else if (args.length > argIndex) {
                if (kwargsIsUsed)
                    throw new org.python.exceptions.SyntaxError("positional argument follows keyword argument");
                this.timeUnits[keyIndex] = ((org.python.types.Int) args[argIndex]).value;
                argIndex++;
            } else if (keyIndex < 3) {
                throw new org.python.exceptions.TypeError("function missing required argument '" + keys[keyIndex] + "' (pos " + (keyIndex + 1) + ")");
            }
            keyIndex++;
        }

        for (Map.Entry<java.lang.String, org.python.Object> entry : kwargs.entrySet()) {
            if (!Arrays.asList(keys).contains(entry.getKey())) {
                throw new org.python.exceptions.TypeError("'" + entry.getKey() + "' is an invalid keyword argument for this function");
            }
        }

        if (this.timeUnits[YEAR_INDEX] < MIN_YEAR || this.timeUnits[YEAR_INDEX] > MAX_YEAR) {
            throw new org.python.exceptions.ValueError("year " + this.timeUnits[YEAR_INDEX] + " is out of range");
        }

        if (this.timeUnits[MONTH_INDEX] < 1 || this.timeUnits[MONTH_INDEX] > 12) {
            throw new org.python.exceptions.ValueError("month must be in 1..12");
        }
        if (this.timeUnits[DAY_INDEX] < 1 || this.timeUnits[DAY_INDEX] > 31) {
            throw new org.python.exceptions.ValueError("day is out of range for month");
        }

        if (this.timeUnits[HOUR_INDEX] < 0 || this.timeUnits[HOUR_INDEX] > 23) {
            throw new org.python.exceptions.ValueError("hour must be in 0..23");
        }

        if (this.timeUnits[MINUTE_INDEX] < 0 || this.timeUnits[MINUTE_INDEX] > 59) {
            throw new org.python.exceptions.ValueError("minute must be in 0..59");
        }

        if (this.timeUnits[SECOND_INDEX] < 0 || this.timeUnits[SECOND_INDEX] > 59) {
            throw new org.python.exceptions.ValueError("second must be in 0..59");
        }

        if (this.timeUnits[MICROSECOND_INDEX] < 0 || this.timeUnits[MICROSECOND_INDEX] > 999999) {
            throw new org.python.exceptions.ValueError("microsecond must be in 0..999999");
        }

        this.year = __year__();
        this.month = __month__();
        this.day = __day__();
        this.hour = __hour__();
        this.minute = __minute__();
        this.second = __second__();
        this.microsecond = __microsecond__();
    }

    public org.python.types.Str __str__() {
        String year = Long.toString(this.timeUnits[YEAR_INDEX]);
        while (year.length() < 4)
            year = "0" + year;

        String month = Long.toString(this.timeUnits[MONTH_INDEX]);
        while (month.length() < 2)
            month = "0" + month;

        String day = Long.toString(this.timeUnits[DAY_INDEX]);
        while (day.length() < 2)
            day = "0" + day;

        String hour = this.timeUnits[HOUR_INDEX] != 0 ? Long.toString(this.timeUnits[HOUR_INDEX]) : "00";
        while (hour.length() < 2)
            hour = "0" + hour;

        String minute = this.timeUnits[MINUTE_INDEX] != 0 ? Long.toString(this.timeUnits[MINUTE_INDEX]) : "00";
        while (minute.length() < 2)
            minute = "0" + minute;

        String second = this.timeUnits[SECOND_INDEX] != 0 ? Long.toString(this.timeUnits[SECOND_INDEX]) : "00";
        while (second.length() < 2)
            second = "0" + second;

        String microsecond = this.timeUnits[MICROSECOND_INDEX] != 0 ? Long.toString(this.timeUnits[MICROSECOND_INDEX]) : "";
        while (microsecond.length() < 6 && microsecond.length() != 0)
            microsecond = "0" + microsecond;

        String returnStr = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;

        returnStr += microsecond.length() > 0 ? "." + microsecond : "";
        return new org.python.types.Str(returnStr);
    }

    @org.python.Method(__doc__ = "Return date object with same year, month and day.")
    public org.python.Object date() {
        org.python.Object[] args = {org.python.types.Int.getInt(this.timeUnits[YEAR_INDEX]), org.python.types.Int.getInt(this.timeUnits[MONTH_INDEX]),
            org.python.types.Int.getInt(this.timeUnits[DAY_INDEX])};
        return new Date(args, Collections.emptyMap());
    }

    @org.python.Method(__doc__ = "Return the current local datetime, with tzinfo None.")
    public static org.python.Object today() {
        java.time.LocalDateTime today = java.time.LocalDateTime.now();
        org.python.Object[] args = {org.python.types.Int.getInt(today.getYear()), org.python.types.Int.getInt(today.getMonth().getValue()),
            org.python.types.Int.getInt(today.getDayOfMonth()), org.python.types.Int.getInt(today.getHour()), org.python.types.Int.getInt(today.getMinute()),
            org.python.types.Int.getInt(today.getSecond()), org.python.types.Int.getInt(today.getNano() / 1000)};
        return new DateTime(args, Collections.emptyMap());
    }

    @org.python.Method(__doc__ = "returns year")
    public org.python.types.Str __year__() {
        return new org.python.types.Str(this.timeUnits[YEAR_INDEX] + "");
    }

    @org.python.Method(__doc__ = "returns month")
    public org.python.types.Str __month__() {
        return new org.python.types.Str(this.timeUnits[MONTH_INDEX] + "");
    }

    @org.python.Method(__doc__ = "returns day")
    public org.python.types.Str __day__() {
        return new org.python.types.Str(this.timeUnits[DAY_INDEX] + "");
    }

    @org.python.Method(__doc__ = "returns hour")
    public org.python.types.Str __hour__() {
        return new org.python.types.Str(this.timeUnits[HOUR_INDEX] + "");
    }

    @org.python.Method(__doc__ = "returns minute")
    public org.python.types.Str __minute__() {
        return new org.python.types.Str(this.timeUnits[MINUTE_INDEX] + "");
    }

    @org.python.Method(__doc__ = "returns second")
    public org.python.types.Str __second__() {
        return new org.python.types.Str(this.timeUnits[SECOND_INDEX] + "");
    }

    @org.python.Method(__doc__ = "returns microsecond")
    public org.python.types.Str __microsecond__() {
        return new org.python.types.Str(this.timeUnits[MICROSECOND_INDEX] + "");
    }

    @org.python.Method(__doc__ = "returns minimum datetime")
    private static org.python.Object __min__() {
        org.python.types.Int year = org.python.types.Int.getInt(1);
        org.python.types.Int month = org.python.types.Int.getInt(1);
        org.python.types.Int day = org.python.types.Int.getInt(1);

        org.python.Object[] args = {year, month, day};
        return new DateTime(args, Collections.emptyMap());
    }

    @org.python.Method(__doc__ = "returns maximum datetime")
    private static org.python.Object __max__() {
        org.python.types.Int year = org.python.types.Int.getInt(9999);
        org.python.types.Int month = org.python.types.Int.getInt(12);
        org.python.types.Int day = org.python.types.Int.getInt(31);
        org.python.types.Int hour = org.python.types.Int.getInt(23);
        org.python.types.Int minute = org.python.types.Int.getInt(59);
        org.python.types.Int second = org.python.types.Int.getInt(59);
        org.python.types.Int microsecond = org.python.types.Int.getInt(999999);

        org.python.Object[] args = {year, month, day, hour, minute, second, microsecond};
        return new DateTime(args, Collections.emptyMap());
    }

    @org.python.Method(__doc__ = "Return the day of the week as an integer, where Monday is 0 and Sunday is 6. For " +
        "example, date(2002, 12, 4).weekday() == 2, a Wednesday.")
    public org.python.Object weekday() {
        long y = ((org.python.types.Int) this.year.__int__()).value;
        long m = ((org.python.types.Int) this.month.__int__()).value;
        long d = ((org.python.types.Int) this.day.__int__()).value;

        java.util.Date myCalendar = new java.util.GregorianCalendar((int) y, (int) m - 1, (int) d).getTime();
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(myCalendar);
        int day = c.get(java.util.Calendar.DAY_OF_WEEK);
        int[] convertToPython = {6, 0, 1, 2, 3, 4, 5};
        return org.python.types.Int.getInt(convertToPython[day - 1]);
    }

    @org.python.Method(__doc__ = "parses python datetime into java localdatetime")
    private LocalDateTime toLocalDateTime() {
        return LocalDateTime.parse(this.__str__().value.replace(" ", "T"));
    }

    @org.python.Method(
        __doc__ = "Return self<value.",
        args = {"other"}
    )
    public org.python.Object __lt__(org.python.Object other) {
        if (!(other instanceof org.python.stdlib.datetime.DateTime)) {
            throw new org.python.exceptions.TypeError("'<' not supported between instances of 'datetime.datetime' and '" + other.typeName() + "'");
        }
        LocalDateTime localDateTime = this.toLocalDateTime();
        LocalDateTime otherLocalDateTime = ((DateTime) other).toLocalDateTime();
        return Bool.getBool(localDateTime.isBefore(otherLocalDateTime));
    }

    @org.python.Method(
        __doc__ = "Return self<=value.",
        args = {"other"}
    )
    public org.python.Object __le__(org.python.Object other) {
        if (!(other instanceof org.python.stdlib.datetime.DateTime)) {
            throw new org.python.exceptions.TypeError("'<=' not supported between instances of 'datetime.datetime' and '" + other.typeName() + "'");
        }
        LocalDateTime localDateTime = this.toLocalDateTime();
        LocalDateTime otherLocalDateTime = ((DateTime) other).toLocalDateTime();
        return Bool.getBool(!localDateTime.isAfter(otherLocalDateTime));
    }

    @org.python.Method(
        __doc__ = "Return self==value.",
        args = {"other"}
    )
    public org.python.Object __eq__(org.python.Object other) {
        if (!(other instanceof org.python.stdlib.datetime.DateTime)) {
            return Bool.getBool(false);
        }
        LocalDateTime localDateTime = this.toLocalDateTime();
        LocalDateTime otherLocalDateTime = ((DateTime) other).toLocalDateTime();
        return Bool.getBool(!localDateTime.isBefore(otherLocalDateTime) && !otherLocalDateTime.isBefore(localDateTime));
    }

    @org.python.Method(
        __doc__ = "Return self!=value.",
        args = {"other"}
    )
    public org.python.Object __ne__(org.python.Object other) {
        if (!(other instanceof org.python.stdlib.datetime.DateTime)) {
            return Bool.getBool(true);
        }
        LocalDateTime localDateTime = this.toLocalDateTime();
        LocalDateTime otherLocalDateTime = ((DateTime) other).toLocalDateTime();
        return Bool.getBool(localDateTime.isBefore(otherLocalDateTime) || otherLocalDateTime.isBefore(localDateTime));
    }

    @org.python.Method(
        __doc__ = "Return self>value.",
        args = {"other"}
    )
    public org.python.Object __gt__(org.python.Object other) {
        if (!(other instanceof org.python.stdlib.datetime.DateTime)) {
            throw new org.python.exceptions.TypeError("'>' not supported between instances of 'datetime.datetime' and '" + other.typeName() + "'");
        }
        LocalDateTime localDateTime = this.toLocalDateTime();
        LocalDateTime otherLocalDateTime = ((DateTime) other).toLocalDateTime();
        return Bool.getBool(localDateTime.isAfter(otherLocalDateTime));
    }

    @org.python.Method(
        __doc__ = "Return self>=value.",
        args = {"other"}
    )
    public org.python.Object __ge__(org.python.Object other) {
        if (!(other instanceof org.python.stdlib.datetime.DateTime)) {
            throw new org.python.exceptions.TypeError("'>=' not supported between instances of 'datetime.datetime' and '" + other.typeName() + "'");
        }
        LocalDateTime localDateTime = this.toLocalDateTime();
        LocalDateTime otherLocalDateTime = ((DateTime) other).toLocalDateTime();
        return Bool.getBool(!localDateTime.isBefore(otherLocalDateTime));
    }

    @org.python.Method(__doc__ = "checks arguments for type")
    private void checkContentsTypeArray(org.python.Object[] args, int array_len, org.python.Object typeToLookFor) {
        for (int i = 0; i < array_len; i++) {
            if (!(args[i].getClass().equals(typeToLookFor.getClass()))) {
                throw new org.python.exceptions.TypeError("an integer is required (got type " + args[i].typeName() + ")");
            }
        }
    }

    @org.python.Method(__doc__ = "checks arguments for type")
    private void checkContentsTypeMap(java.util.Map<java.lang.String, org.python.Object> kwargs, org.python.Object typeToLookFor) {
        for (String key : kwargs.keySet()) {
            if (!(kwargs.get(key).getClass().equals(typeToLookFor.getClass()))) {
                throw new org.python.exceptions.TypeError("an integer is required (got type " + kwargs.get(key).typeName() + ")");
            }
        }
    }

    @org.python.Method(
        __doc__ = "Return a datetime with the same attributes, except for those attributes given new values by " +
            "whichever keyword arguments are specified. Note that tzinfo=None can be specified to create a naive " +
            "datetime from an aware datetime with no conversion of date and time data.",
        args = {"args", "kwargs"}
    )
    public org.python.stdlib.datetime.DateTime replace(org.python.Object[] args, java.util.Map<java.lang.String, org.python.Object> kwargs) {
        checkContentsTypeMap(kwargs, Int.getInt(0));
        checkContentsTypeArray(args, args.length, Int.getInt(0));

        HashMap<String, org.python.Object> whatToReplace = new HashMap<>();
        String[] keys = {"year", "month", "day", "hour", "minute", "second", "microsecond"};
        boolean kwargsIsUsed = false;
        int argIndex = 0;
        for (String key : keys) {
            if (kwargs.get(key) != null) {
                whatToReplace.put(key, kwargs.get(key));
                kwargsIsUsed = true;
            } else if (args.length > argIndex) {
                if (kwargsIsUsed)
                    throw new org.python.exceptions.SyntaxError("positional argument follows keyword argument");
                whatToReplace.put(key, args[argIndex]);
                argIndex++;
            }
        }
        for (Map.Entry<java.lang.String, org.python.Object> entry : kwargs.entrySet()) {
            if (!Arrays.asList(keys).contains(entry.getKey())) {
                throw new org.python.exceptions.TypeError("'" + entry.getKey() + "' is an invalid keyword argument for replace()");
            }
        }
        for (int i = 0; i < 7; i++) {
            if (!whatToReplace.containsKey(keys[i])) {
                whatToReplace.put(keys[i], Int.getInt(timeUnits[i]));
            }
        }
        return new DateTime(new org.python.Object[]{}, whatToReplace);
    }

    @org.python.Method(
        __doc__ = "Return a datetime corresponding to a date_string in one of the formats emitted by date.isoformat()" +
            " and datetime.isoformat().",
        args = {"date_string"}
    )
    public static org.python.Object fromisoformat(org.python.Object date_string) {
        if (!(date_string instanceof org.python.types.Str)) {
            throw new org.python.exceptions.TypeError("fromisoformat: argument must be str");
        }
        Integer[] Allowed_lengths = {10, 13, 16, 19, 26};
        String format = "yyyy-MM-dd-HH:mm:ss.nnnnnnnnn";
        String j_str = ((Str) date_string).value;
        Integer len = j_str.length();
        if (!Arrays.stream(Allowed_lengths).anyMatch(i -> i == len)) {
            throw new org.python.exceptions.ValueError("Invalid isoformat string: '" + j_str + "'");
        }
        String mandatory = j_str.substring(0, 10);
        String extra = "";
        if (j_str.length() > 10) {
            extra = "-" + j_str.substring(11);
        }
        j_str = mandatory + extra;
        if (j_str.length() == 10) {
            j_str = j_str + "-00";
        } else if (j_str.length() == 26) {
            j_str = j_str + "000";
        }
        DateTimeFormatter form = DateTimeFormatter.ofPattern(format.substring(0, j_str.length()));
        try {
            LocalDateTime pd = LocalDateTime.parse(j_str, form);
            return new DateTime(new org.python.Object[]{
                Int.getInt(pd.getYear()),
                Int.getInt(pd.getMonthValue()),
                Int.getInt(pd.getDayOfMonth()),
                Int.getInt(pd.getHour()),
                Int.getInt(pd.getMinute()),
                Int.getInt(pd.getSecond()),
                Int.getInt(pd.getNano() / 1000)
            }, Collections.emptyMap()
            );
        } catch (java.time.format.DateTimeParseException e) {
            j_str = ((Str) date_string).value;
            throw new org.python.exceptions.ValueError("Invalid isoformat string: '" + j_str + "'");
        }
    }
}
