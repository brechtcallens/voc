package org.python.stdlib.datetime;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.python.types.Int.getInt;

public class TimeDelta extends org.python.types.Object {
    private static final int MAX_DAYS = 999999999;
    private static final int MAX_SECONDS = 86399;
    private static final int MAX_MICROSECONDS = 999999;

    @org.python.Attribute
    public org.python.Object days = __days__();

    @org.python.Attribute
    public org.python.Object seconds = __seconds__();

    @org.python.Attribute
    public org.python.Object microseconds = __microseconds__();

    @org.python.Attribute
    public static final org.python.Object min = __min__();

    @org.python.Attribute
    public static final org.python.Object max = __max__();

    @org.python.Attribute
    public static final org.python.Object resolution = __resolution__();

    @org.python.Method(__doc__ = "")
    public TimeDelta(org.python.Object[] args, java.util.Map<java.lang.String, org.python.Object> kwargs) {
        super();

        this.days = getInt(0);
        this.seconds = getInt(0);
        this.microseconds = getInt(0);

        if (args.length > 7) {
            throw new org.python.exceptions.TypeError("__new__() takes at most 7 arguments (" + args.length + " given)");
        }

        String[] allowed = {"days", "seconds", "microseconds", "milliseconds", "minutes", "hours", "weeks"};
        for(int i = 0; i < args.length; i++) {
            if (!(args[i] instanceof org.python.types.Int) && !(args[i] instanceof org.python.types.Float)){
                throw new org.python.exceptions.TypeError("unsupported type for timedelta " + allowed[i] + " component: " + args[i].typeName());
            }
        }

        List<String> allowedList = Arrays.asList(allowed);
        if (!kwargs.isEmpty()) {
            boolean correct = true;
            for (java.lang.String key : kwargs.keySet()) {
                correct = allowedList.contains(key);
                if (!correct) {
                    throw new org.python.exceptions.TypeError(key + " is an invalid keyword argument for this function");

                }
            }
            if (args.length > 0) {
                if (kwargs.get("days") != null && args.length >= 1) {
                    throw new org.python.exceptions.TypeError("Argument given by name ('days') and position (1)");
                }

                if (kwargs.get("seconds") != null && args.length >= 2) {
                    throw new org.python.exceptions.TypeError("Argument given by name ('seconds') and position (2)");
                }

                if (kwargs.get("microseconds") != null && args.length >= 3) {
                    throw new org.python.exceptions.TypeError("Argument given by name ('microseconds') and position (3)");
                }
            }
        }

        if (args.length == 3) {
            this.days = args[0].__int__();
            this.seconds = args[1].__int__();
            this.microseconds = args[2].__int__();

        } else if (args.length == 2) {
            this.days = args[0].__int__();
            this.seconds = args[1].__int__();
            this.microseconds = getInt(0);

        } else if (args.length == 1) {
            this.days = args[0].__int__();
            this.seconds = getInt(0);
            this.microseconds = getInt(0);
        }

        if (kwargs.get("days") != null) {
            long day = ((org.python.types.Int) kwargs.get("days")).value;
            this.days = getInt(day);
        }

        if (kwargs.get("seconds") != null) {
            long second = ((org.python.types.Int) kwargs.get("seconds")).value;
            this.seconds = getInt(second);
        }

        if (kwargs.get("microseconds") != null) {
            long microsecond = ((org.python.types.Int) kwargs.get("microseconds")).value;
            this.microseconds = getInt(microsecond);
        }

        if (kwargs.get("weeks") != null) {
            long weeks = ((org.python.types.Int) kwargs.get("weeks")).value;
            long day = ((org.python.types.Int) this.days).value;
            day = day + weeks * 7;
            this.days = getInt(day);
        }

        if (kwargs.get("hours") != null) {
            long hours = ((org.python.types.Int) kwargs.get("hours")).value;
            long second = ((org.python.types.Int) this.seconds).value;
            second = second + hours * 3600;
            this.seconds = getInt(second);
        }

        if (kwargs.get("minutes") != null) {
            long minutes = ((org.python.types.Int) kwargs.get("minutes")).value;
            long second = ((org.python.types.Int) this.seconds).value;
            second = second + minutes * 60;
            this.seconds = getInt(second);
        }

        if (kwargs.get("milliseconds") != null) {
            long millisecond = ((org.python.types.Int) kwargs.get("milliseconds")).value;
            long microsecond = ((org.python.types.Int) this.microseconds).value;
            microsecond = microsecond + millisecond * 1000;
            this.microseconds = getInt(microsecond);
        }

        long microsecond = ((org.python.types.Int) this.microseconds).value;
        if (microsecond > MAX_MICROSECONDS || microsecond < 0) {
            long second = ((org.python.types.Int) this.seconds).value;
            second += microsecond / (MAX_MICROSECONDS + 1);
            microsecond = microsecond % (MAX_MICROSECONDS + 1);
            if (microsecond < 0) {
                second--;
                microsecond = (MAX_MICROSECONDS + 1) + microsecond;
            }
            this.seconds = getInt(second);
            this.microseconds = getInt(microsecond);
        }

        long second = ((org.python.types.Int) this.seconds).value;
        if (second > MAX_SECONDS || second < 0) {
            long day = ((org.python.types.Int) this.days).value;
            day += second / (MAX_SECONDS + 1);
            second = second % (MAX_SECONDS + 1);
            if (second < 0) {
                day--;
                second = (MAX_SECONDS + 1) + second;
            }
            this.days = getInt(day);
            this.seconds = getInt(second);
        }

        long day = ((org.python.types.Int) this.days).value;
        if(day < -MAX_DAYS || day > MAX_DAYS) {
            throw new org.python.exceptions.OverflowError("Python int to large to convert to C int");
        }
    }

