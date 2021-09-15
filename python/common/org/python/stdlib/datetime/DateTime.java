package org.python.stdlib.datetime;

import org.python.types.Bool;
import org.python.types.Float;
import org.python.types.Int;
import org.python.types.Object;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.WeekFields;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
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

    private Long[] timeUnits = {0l, 0l, 0l, 0l, 0l, 0l, 0l};

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

    public DateTime(org.python.Object[] args, java.util.Map<java.lang.String, org.python.Object> kwargs) {
        super();
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

    @org.python.Method(__doc__ = "")
    public org.python.Object date() {
        org.python.Object[] args = {org.python.types.Int.getInt(this.timeUnits[YEAR_INDEX]), org.python.types.Int.getInt(this.timeUnits[MONTH_INDEX]),
            org.python.types.Int.getInt(this.timeUnits[DAY_INDEX])};
        return new Date(args, Collections.emptyMap());
    }

    @org.python.Method(__doc__ = "")
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

    @org.python.Method(__doc__ = "")
    private static org.python.Object __min__() {
        org.python.types.Int year = org.python.types.Int.getInt(1);
        org.python.types.Int month = org.python.types.Int.getInt(1);
        org.python.types.Int day = org.python.types.Int.getInt(1);

        org.python.Object[] args = {year, month, day};
        return new DateTime(args, Collections.emptyMap());
    }

    @org.python.Method(__doc__ = "")
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

    @org.python.Method(__doc__ = "")
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

    @org.python.Method(__doc__ = "")
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

    @org.python.Method(
        __doc__ = "Return POSIX timestamp corresponding to the datetime instance. The return value is a float similar " +
            "to that returned by time.time(). Naive datetime instances are assumed to represent local time and this " +
            "method relies on the platform C mktime() function to perform the conversion. Since datetime supports wider " +
            "range of values than mktime() on many platforms, this method may raise OverflowError for times far in the " +
            "past or far in the future."
    )
    public org.python.Object timestamp() {
        LocalDateTime maxAllowedLocalDateTime = LocalDateTime.of(9999, 12, 31, 22, 59, 59, 999999999);
        LocalDateTime minAllowedLocalDateTime = LocalDateTime.of(1, 1, 2, 0, 0, 0, 0);
        LocalDateTime localDateTime = this.toLocalDateTime();

        if (localDateTime.isAfter(maxAllowedLocalDateTime)) {
            throw new org.python.exceptions.ValueError("year " + (localDateTime.getYear()+1) + " is out of range");
        } else if (localDateTime.isBefore(minAllowedLocalDateTime)) {
            throw new org.python.exceptions.ValueError("year " + (localDateTime.getYear()-1) + " is out of range");
        }

        double epochSeconds = localDateTime.atZone(ZoneId.systemDefault()).toEpochSecond();
        return new Float(epochSeconds + (double)localDateTime.getNano() / 1e9);
    }

    public static org.python.Object fromtimestamp(org.python.Object timestamp) { // TODO: tz arg
        if (!(timestamp instanceof org.python.types.Float) && !(timestamp instanceof org.python.types.Int)) {
            throw new org.python.exceptions.TypeError("an integer is required (got type " + timestamp.typeName() + ")");
        }

        long timestampSeconds = (long) ((Float) timestamp).value;
        long timestampNano = 1000 * (long) (((Float) timestamp).value * 1e6 % 1e6);
        Instant instant = Instant.ofEpochSecond(timestampSeconds, timestampNano);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

        return new DateTime(new org.python.Object[] {
            Int.getInt(localDateTime.getYear()),
            Int.getInt(localDateTime.getMonthValue()),
            Int.getInt(localDateTime.getDayOfMonth()),
            Int.getInt(localDateTime.getHour()),
            Int.getInt(localDateTime.getMinute()),
            Int.getInt(localDateTime.getSecond()),
            Int.getInt(localDateTime.getNano() / 1000)
        }, Collections.emptyMap());
    }
}
