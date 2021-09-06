package python;

@org.python.Module()
public class math extends org.python.types.Module {

    @org.python.Method(
        __doc__ = "Return the sine of x radians.\n",
        args = {"x"})
    public static org.python.types.Float sin(org.python.Object x)  {
        // Bool, Ints, Floats allowed
        if (x instanceof org.python.types.Int){
            return new org.python.types.Float(Math.sin(Double.valueOf(((org.python.types.Int) x.__int__()).value)));
        }
        if (x instanceof org.python.types.Float){
            return new org.python.types.Float(Math.sin(Double.valueOf(((org.python.types.Float) x.__float__()).value)));
        }
        if (x instanceof org.python.types.Bool){
            return new org.python.types.Float(Math.sin(Double.valueOf(((org.python.types.Float) x.__float__()).value)));
        }            
        
        throw new org.python.exceptions.TypeError("Wrong type. Must be float, int or bool. Not " + x.typeName());
    }
}