package org.ngs.ngunits.unit;

import org.ngs.ngunits.UnitConverter;
import org.ngs.ngunits.Unit;
import org.ngs.ngunits.converter.AbstractUnitConverter;

/** */
public final class TransformedUnit extends AbstractUnit 
{
    private final Unit _parent;
    
    private final UnitConverter _toParent;
    
    TransformedUnit (UnitDelegate delegate, Unit parent, UnitConverter toParent) {
        super(delegate);
        if (toParent == AbstractUnitConverter.IDENTITY)
            throw new IllegalArgumentException("Identity not allowed");
        _parent = parent;
        _toParent = toParent;
    }
    
    public Unit getParent () {
        return _parent;
    }
    
    public UnitConverter toParent () {
        return _toParent;
    }
    
    public Unit getSystemUnit () {
        return _parent.getSystemUnit();
    }
    
    public UnitConverter toSystemUnit () {
        return ((AbstractUnit)_parent).toSystemUnit().concatenate(_toParent);
    }

    @Override
    public boolean equals (Object that) {
        return (this == that) ||
               ((that instanceof TransformedUnit) &&
                ((TransformedUnit)that)._parent.equals(_parent) &&
                ((TransformedUnit)that)._toParent.equals(_toParent));
    }

    @Override
    public int hashCode () {
        return _parent.hashCode() ^ _toParent.hashCode();
    }
}
