package org.ngs.ngunits;

import org.ngs.ngunits.converter.MultiplyConverter;
import org.ngs.ngunits.converter.RationalConverter;

/** */
public enum Prefix {
    
    YOTTA(new MultiplyConverter(1E24)),
    ZETTA(new MultiplyConverter(1E21)),
    EXA(new RationalConverter(1000000000000000000L, 1)),
    PETA(new RationalConverter(1000000000000000L, 1)),
    TERA(new RationalConverter(1000000000000L, 1)),
    GIGA(new RationalConverter(1000000000L, 1)),
    MEGA(new RationalConverter(1000000L, 1)),
    KILO(new RationalConverter(1000L, 1)),
    HECTO(new RationalConverter(100L, 1)),
    DEKA(new RationalConverter(10L, 1)),
    DECI(new RationalConverter(1, 10L)),
    CENTI(new RationalConverter(1, 100L)),
    MILLI(new RationalConverter(1, 1000L)),
    MICRO(new RationalConverter(1, 1000000L)),
    NANO(new RationalConverter(1, 1000000000L)),
    PICO(new RationalConverter(1, 1000000000000L)),
    FEMTO(new RationalConverter(1, 1000000000000000L)),
    ATTO(new RationalConverter(1, 1000000000000000000L)),
    ZEPTO(new MultiplyConverter(1E-21)),
    YOCTO(new MultiplyConverter(1E-24));

    public final UnitConverter converter;

    Prefix (UnitConverter converter) {
        this.converter = converter;
    }
    
    public Unit transform (Unit unit) {
        return (Unit)unit.transform(converter);
    }
}
