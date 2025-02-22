package org.python.stdlib.datetime;

import java.util.Collections;

public class Date extends org.python.types.Object {

    @org.python.Attribute
    public org.python.Object year = __year__();

    @org.python.Attribute
    public org.python.Object month = __month__();

    @org.python.Attribute
    public org.python.Object day = __day__();

    @org.python.Attribute
    public static final org.python.Object min = __min__();

    @org.python.Attribute
    public static final org.python.Object max = __max__();

    @org.python.Method(__doc__ = "")
    public Date(org.python.Object[] args, java.util.Map<java.lang.String, org.python.Object> kwargs) {
        super();

        java.util.HashMap<java.lang.String, org.python.Object> filteredKwargs = new java.util.HashMap<>(kwargs.size());
        kwargs.forEach((k, v) -> {
            if (k.equals("year") || k.equals("month") || k.equals("day")) {
                filteredKwargs.put(k, v);
            }
        });
        kwargs = filteredKwargs;

        if (args.length + kwargs.size() > 3) {
            int val = args.length + kwargs.size();
            throw new org.python.exceptions.TypeError("function takes at most 3 arguments (" + val + " given)");
        }

        if (args.length + kwargs.size() == 3) {
            if (kwargs.get("year") != null && args.length == 2) {
                throw new org.python.exceptions.SyntaxError("positional argument follows keyword argument");
            } else if (kwargs.get("month") != null && args.length == 2) {
                throw new org.python.exceptions.SyntaxError("positional argument follows keyword argument");
            }
            if (kwargs.get("year") != null) {
                this.year = kwargs.get("year");
            } else {
                this.year = args[0];
            }

            if (kwargs.get("month") != null) {
                this.month = kwargs.get("month");
            } else {
                this.month = args[1];
            }

            if (kwargs.get("day") != null) {
                this.day = kwargs.get("day");
            } else {
                this.day = args[2];
            }

            if ((this.year instanceof org.python.types.Int) && (this.month instanceof org.python.types.Int) && (this.day instanceof org.python.types.Int)) {
                if (1 <= ((org.python.types.Int) this.year).value && ((org.python.types.Int) this.year).value <= 9999) {
                    if (1d <= ((org.python.types.Int) this.month).value && ((org.python.types.Int) this.month).value <= 12d) {
                        long daysThisMonth = daysInMonth(((org.python.types.Int) this.year).value, ((org.python.types.Int) this.month).value);
                        if (1d <= ((org.python.types.Int) this.day).value && ((org.python.types.Int) this.day).value <= daysThisMonth) {
                        } else {
                            throw new org.python.exceptions.ValueError("day is out of range for month");
                        }
                    } else {
                        throw new org.python.exceptions.ValueError("month must be in 1..12");
                    }
                } else {
                    throw new org.python.exceptions.ValueError("year " + this.year + " is out of range");
                }
            } else {
                if (!(this.year instanceof org.python.types.Int)) {
                    throw new org.python.exceptions.TypeError("an integer is required (got type " + this.year.typeName() + ")");
                }
                if (!(this.month instanceof org.python.types.Int)) {
                    throw new org.python.exceptions.TypeError("an integer is required (got type " + this.month.typeName() + ")");
                }
                if (!(this.day instanceof org.python.types.Int)) {
                    throw new org.python.exceptions.TypeError("an integer is required (got type " + this.day.typeName() + ")");
                }
            }
        }

        if (args.length + kwargs.size() == 2) {
            this.year = null;
            this.month = null;
            this.day = null;
            if (args.length == 2) {
                this.year = args[0];
                this.month = args[1];
            }

            if (kwargs.get("year") != null) {
                this.year = kwargs.get("year");
            } else if (args.length > 0) {
                this.year = args[0];
            }
            if (kwargs.get("month") != null) {
                this.month = kwargs.get("month");
            }
            if (kwargs.get("day") != null) {
                this.day = kwargs.get("day");
            }

            String y = this.year + "";
            String m = this.month + "";
            String d = this.day + "";

            if (this.year != null && !(this.year instanceof org.python.types.Int)) {
                throw new org.python.exceptions.TypeError("an integer is required (got type " + this.year.typeName() + ")");
            }
            if (kwargs.get("year") != null && args.length > 0) {
                throw new org.python.exceptions.SyntaxError("positional argument follows keyword argument");
            }

            if (!(this.month instanceof org.python.types.Int) && this.month != null) {
                throw new org.python.exceptions.TypeError("an integer is required (got type " + this.month.typeName() + ")");
            }

            if (this.year == null) {
                throw new org.python.exceptions.TypeError("function missing required argument 'year' (pos 1)");
            }
            if (this.month == null) {
                throw new org.python.exceptions.TypeError("function missing required argument 'month' (pos 2)");
            }
            if (this.day == null) {
                throw new org.python.exceptions.TypeError("function missing required argument 'day' (pos 3)");
            }
        }

        if (args.length + kwargs.size() == 1) {
            this.year = null;
            if (kwargs.get("year") != null) {
                this.year = kwargs.get("year");
            } else if (args.length > 0) {
                this.year = args[0];
            }

            if (!(this.year instanceof org.python.types.Int) && this.year != null) {
                throw new org.python.exceptions.TypeError("an integer is required (got type " + this.year.typeName() + ")");
            }
            if (this.year != null) {
                throw new org.python.exceptions.TypeError("function missing required argument 'month' (pos 2)");
            }
            else {
                throw new org.python.exceptions.TypeError("function missing required argument 'year' (pos 1)");
            }
        }

        if (args.length + kwargs.size() == 0) {
            throw new org.python.exceptions.TypeError("function missing required argument 'year' (pos 1)");
        }
    }

