package org.ngs.ngunits.unit;

import org.ngs.ngunits.UnconvertibleException;
import org.ngs.ngunits.Unit;
import org.ngs.ngunits.UnitConverter;
import java.util.Map;
import org.ngs.ngunits.converter.AddConverter;
import org.ngs.ngunits.converter.MultiplyConverter;
import org.ngs.ngunits.converter.RationalConverter;

/** */
public abstract class AbstractUnit implements Unit
{
    public final UnitDelegate delegate;
    
    protected AbstractUnit (UnitDelegate delegate) {
        this.delegate = delegate;
    }
    
    public String getSymbol () {
        return null;
    }
    
    public Map<Unit, Integer> getProductUnits ()  {
        return null;
    }
    
    public boolean isCompatible (Unit that) {
        return delegate.compatible(this, that);
    }
    
    public Unit asType (Class type) {
        return this;
    }
    
    public UnitConverter getConverterTo (Unit that) throws UnconvertibleException {
        return getConverterToAny(that);
    }
    
    public UnitConverter getConverterToAny (Unit that) throws UnconvertibleException  {
        return delegate.getConverter(this, that);
    }
    
    public Unit alternate (String symbol) {
        return delegate.alternate(this, symbol);
    }
    
    public Unit annotate (String annotation) {
        return delegate.annotate(this, annotation);
    }
    
    public Unit transform (UnitConverter operation) {
        return delegate.transform(this, operation);
    }

    public Unit add (double offset) {
        return transform(new AddConverter(offset));
    }   

    public Unit multiply (double factor) {
        if (factor == 1.0) {
            return this;
        } else if ((StrictMath.floor(factor) == factor) && (Math.abs(factor) < Integer.MAX_VALUE)) {
            return transform(new RationalConverter((long)factor, 1));
        } else {
            return transform(new MultiplyConverter(factor));
        }
    }
    
    public Unit multiply (Unit that) {
        return delegate.multiply(this, that);
    }

    public Unit inverse () {
        return delegate.divide(delegate.one(), this);
    }

    public Unit divide (double divisor) {
        if (divisor == 1.0) {
            return this;
        } else if ((StrictMath.floor(divisor) == divisor) && (Math.abs(divisor) < Integer.MAX_VALUE)) {
            return transform(new RationalConverter(1, (long)divisor));
        } else {
            return transform(new MultiplyConverter(1.0 / divisor));
        }
    }

    public Unit divide (Unit that) {
        return delegate.divide(this, that);
    }

    public Unit<?> root (int n) {
        if (n > 0) {
            return delegate.root(this, n);
        } else if (n == 0) {
            throw new ArithmeticException("Root's order of zero");
        } else { // n < 0
            return delegate.one().divide(this.root(-n));
        }
    }

    public Unit<?> pow (int n) {
        if (n > 0) {
            return this.multiply(this.pow(n - 1));
        } else if (n == 0) {
            return delegate.one();
        } else { // n < 0
            return delegate.one().divide(this.pow(-n));
        }
    }
}
