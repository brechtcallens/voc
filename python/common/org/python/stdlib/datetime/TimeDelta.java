package org.python.stdlib.datetime;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TimeDelta extends org.python.types.Object {

    @org.python.Attribute
    public org.python.Object days = __days__();

    @org.python.Attribute
    public org.python.Object seconds = __seconds__();

    @org.python.Attribute
    public org.python.Object microseconds = __microseconds__();

    @org.python.Attribute
    public org.python.Object min = __min__();

    @org.python.Attribute
    public org.python.Object max = __max__();

    @org.python.Attribute
    public org.python.Object resolution = __resolution__();

    @org.python.Method(__doc__ = "")
    public TimeDelta(org.python.Object[] args, java.util.Map<java.lang.String, org.python.Object> kwargs) {
        super();

        this.days = org.python.types.Int.getInt(0);
        this.seconds = org.python.types.Int.getInt(0);
        this.microseconds = org.python.types.Int.getInt(0);

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
            this.microseconds = org.python.types.Int.getInt(0);

        } else if (args.length == 1) {
            this.days = args[0].__int__();
            this.seconds = org.python.types.Int.getInt(0);
            this.microseconds = org.python.types.Int.getInt(0);
        }

        if (kwargs.get("weeks") != null) {
            long weeks = ((org.python.types.Int) kwargs.get("weeks")).value;
            long day = ((org.python.types.Int) this.days).value;
            day = day + weeks * 7;
            this.days = org.python.types.Int.getInt(day);
        }

        if (kwargs.get("hours") != null) {
            long hours = ((org.python.types.Int) kwargs.get("hours")).value;
            long second = ((org.python.types.Int) this.seconds).value;
            second = second + hours * 3600;
            this.seconds = org.python.types.Int.getInt(second);
        }

        if (kwargs.get("minutes") != null) {
            long minutes = ((org.python.types.Int) kwargs.get("minutes")).value;
            long second = ((org.python.types.Int) this.seconds).value;
            second = second + minutes * 60;
            this.seconds = org.python.types.Int.getInt(second);
        }

        if (kwargs.get("milliseconds") != null) {
            long millisecond = ((org.python.types.Int) kwargs.get("milliseconds")).value;
            long microsecond = ((org.python.types.Int) this.microseconds).value;
            microsecond = microsecond + millisecond * 1000;
            this.microseconds = org.python.types.Int.getInt(microsecond);
        }
    }

    @org.python.Method(__doc__ = "returns day")
    public org.python.types.Str __days__() {
        return new org.python.types.Str(this.days + "");
    }

    @org.python.Method(__doc__ = "returns seconds")
    public org.python.types.Str __seconds__() {
        return new org.python.types.Str(this.seconds + "");
    }

    @org.python.Method(__doc__ = "returns microseconds")
    public org.python.types.Str __microseconds__() {
        return new org.python.types.Str(this.microseconds + "");
    }

    @org.python.Method()
    public org.python.Object __min__() {

        return new org.python.types.Str("-999999 days, 0:00:00");
    }

    @org.python.Method()
    public org.python.Object __max__() {
        return new org.python.types.Str("9999999 days, 23:59:59.999999");
    }

    @org.python.Method()
    public org.python.Object __resolution__() {
        return new org.python.types.Str("0:00:00.000001");
    }

    @org.python.Method()
    public org.python.types.Str totalSeconds() {
        long days = (((org.python.types.Int) this.days).value) * 24 * 3600;
        long sum_seconds = days + (((org.python.types.Int) this.seconds).value);
        long microseconds = (((org.python.types.Int) this.microseconds).value);
        String micro = "";
        if(microseconds < 0) {
            sum_seconds--;
            microseconds = 1000000 + microseconds;
        }
        if (microseconds == 0) {
            micro = "0";
        } else if (microseconds < 10) {
            micro = "00000" + microseconds;
        } else if (microseconds < 100) {
            micro = "0000" + microseconds;
        } else if (microseconds < 1000) {
            micro = "000" + microseconds;
        } else if (microseconds < 10000) {
            micro = "00" + microseconds;
        } else if (microseconds < 100000) {
            micro = "0" + microseconds;
        } else {
            micro = "" + microseconds;
        }
        String returnStr = ("" + sum_seconds + "." + micro);
        return new org.python.types.Str(returnStr);
    }

    @org.python.Method(__doc__ = "", args = {"other"})
    public org.python.Object __add__(org.python.Object other) {
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
        org.python.Object[] args = {org.python.types.Int.getInt(sumDays), org.python.types.Int.getInt(sumSeconds), org.python.types.Int.getInt(sumMicroseconds)};
        TimeDelta TD = new TimeDelta(args, Collections.EMPTY_MAP);
        return TD;
    }

    @org.python.Method(__doc__ = "", args = {"other"})
    public org.python.Object __sub__(org.python.Object other) {
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
        org.python.Object[] args = {org.python.types.Int.getInt(sumDays), org.python.types.Int.getInt(sumSeconds), org.python.types.Int.getInt(sumMicroseconds)};
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

            org.python.Object[] args = {org.python.types.Int.getInt(productDays), org.python.types.Int.getInt(productSeconds), org.python.types.Int.getInt(productMicroseconds)};
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
        org.python.Object[] args = {org.python.types.Int.getInt(otherDays), org.python.types.Int.getInt(otherSeconds), org.python.types.Int.getInt(otherMicroSeconds)};
        TimeDelta TD = new TimeDelta(args, Collections.EMPTY_MAP);
        return TD;
    }

    public org.python.types.Str __str__() {
        long dayslong = ((org.python.types.Int) this.days).value;
        String days = Long.toString(dayslong);
        long seconds = ((org.python.types.Int) this.seconds).value;
        long microseconds = ((org.python.types.Int) this.microseconds).value;
        String returnStr = "days: " + days + ", seconds: " + seconds + ", microseconds: " + microseconds;
        return new org.python.types.Str(returnStr);
    }

    public org.python.Object __lt__(org.python.Object other){
        TimeDelta otherObject = (org.python.stdlib.datetime.TimeDelta) other;

        org.python.types.Int thisDays = (org.python.types.Int) this.days;
        org.python.types.Int otherDays = (org.python.types.Int) otherObject.days;
        org.python.types.Int thisSeconds = (org.python.types.Int) this.seconds;
        org.python.types.Int otherSeconds = (org.python.types.Int) otherObject.seconds;
        org.python.types.Int thisMicroseconds = (org.python.types.Int) this.microseconds;
        org.python.types.Int otherMicroseconds = (org.python.types.Int) otherObject.microseconds;

        if(thisDays.value < otherDays.value){
            return org.python.types.Bool.getBool(true);
        }
        else if(thisDays.value == otherDays.value) {
            if(thisSeconds.value < otherSeconds.value){
                return org.python.types.Bool.getBool(true);
            }
            else if(thisSeconds.value == otherSeconds.value) {
                return org.python.types.Bool.getBool(thisMicroseconds.value < otherMicroseconds.value);
            }
        }
        return org.python.types.Bool.getBool(false);
    }

    public org.python.Object __le__(org.python.Object other){
        TimeDelta otherObject = (org.python.stdlib.datetime.TimeDelta) other;

        org.python.types.Int thisDays = (org.python.types.Int) this.days;
        org.python.types.Int otherDays = (org.python.types.Int) otherObject.days;
        org.python.types.Int thisSeconds = (org.python.types.Int) this.seconds;
        org.python.types.Int otherSeconds = (org.python.types.Int) otherObject.seconds;
        org.python.types.Int thisMicroseconds = (org.python.types.Int) this.microseconds;
        org.python.types.Int otherMicroseconds = (org.python.types.Int) otherObject.microseconds;

        if(thisDays.value < otherDays.value){
            return org.python.types.Bool.getBool(true);
        }
        else if(thisDays.value == otherDays.value) {
            if(thisSeconds.value < otherSeconds.value){
                return org.python.types.Bool.getBool(true);
            }
            else if(thisSeconds.value == otherSeconds.value) {
                return org.python.types.Bool.getBool(thisMicroseconds.value <= otherMicroseconds.value);
            }
        }
        return org.python.types.Bool.getBool(false);
    }

    public org.python.Object __eq__(org.python.Object other) {
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

    public org.python.Object __neq__(org.python.Object other) {
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
        TimeDelta otherObject = (org.python.stdlib.datetime.TimeDelta) other;

        org.python.types.Int thisDays = (org.python.types.Int) this.days;
        org.python.types.Int otherDays = (org.python.types.Int) otherObject.days;
        org.python.types.Int thisSeconds = (org.python.types.Int) this.seconds;
        org.python.types.Int otherSeconds = (org.python.types.Int) otherObject.seconds;
        org.python.types.Int thisMicroseconds = (org.python.types.Int) this.microseconds;
        org.python.types.Int otherMicroseconds = (org.python.types.Int) otherObject.microseconds;

        if(thisDays.value > otherDays.value){
            return org.python.types.Bool.getBool(true);
        }
        else if(thisDays.value == otherDays.value) {
            if(thisSeconds.value > otherSeconds.value){
                return org.python.types.Bool.getBool(true);
            }
            else if(thisSeconds.value == otherSeconds.value) {
                return org.python.types.Bool.getBool(thisMicroseconds.value >= otherMicroseconds.value);
            }
        }
        return org.python.types.Bool.getBool(false);
    }

    public org.python.Object __gt__(org.python.Object other){
        TimeDelta otherObject = (org.python.stdlib.datetime.TimeDelta) other;

        org.python.types.Int thisDays = (org.python.types.Int) this.days;
        org.python.types.Int otherDays = (org.python.types.Int) otherObject.days;
        org.python.types.Int thisSeconds = (org.python.types.Int) this.seconds;
        org.python.types.Int otherSeconds = (org.python.types.Int) otherObject.seconds;
        org.python.types.Int thisMicroseconds = (org.python.types.Int) this.microseconds;
        org.python.types.Int otherMicroseconds = (org.python.types.Int) otherObject.microseconds;

        if(thisDays.value > otherDays.value){
            return org.python.types.Bool.getBool(true);
        }
        else if(thisDays.value == otherDays.value) {
            if(thisSeconds.value > otherSeconds.value){
                return org.python.types.Bool.getBool(true);
            }
            else if(thisSeconds.value == otherSeconds.value) {
                return org.python.types.Bool.getBool(thisMicroseconds.value > otherMicroseconds.value);
            }
        }
        return org.python.types.Bool.getBool(false);
    }

}
