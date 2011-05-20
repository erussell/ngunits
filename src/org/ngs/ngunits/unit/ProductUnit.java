package org.ngs.ngunits.unit;

import org.ngs.ngunits.UnconvertibleException;
import org.ngs.ngunits.UnitConverter;
import org.ngs.ngunits.Unit;
import java.util.Collections;
import java.util.Map;
import org.ngs.ngunits.converter.AbstractUnitConverter;

/** */
public final class ProductUnit extends AbstractUnit 
{
    private Map<Unit,Integer> _entries;
    
    ProductUnit (UnitDelegate delegate, Map<Unit,Integer> entries) {
        super(delegate);
        _entries = entries;
    }
    
    public Unit getSystemUnit () {
        if (hasOnlySystemUnits()) {
            return this;
        } else {
            Unit result = delegate.one();
            for (Map.Entry<Unit,Integer> entry : _entries.entrySet()) {
                result = result.multiply(entry.getKey().getSystemUnit().pow(entry.getValue().intValue()));
            }
            return result;
        }
    }
    
    public UnitConverter toSystemUnit () {
        if (hasOnlySystemUnits()) {
            return AbstractUnitConverter.IDENTITY;
        } else {
            UnitConverter result = AbstractUnitConverter.IDENTITY;
            for (Map.Entry<Unit,Integer> entry : _entries.entrySet()) {
                UnitConverter converter = ((AbstractUnit)entry.getKey()).toSystemUnit();
                if (!converter.isLinear()) {
                    throw new UnconvertibleException("unit is non-linear, cannot convert", 
                                                     entry.getKey(), 
                                                     entry.getKey().getSystemUnit());
                }
                int pow = entry.getValue().intValue();
                if (pow < 0) {
                    pow = -pow;
                    converter = converter.inverse();
                }
                for (int i = 0; i < pow; i += 1) {
                    result = result.concatenate(converter);
                }
            }
            return result;
        }
    }
    
    @Override
    public Map<Unit, Integer> getProductUnits () {
        return Collections.unmodifiableMap(_entries);
    }

    @Override
    public boolean equals (Object that) {
        return (this == that) ||
               ((that instanceof ProductUnit) && ((ProductUnit)that)._entries.equals(_entries));
    }

    @Override
    public int hashCode() {
        return _entries.hashCode();
    }
    
    private boolean hasOnlySystemUnits () {
        for (Unit u : _entries.keySet()) {
            if (u != u.getSystemUnit()) {
                return false;
            }
        }
        return true;
    }
}
