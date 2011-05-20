package org.ngs.ngunits.converter;

import org.ngs.ngunits.UnitConverter;


public final class AddConverter extends AbstractUnitConverter {

    /**
     * Holds the offset.
     */
    private final double _offset;

    /**
     * Creates an add converter with the specified offset.
     *
     * @param  offset the offset value.
     * @throws IllegalArgumentException if offset is zero (or close to zero).
     */
    public AddConverter (double offset) {
        if ((float)offset == 0.0)
            throw new IllegalArgumentException("Identity converter not allowed");
        _offset = offset;
    }
    
    public double getOffset() {
        return _offset;
    }

    @Override
    public boolean isLinear() {
        return false;
    }

    @Override
    public UnitConverter concatenate (UnitConverter converter) {
        if (converter instanceof AddConverter) {
            double offset = _offset + ((AddConverter)converter)._offset;
            if (((float)offset) == 0.0f) {
                return IDENTITY;
            } else {
                return new AddConverter(offset);
            }
        } else {
            return super.concatenate(converter);
        }
    }
    public double convert (double value) {
        return value + _offset;
    }
    
    public UnitConverter inverse () {
        return new AddConverter(- _offset);
    }
}
