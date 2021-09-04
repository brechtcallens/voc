package python;

@org.python.Module()
public class math extends org.python.types.Module {

    @org.python.Method(
        __doc__ = "Return the square root of x.",
        args = {"x"})
    public static org.python.Object sqrt(org.python.Object x) {
        //Type checking the parameter
        if (!(x instanceof org.python.types.Int) && !(x instanceof org.python.types.Float)) {
            throw new org.python.exceptions.TypeError("must be real number, not " + x.typeName());
        }
        double arg = ((org.python.types.Float) x.__float__()).value;
        if (arg < 0) {
            throw new org.python.exceptions.ValueError("math domain error");
        }
        return new org.python.types.Float(Math.sqrt(arg));
    }
}