    @org.python.Method(__doc__ = "returns day")
    public org.python.Object __days__() {
        return this.days;
    }

    @org.python.Method(__doc__ = "returns seconds")
    public org.python.Object __seconds__() {
        return this.seconds;
    }

    @org.python.Method(__doc__ = "returns microseconds")
    public org.python.Object __microseconds__() {
        return this.microseconds;
    }

    @org.python.Method()
    public static org.python.Object __min__() {
        org.python.Object[] args = {getInt(-MAX_DAYS)};
        return new TimeDelta(args, Collections.EMPTY_MAP);
    }

    @org.python.Method()
    public static org.python.Object __max__() {
        org.python.Object[] args = {getInt(MAX_DAYS), getInt(MAX_SECONDS), getInt(MAX_MICROSECONDS)};
        return new TimeDelta(args, Collections.EMPTY_MAP);
    }

    @org.python.Method()
    public static org.python.Object __resolution__() {
        org.python.Object[] args = {getInt(0), getInt(0), getInt(1)};
        return new TimeDelta(args, Collections.EMPTY_MAP);
    }

    @org.python.Method()
    public org.python.Object total_seconds() {
        long days = (((org.python.types.Int) this.days).value) * 24 * 3600;
        long sum_seconds = days + (((org.python.types.Int) this.seconds).value);
        long microseconds = (((org.python.types.Int) this.microseconds).value);

        double result = sum_seconds + (microseconds / 1000000.0);
        return new org.python.types.Float(result);
    }

    @org.python.Method(__doc__ = "", args = {"other"})
    public org.python.Object __add__(org.python.Object other) {
        if (!(other instanceof TimeDelta)) {
            throw new org.python.exceptions.TypeError("unsupported operand type(s) for +: 'datetime.timedelta' and '" + other.typeName() + "'");
        }

        long thisDays = ((org.python.types.Int) this.days).value;
        TimeDelta otherObject = (org.python.stdlib.datetime.TimeDelta) other;
        long otherDays = ((org.python.types.Int) otherObject.days).value;
        long thisSeconds = ((org.python.types.Int) this.seconds).value;
        long otherSeconds = ((org.python.types.Int) otherObject.seconds).value;
        long thisMicroseconds = ((org.python.types.Int) this.microseconds).value;
        long otherMicroSeconds = ((org.python.types.Int) otherObject.microseconds).value;
        long sumDays = thisDays + otherDays;
        long sumSeconds = thisSeconds + otherSeconds;
        long sumMicroseconds = thisMicroseconds + otherMicroSeconds;
        org.python.Object[] args = {getInt(sumDays), getInt(sumSeconds), getInt(sumMicroseconds)};
        TimeDelta TD = new TimeDelta(args, Collections.EMPTY_MAP);
        return TD;
    }

