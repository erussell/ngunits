package org.ngs.ngunits;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.ngs.ngunits.unit.DefaultUnitDelegate;
import org.ngs.ngunits.unit.UnitDelegate;

public abstract class SystemOfUnits
{   
    protected static final UnitDelegate DELEGATE = DefaultUnitDelegate.INSTANCE;
    
    private static final HashSet<Unit<?>> UNITS = new HashSet<Unit<?>>();
    
    public static Set<Unit<?>> getUnits () {
        return Collections.unmodifiableSet(UNITS);
    }
    
    protected static Unit u (Unit unit) {
        UNITS.add(unit);
        return unit;
    }
}
