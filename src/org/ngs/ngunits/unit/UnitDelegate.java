package org.ngs.ngunits.unit;

import org.ngs.ngunits.UnconvertibleException;
import org.ngs.ngunits.Unit;
import org.ngs.ngunits.UnitConverter;

/** */
public interface UnitDelegate 
{
    Unit one ();
    
    void putSymbol (Unit unit, String symbol);
    
    boolean compatible (Unit a, Unit b);
    
    Unit alternate (Unit unit, String symbol);
    
    Unit annotate (Unit unit, String annotation);
    
    Unit transform (Unit unit, UnitConverter operation);
    
    Unit multiply (Unit a, Unit b);
    
    Unit divide (Unit a, Unit b);
    
    Unit pow (Unit unit, int exponent);
    
    Unit root (Unit unit, int root);
    
    UnitConverter getConverter (Unit from, Unit to) throws UnconvertibleException;
}