    @org.python.Method(__doc__ = "", args = {"other"})
    public org.python.Object __sub__(org.python.Object other) {
        if (!(other instanceof TimeDelta)) {
            throw new org.python.exceptions.TypeError("unsupported operand type(s) for -: 'datetime.timedelta' and '" + other.typeName() + "'");
        }

        long thisDays = ((org.python.types.Int) this.days).value;
        TimeDelta otherObject = (org.python.stdlib.datetime.TimeDelta) other;
        long otherDays = ((org.python.types.Int) otherObject.days).value;
        long thisSeconds = ((org.python.types.Int) this.seconds).value;
        long otherSeconds = ((org.python.types.Int) otherObject.seconds).value;
        long thisMicroseconds = ((org.python.types.Int) this.microseconds).value;
        long otherMicroSeconds = ((org.python.types.Int) otherObject.microseconds).value;
        long sumDays = thisDays - otherDays;
        long sumSeconds = thisSeconds - otherSeconds;
        long sumMicroseconds = thisMicroseconds - otherMicroSeconds;
        org.python.Object[] args = {getInt(sumDays), getInt(sumSeconds), getInt(sumMicroseconds)};
        TimeDelta TD = new TimeDelta(args, Collections.EMPTY_MAP);
        return TD;
    }

    @org.python.Method(__doc__ = "", args = {"other"})
    public org.python.Object __mul__(org.python.Object multiplier){
        long thisDays = ((org.python.types.Int) this.days).value;
        long thisSeconds = ((org.python.types.Int) this.seconds).value;
        long thisMicroseconds = ((org.python.types.Int) this.microseconds).value;

        if(multiplier instanceof org.python.types.Int){
            long multiplierValue = ((org.python.types.Int) multiplier).value;

            long productDays = thisDays * multiplierValue;
            long productSeconds = thisSeconds * multiplierValue;
            long productMicroseconds = thisMicroseconds * multiplierValue;

            org.python.Object[] args = {getInt(productDays), getInt(productSeconds), getInt(productMicroseconds)};
            TimeDelta TD = new TimeDelta(args, Collections.EMPTY_MAP);
            return TD;
        }
        else if(multiplier instanceof org.python.types.Float){
            double multiplierValue = ((org.python.types.Float) multiplier).value;

            double productDays = thisDays * multiplierValue;
            double productSeconds = thisSeconds * multiplierValue;
            double productMicroseconds = thisMicroseconds * multiplierValue;

            org.python.Object[] args = {new org.python.types.Float(productDays), new org.python.types.Float(productSeconds), new org.python.types.Float(productMicroseconds)};
            TimeDelta TD = new TimeDelta(args, Collections.EMPTY_MAP);
            return TD;
        }
        else {
            throw new org.python.exceptions.TypeError("can't multiply sequence by non-int of type 'datetime.timedelta'");
        }
    }

    public org.python.Object __pos__() {
        long otherSeconds = ((org.python.types.Int) this.seconds).value;
        long otherMicroSeconds = ((org.python.types.Int) this.microseconds).value;
        long otherDays = ((org.python.types.Int) this.days).value;
        org.python.Object[] args = {getInt(otherDays), getInt(otherSeconds), getInt(otherMicroSeconds)};
        TimeDelta TD = new TimeDelta(args, Collections.EMPTY_MAP);
        return TD;
    }

