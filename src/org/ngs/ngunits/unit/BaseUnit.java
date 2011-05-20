package org.ngs.ngunits.unit;

import org.ngs.ngunits.UnitConverter;
import org.ngs.ngunits.Unit;
import org.ngs.ngunits.converter.AbstractUnitConverter;

/** */
public class BaseUnit extends AbstractUnit {

    private final String _symbol;
    
    public BaseUnit (UnitDelegate delegate, String symbol) {
        super(delegate);
        delegate.putSymbol(this, symbol);
        _symbol = symbol;
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
               ((that instanceof BaseUnit) && ((BaseUnit)that)._symbol.equals(this._symbol)); 
    }

    @Override
    public int hashCode() {
        return _symbol.hashCode();
    }
}
