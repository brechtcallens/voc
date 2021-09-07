package python;

@org.python.Module(
        __doc__ =
                "This module provides access to the mathematical functions " +
                "defined by the C standard." +
                "\n" +
                "These functions cannot be used with complex numbers; use " +
                "the functions of the same name from the cmath module if " + 
                "you require support for complex numbers. The distinction " +
                "between functions which support complex numbers and those " +
                "which don’t is made since most users do not want to learn " +
                "quite as much mathematics as required to understand " + 
                "complex numbers. Receiving an exception instead of a " +
                "complex result allows earlier detection of the unexpected " +
                "complex number used as a parameter, so that the programmer " +
                "can determine how and why it was generated in the first " +
                "place." +
                "\n" +
                "The following functions are provided by this module. " +
                "Except when explicitly noted otherwise, all return values " +
                "are floats."
)
public class math extends org.python.types.Module {
    @org.python.Method(
            __doc__ = "Return the absolute value of x.",
            args = {"x"}
    )
    public static org.python.types.Float fabs(org.python.types.Float x) {
        double absoluteValue = Math.abs(x.value);
        return new org.python.types.Float(absoluteValue);
    }
}