    @org.python.Method(__doc__ = "", args = {"dtstr"})
    public static Date fromisoformat(org.python.Object dtstr) {
        // It is assumed that this function will only be called with a
        // string of length exactly 10, and (though this is not used) ASCII-only
        if (!(dtstr instanceof org.python.types.Str)) {
            throw new org.python.exceptions.TypeError("fromisoformat: argument must be str");
        }
        String s = ((org.python.types.Str) dtstr).value;
        try {
            int year = Integer.parseInt(s.substring(0, 4));
            if (s.charAt(4) != '-') {
                throw new org.python.exceptions.ValueError("Invalid date separator: " + s.charAt(4));
            }

            int month = Integer.parseInt(s.substring(5, 7));
            if (s.charAt(7) != '-') {
                throw new org.python.exceptions.ValueError("Invalid date separator");
            }

            int day = Integer.parseInt(s.substring(8, 10));

            org.python.Object[] args = {org.python.types.Int.getInt(year), org.python.types.Int.getInt(month), org.python.types.Int.getInt(day)};
            return new Date(args, java.util.Collections.emptyMap());
        } catch (Exception ex) {
            throw new org.python.exceptions.ValueError("Invalid isoformat string: '" + dtstr + "'");
        }
    }

    @org.python.Method(__doc__ = "")
    public org.python.Object isoformat() {
        long y = ((org.python.types.Int) this.year).value;
        long m = ((org.python.types.Int) this.month).value;
        long d = ((org.python.types.Int) this.day).value;
        return new org.python.types.Str(String.format("%04d-%02d-%02d", y, m, d));
    }

    private boolean isLeap(long year) {
        return (year % 4 == 0) && (year % 100 != 0 || year % 400 == 0);
    }