    public org.python.types.Str __str__() {
        String returnStr = "";

        long days = ((org.python.types.Int) this.days).value;
        if(days != 0) {
            returnStr += days + " day" + ((days > 1 || days < -1) ? "s" : "") + ", ";
        }

        long seconds = ((org.python.types.Int) this.seconds).value;
        long minutes = seconds / 60;
        seconds = seconds % 60;
        long hours = minutes / 60;
        minutes = minutes % 60;
        returnStr += (hours < 10 ? "0" : "") + hours + ":";
        returnStr += (minutes < 10 ? "0" : "") + minutes + ":";
        returnStr += (seconds < 10 ? "0" : "") + seconds;

        long microseconds = ((org.python.types.Int) this.microseconds).value;
        if (microseconds == 0) {
            returnStr += "";
        } else if (microseconds < 10) {
            returnStr += ".00000" + microseconds;
        } else if (microseconds < 100) {
            returnStr += ".0000" + microseconds;
        } else if (microseconds < 1000) {
            returnStr += ".000" + microseconds;
        } else if (microseconds < 10000) {
            returnStr += ".00" + microseconds;
        } else if (microseconds < 100000) {
            returnStr += ".0" + microseconds;
        } else {
            returnStr += "." + microseconds;
        }

        return new org.python.types.Str(returnStr);
    }

    public org.python.Object __lt__(org.python.Object other){
        if (!(other instanceof TimeDelta)) {
            throw new org.python.exceptions.TypeError("unsupported operand type(s) for <: 'datetime.timedelta' and '" + other.typeName() + "'");
        }
        TimeDelta otherObject = (org.python.stdlib.datetime.TimeDelta) other;

        org.python.types.Int thisDays = (org.python.types.Int) this.days;
        org.python.types.Int otherDays = (org.python.types.Int) otherObject.days;
        org.python.types.Int thisSeconds = (org.python.types.Int) this.seconds;
        org.python.types.Int otherSeconds = (org.python.types.Int) otherObject.seconds;
        org.python.types.Int thisMicroseconds = (org.python.types.Int) this.microseconds;
        org.python.types.Int otherMicroseconds = (org.python.types.Int) otherObject.microseconds;

        if(thisDays.value < otherDays.value){
            return org.python.types.Bool.TRUE;
        }
        else if(thisDays.value == otherDays.value) {
            if(thisSeconds.value < otherSeconds.value){
                return org.python.types.Bool.TRUE;
            }
            else if(thisSeconds.value == otherSeconds.value) {
                return org.python.types.Bool.getBool(thisMicroseconds.value < otherMicroseconds.value);
            }
        }
        return org.python.types.Bool.FALSE;
    }

    public org.python.Object __le__(org.python.Object other){
        if (!(other instanceof TimeDelta)) {
            throw new org.python.exceptions.TypeError("unsupported operand type(s) for <=: 'datetime.timedelta' and '" + other.typeName() + "'");
        }
        TimeDelta otherObject = (org.python.stdlib.datetime.TimeDelta) other;

        org.python.types.Int thisDays = (org.python.types.Int) this.days;
        org.python.types.Int otherDays = (org.python.types.Int) otherObject.days;
        org.python.types.Int thisSeconds = (org.python.types.Int) this.seconds;
        org.python.types.Int otherSeconds = (org.python.types.Int) otherObject.seconds;
        org.python.types.Int thisMicroseconds = (org.python.types.Int) this.microseconds;
        org.python.types.Int otherMicroseconds = (org.python.types.Int) otherObject.microseconds;

        if(thisDays.value < otherDays.value){
            return org.python.types.Bool.TRUE;
        }
        else if(thisDays.value == otherDays.value) {
            if(thisSeconds.value < otherSeconds.value){
                return org.python.types.Bool.TRUE;
            }
            else if(thisSeconds.value == otherSeconds.value) {
                return org.python.types.Bool.getBool(thisMicroseconds.value <= otherMicroseconds.value);
            }
        }
        return org.python.types.Bool.FALSE;
    }

    public org.python.Object __eq__(org.python.Object other) {
        if (!(other instanceof TimeDelta)) {
            throw new org.python.exceptions.TypeError("unsupported operand type(s) for =: 'datetime.timedelta' and '" + other.typeName() + "'");
        }
        TimeDelta otherObject = (org.python.stdlib.datetime.TimeDelta) other;

        org.python.types.Int thisDays = (org.python.types.Int) this.days;
        org.python.types.Int otherDays = (org.python.types.Int) otherObject.days;
        org.python.types.Int thisSeconds = (org.python.types.Int) this.seconds;
        org.python.types.Int otherSeconds = (org.python.types.Int) otherObject.seconds;
        org.python.types.Int thisMicroseconds = (org.python.types.Int) this.microseconds;
        org.python.types.Int otherMicroseconds = (org.python.types.Int) otherObject.microseconds;

        return org.python.types.Bool.getBool(thisDays.value == otherDays.value &&
                                             thisSeconds.value == otherSeconds.value &&
                                             thisMicroseconds.value == otherMicroseconds.value);
    }

