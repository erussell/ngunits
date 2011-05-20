package org.ngs.ngunits.converter;

import org.ngs.ngunits.UnitConverter;
 
/**
 * <p> This class represents a converter multiplying numeric values by a 
 *     constant scaling factor (approximated as a <code>double</code>). 
 *     For exact scaling conversions {@link RationalConverter} is preferred.</p>
 *      
 * <p> Instances of this class are immutable.</p>
 */
public final class MultiplyConverter extends AbstractUnitConverter 
{
    private final double _factor;
    
    public MultiplyConverter (double factor) {
        if ((float)factor == 1.0)
            throw new IllegalArgumentException("Identity converter not allowed");
        _factor = factor;
    }
    
    public double getFactor() {
        return _factor;
    }
    
    @Override
    public UnitConverter concatenate (UnitConverter converter) {
        if (converter instanceof MultiplyConverter) {
            double factor = _factor * ((MultiplyConverter)converter)._factor;
            if (((float)factor) == 1.0f) {
                return IDENTITY;
            } else {
                return new MultiplyConverter(factor);
            }
        } else if (converter instanceof RationalConverter) {
            double factor = _factor
                    * ((RationalConverter) converter).getDividend()
                    / ((RationalConverter) converter).getDivisor();
            if (((float)factor) == 1.0f) {
                return IDENTITY;
            } else {
                return new MultiplyConverter(factor);
            }
        } else {
            return super.concatenate(converter);
        }
    }
    
    public double convert (double value) {
        return value * _factor;
    }
    
    public UnitConverter inverse () {
        return new MultiplyConverter(1 / _factor);
    }
}