    private long daysInMonth(long year, long month) {
        long[] daysInMonth = { -1, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
        if (month == 2 && isLeap(year)) {
            return 29;
        }
        return daysInMonth[(int) month];
    }

    @org.python.Method(__doc__ = "", args = {"other"})
    @Override
    public org.python.Object __eq__(org.python.Object other) {
        if (other instanceof org.python.stdlib.datetime.Date) {
            org.python.stdlib.datetime.Date castedOther = (org.python.stdlib.datetime.Date) other;
            return org.python.types.Bool.getBool(_cmp(castedOther) == 0);
        }
        return org.python.types.NotImplementedType.NOT_IMPLEMENTED;
    }

    @org.python.Method(__doc__ = "", args = {"other"})
    @Override
    public org.python.Object __le__(org.python.Object other) {
        if (other instanceof org.python.stdlib.datetime.Date) {
            org.python.stdlib.datetime.Date castedOther = (org.python.stdlib.datetime.Date) other;
            return org.python.types.Bool.getBool(_cmp(castedOther) <= 0);
        }
        return org.python.types.NotImplementedType.NOT_IMPLEMENTED;
    }

    @org.python.Method(__doc__ = "", args = {"other"})
    @Override
    public org.python.Object __lt__(org.python.Object other) {
        if (other instanceof org.python.stdlib.datetime.Date) {
            org.python.stdlib.datetime.Date castedOther = (org.python.stdlib.datetime.Date) other;
            return org.python.types.Bool.getBool(_cmp(castedOther) < 0);
        }
        return org.python.types.NotImplementedType.NOT_IMPLEMENTED;
    }

    @org.python.Method(__doc__ = "", args = {"other"})
    @Override
    public org.python.Object __ge__(org.python.Object other) {
        if (other instanceof org.python.stdlib.datetime.Date) {
            org.python.stdlib.datetime.Date castedOther = (org.python.stdlib.datetime.Date) other;
            return org.python.types.Bool.getBool(_cmp(castedOther) >= 0);
        }
        return org.python.types.NotImplementedType.NOT_IMPLEMENTED;
    }

    @org.python.Method(__doc__ = "", args = {"other"})
    @Override
    public org.python.Object __gt__(org.python.Object other) {
        if (other instanceof org.python.stdlib.datetime.Date) {
            org.python.stdlib.datetime.Date castedOther = (org.python.stdlib.datetime.Date) other;
            return org.python.types.Bool.getBool(_cmp(castedOther) > 0);
        }
        return org.python.types.NotImplementedType.NOT_IMPLEMENTED;
    }

    private long _cmp(org.python.stdlib.datetime.Date other) {
        long y = ((org.python.types.Int) this.year).value;
        long m = ((org.python.types.Int) this.month).value;
        long d = ((org.python.types.Int) this.day).value;
        long y2 = ((org.python.types.Int) other.year).value;
        long m2 = ((org.python.types.Int) other.month).value;
        long d2 = ((org.python.types.Int) other.day).value;
        if (y > y2) return 1;
        if (y < y2) return -1;
        if (m > m2) return 1;
        if (m < m2) return -1;
        if (d > d2) return 1;
        if (d < d2) return -1;
        return 0;
    }

    @org.python.Method(__doc__ = "")
    @Override
    public org.python.types.Str __str__() {
        String year = this.year + "";
        while (year.length() < 4)
            year = "0" + year;

        String month = this.month + "";
        while (month.length() < 2)
            month = "0" + month;

        String day = this.day + "";
        while (day.length() < 2)
            day = "0" + day;

        return new org.python.types.Str(year + "-" + month + "-" + day);
    }

    @org.python.Method(__doc__ = "")
    @Override
    public org.python.types.Str __repr__() {
        long y = ((org.python.types.Int) this.year).value;
        long m = ((org.python.types.Int) this.month).value;
        long d = ((org.python.types.Int) this.day).value;
        return new org.python.types.Str(String.format("%s.%s(%d, %d, %d)", "datetime", "date", y, m, d));
    }

    @org.python.Method(__doc__ = "")
    public org.python.types.Str __year__() {
        return new org.python.types.Str(this.year + "");
    }

    @org.python.Method(__doc__ = "")
    public org.python.types.Str __month__() {
        return new org.python.types.Str(this.month + "");
    }

    @org.python.Method(__doc__ = "")
    public org.python.types.Str __day__() {
        return new org.python.types.Str(this.day + "");
    }

    @org.python.Method(__doc__ = "")
    private static org.python.Object __max__() {
        org.python.types.Int day = org.python.types.Int.getInt(31);
        org.python.types.Int month = org.python.types.Int.getInt(12);
        org.python.types.Int year = org.python.types.Int.getInt(9999);

        org.python.Object[] args = {year, month, day};
        return new Date(args, Collections.emptyMap());
    }

    @org.python.Method(__doc__ = "")
    private static org.python.Object __min__() {
        org.python.types.Int day = org.python.types.Int.getInt(1);
        org.python.types.Int month = org.python.types.Int.getInt(1);
        org.python.types.Int year = org.python.types.Int.getInt(1);

        org.python.Object[] args = {year, month, day};
        return new Date(args, Collections.emptyMap());
    }

    @org.python.Method(__doc__ = "")
    public static org.python.Object today() {
        java.time.LocalDateTime today = java.time.LocalDateTime.now();
        int y = today.getYear();
        int m = today.getMonthValue();
        int d = today.getDayOfMonth();

        org.python.Object[] args = {org.python.types.Int.getInt(y), org.python.types.Int.getInt(m), org.python.types.Int.getInt(d)};
        return new Date(args, Collections.emptyMap());
    }

    @org.python.Method(__doc__ = "")
    public org.python.Object ctime() {
        String[] monthList = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        double monthNum = ((org.python.types.Int) this.month).value;
        String monthStr = monthList[(int) monthNum - 1];

        String[] weekdayList = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        double weekdayNum = ((org.python.types.Int) weekday()).value;
        String weekdayStr = weekdayList[(int) weekdayNum];

        return new org.python.types.Str(weekdayStr + " " + monthStr + " " + this.day + " 00:00:00 " + this.year);
    }

    @org.python.Method(__doc__ = "")
    public org.python.Object weekday() {
        double y = ((org.python.types.Int) this.year).value;
        double m = ((org.python.types.Int) this.month).value;
        double d = ((org.python.types.Int) this.day).value;

        java.util.Date myCalendar = new java.util.GregorianCalendar((int) y, (int) m - 1, (int) d).getTime();
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(myCalendar);
        int day = c.get(java.util.Calendar.DAY_OF_WEEK);
        int[] convertToPython = {6, 0, 1, 2, 3, 4, 5};
        return org.python.types.Int.getInt(convertToPython[day - 1]);
    }
}