    public org.python.Object __ne__(org.python.Object other) {
        if (!(other instanceof TimeDelta)) {
            throw new org.python.exceptions.TypeError("unsupported operand type(s) for !=: 'datetime.timedelta' and '" + other.typeName() + "'");
        }
        TimeDelta otherObject = (org.python.stdlib.datetime.TimeDelta) other;

        org.python.types.Int thisDays = (org.python.types.Int) this.days;
        org.python.types.Int otherDays = (org.python.types.Int) otherObject.days;
        org.python.types.Int thisSeconds = (org.python.types.Int) this.seconds;
        org.python.types.Int otherSeconds = (org.python.types.Int) otherObject.seconds;
        org.python.types.Int thisMicroseconds = (org.python.types.Int) this.microseconds;
        org.python.types.Int otherMicroseconds = (org.python.types.Int) otherObject.microseconds;

        return org.python.types.Bool.getBool(thisDays.value != otherDays.value ||
            thisSeconds.value != otherSeconds.value ||
            thisMicroseconds.value != otherMicroseconds.value);
    }

    public org.python.Object __ge__(org.python.Object other){
        if (!(other instanceof TimeDelta)) {
            throw new org.python.exceptions.TypeError("unsupported operand type(s) for >=: 'datetime.timedelta' and '" + other.typeName() + "'");
        }
        TimeDelta otherObject = (org.python.stdlib.datetime.TimeDelta) other;

        org.python.types.Int thisDays = (org.python.types.Int) this.days;
        org.python.types.Int otherDays = (org.python.types.Int) otherObject.days;
        org.python.types.Int thisSeconds = (org.python.types.Int) this.seconds;
        org.python.types.Int otherSeconds = (org.python.types.Int) otherObject.seconds;
        org.python.types.Int thisMicroseconds = (org.python.types.Int) this.microseconds;
        org.python.types.Int otherMicroseconds = (org.python.types.Int) otherObject.microseconds;

        if(thisDays.value > otherDays.value){
            return org.python.types.Bool.TRUE;
        }
        else if(thisDays.value == otherDays.value) {
            if(thisSeconds.value > otherSeconds.value){
                return org.python.types.Bool.TRUE;
            }
            else if(thisSeconds.value == otherSeconds.value) {
                return org.python.types.Bool.getBool(thisMicroseconds.value >= otherMicroseconds.value);
            }
        }
        return org.python.types.Bool.FALSE;
    }

    public org.python.Object __gt__(org.python.Object other){
        if (!(other instanceof TimeDelta)) {
            throw new org.python.exceptions.TypeError("unsupported operand type(s) for >: 'datetime.timedelta' and '" + other.typeName() + "'");
        }
        TimeDelta otherObject = (org.python.stdlib.datetime.TimeDelta) other;

        org.python.types.Int thisDays = (org.python.types.Int) this.days;
        org.python.types.Int otherDays = (org.python.types.Int) otherObject.days;
        org.python.types.Int thisSeconds = (org.python.types.Int) this.seconds;
        org.python.types.Int otherSeconds = (org.python.types.Int) otherObject.seconds;
        org.python.types.Int thisMicroseconds = (org.python.types.Int) this.microseconds;
        org.python.types.Int otherMicroseconds = (org.python.types.Int) otherObject.microseconds;

        if(thisDays.value > otherDays.value){
            return org.python.types.Bool.TRUE;
        }
        else if(thisDays.value == otherDays.value) {
            if(thisSeconds.value > otherSeconds.value){
                return org.python.types.Bool.TRUE;
            }
            else if(thisSeconds.value == otherSeconds.value) {
                return org.python.types.Bool.getBool(thisMicroseconds.value > otherMicroseconds.value);
            }
        }
        return org.python.types.Bool.FALSE;
    }

}
