package org.ngs.ngunits.converter;

import org.ngs.ngunits.UnitConverter;


/**
 * <p> This class represents a converter multiplying numeric values by an
 *     exact scaling factor (represented as the quotient of two 
 *     <code>long</code> numbers).</p>
 *  
 * <p> Instances of this class are immutable.</p>
 */
public final class RationalConverter extends AbstractUnitConverter 
{
    private static long gcd (long m, long n) {
        if (n == 0L) {
            return m;
        } else {
            return gcd(n, m % n);
        }
    }
    
    private final long _dividend;

    private final long _divisor;

    public RationalConverter (long dividend, long divisor) {
        if (divisor < 0) {
            throw new IllegalArgumentException("Negative divisor");
        }
        if (dividend == divisor) { 
            throw new IllegalArgumentException("Identity converter not allowed");
        }
        _dividend = dividend;
        _divisor = divisor;
    }

    public long getDividend() {
        return _dividend;
    }

    public long getDivisor() {
        return _divisor;
    }
    
    @Override
    public UnitConverter concatenate (UnitConverter converter) {
        if (converter instanceof RationalConverter) {
            RationalConverter that = (RationalConverter)converter;
            long dividend = this._dividend * that._dividend;
            long divisor = this._divisor * that._divisor;
            double dividendF = ((double)this._dividend) * that._dividend;
            double divisorF = ((double)this._divisor) * that._divisor;
            if ((dividend != dividendF) || (divisor != divisorF)) {
                // Long overflows.
                double factor = dividendF / divisorF;
                if (((float)factor) == 1.0f) {
                    return IDENTITY;
                } else {
                    return new MultiplyConverter(factor);
                }
            }
            long gcd = gcd(dividend, divisor);
            dividend /= gcd;
            divisor /= gcd;
            if (dividend == divisor) {
                return IDENTITY;
            } else {
                return new RationalConverter(dividend, divisor);
            }
        } else if (converter instanceof MultiplyConverter) {
            return converter.concatenate(this);
        } else {
            return super.concatenate(converter);
        }
    }
    
    public double convert (double value) {
        return value * _dividend  / _divisor;
    }
    
    public UnitConverter inverse () {
        if (_dividend < 0) {
            return new RationalConverter(-_divisor, -_dividend);
        } else {
            return new RationalConverter(_divisor, _dividend);
        }
    }    
}
