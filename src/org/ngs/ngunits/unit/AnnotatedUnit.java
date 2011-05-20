package org.ngs.ngunits.unit;

import org.ngs.ngunits.Unit;
import org.ngs.ngunits.UnitConverter;

/** */
public final class AnnotatedUnit extends AbstractUnit {
    
    private final Unit _parent;
    
    private final String _annotation;
    
    AnnotatedUnit (UnitDelegate delegate, Unit parent, String annotation) {
        super(delegate);
        _parent = parent;
        _annotation = annotation;
    }

    public final Unit getParent () {
        return _parent;
    }
    
    public String getAnnotation () {
        return _annotation;
    }
    
    public Unit getSystemUnit () {
        return _parent.getSystemUnit();
    }
    
    public UnitConverter toSystemUnit () {
        return ((AbstractUnit)_parent).toSystemUnit();
    }
    
    @Override
    public final String getSymbol() {
        return _parent.getSymbol();
    }

    @Override
    public boolean equals (Object that) {
        return (this == that) ||
               ((that instanceof AnnotatedUnit) && ((AnnotatedUnit)that)._parent.equals(this._parent)) ||
               (_parent.equals(that));
    }

    @Override
    public int hashCode() {
        return _parent.hashCode();
    }
}