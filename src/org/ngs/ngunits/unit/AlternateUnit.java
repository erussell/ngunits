package org.ngs.ngunits.unit;

import org.ngs.ngunits.Unit;
import org.ngs.ngunits.UnitConverter;
import org.ngs.ngunits.converter.AbstractUnitConverter;

/** */
public final class AlternateUnit extends AbstractUnit {
    
    private final Unit _parent;
    
    private final String _symbol;
    
    AlternateUnit(UnitDelegate delegate, Unit parent, String symbol) {
        super(delegate);
        if (parent.getSystemUnit() != parent) {
            throw new UnsupportedOperationException(parent + " is not a standard unit");
        }
        _parent = parent;
        _symbol = symbol;
        delegate.putSymbol(this, symbol);
    }

    public final Unit getParent () {
        return _parent;
    }
    
    public Unit getSystemUnit () {
        return this;
    }
    
    public UnitConverter toSystemUnit () {
        return AbstractUnitConverter.IDENTITY;
    }
    
    @Override
    public final String getSymbol() {
        return _symbol;
    }

    @Override
    public boolean equals (Object that) {
        return (this == that) ||
               ((that instanceof AlternateUnit) && ((AlternateUnit)that)._symbol.equals(_symbol));
    }

    @Override
    public int hashCode() {
        return _symbol.hashCode();
    }
}