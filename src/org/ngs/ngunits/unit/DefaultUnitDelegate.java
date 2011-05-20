package org.ngs.ngunits.unit;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.ngs.ngunits.UnconvertibleException;
import org.ngs.ngunits.Unit;
import org.ngs.ngunits.UnitConverter;
import org.ngs.ngunits.converter.AbstractUnitConverter;

/** */
public class DefaultUnitDelegate implements UnitDelegate
{
    
    private static class BaseUnitException extends Exception {
        
        public BaseUnitException () {
            super();
        }
    }
    
    public static final UnitDelegate INSTANCE = new DefaultUnitDelegate();
    
    private final AbstractUnit _one;
    
    private final Map<String,Unit> _symbols;
    
    public DefaultUnitDelegate () {
        _one = new ProductUnit(this, new HashMap<Unit,Integer>());
        _symbols = new HashMap<String,Unit>();
    }
    
    public AbstractUnit one () {
        return _one;
    }

    public synchronized void putSymbol (Unit unit, String symbol) {
        if (_symbols.containsKey(symbol)) {
            Unit unit1 = _symbols.get(symbol);
            if ((!(unit instanceof AlternateUnit)) ||
                (!(unit1 instanceof AlternateUnit)) ||
                (!((AlternateUnit)unit).getParent().equals(((AlternateUnit)unit1).getParent()))) {
                new IllegalArgumentException("Symbol " + symbol + " is already associated to a different unit");
            }
        }
        _symbols.put(symbol, unit);
    }
    
    public Set<Map.Entry<String,Unit>> getSymbols () {
        return Collections.unmodifiableSet(_symbols.entrySet());
    }
    
    public boolean compatible (Unit a, Unit b) {
        try {
            return getBaseUnit(a).equals(getBaseUnit(b));
        } catch (BaseUnitException e) {
            return false;
        }
    }   
    
    public Unit annotate (Unit unit, String annotation) {
        return new AnnotatedUnit(this, unit, annotation);
    }
    
    public Unit alternate (Unit unit, String symbol) {
        return new org.ngs.ngunits.unit.AlternateUnit(this, unit, symbol);
    }
    
    public Unit transform (Unit unit, UnitConverter operation) {
        if (unit instanceof TransformedUnit) {
            operation = ((TransformedUnit)unit).toParent().concatenate(operation);
            unit = ((TransformedUnit)unit).getParent();
        }
        if (operation.isIdentity()) {
            return unit;
        } else {
            return new TransformedUnit(this, unit, operation);
        }
    }
    
    public Unit multiply (Unit a, Unit b) {
        return getProductInstance(productEntries(a), productEntries(b));
    }
    
    public Unit divide (Unit a, Unit b) {
        Map<Unit,Integer> quotientEntries = new HashMap<Unit,Integer>();
        for (Map.Entry<Unit,Integer> entry : productEntries(b).entrySet()) {
            quotientEntries.put(entry.getKey(), Integer.valueOf(-entry.getValue().intValue()));
        }
        return getProductInstance(productEntries(a), quotientEntries);
    }
    
    public Unit pow (Unit unit, int exponent) {
        Map<Unit,Integer> entries = new HashMap<Unit,Integer>();
        Map<Unit,Integer> products = unit.getProductUnits();
        if (products != null) {
            for (Map.Entry<Unit,Integer> entry : products.entrySet()) {
                entries.put(entry.getKey(), Integer.valueOf(entry.getValue().intValue() * exponent));
            }
        } else {
            entries.put(unit, Integer.valueOf(exponent));
        }
        return new ProductUnit(this, entries);
    }
    
    public Unit root (Unit unit, int root) {
        Map<Unit,Integer> products = unit.getProductUnits();
        if (products != null) {
            Map<Unit,Integer> entries = new HashMap<Unit,Integer>();
            for (Map.Entry<Unit,Integer> entry : products.entrySet()) {
                if ((entry.getValue().intValue() % root) != 0) {
                    throw new ArithmeticException("fractional powers not supported");
                }
                entries.put(entry.getKey(), Integer.valueOf(entry.getValue().intValue() / root));
            }
            return new ProductUnit(this, entries);
        } else {
            throw new ArithmeticException("fractional powers not supported");
        }
    }
    
    public UnitConverter getConverter (Unit from, Unit to) throws UnconvertibleException {
        if (from.equals(to)) {
            return AbstractUnitConverter.IDENTITY;
        }
        Unit fromSystemUnit = from.getSystemUnit();
        Unit toSystemUnit = to.getSystemUnit();
        if (fromSystemUnit.equals(toSystemUnit)) {
            return ((AbstractUnit)to).toSystemUnit().inverse().concatenate(((AbstractUnit)from).toSystemUnit());
        }
        try {
            Unit fromBaseUnit = getBaseUnit(from);
            Unit toBaseUnit = getBaseUnit(to);
            if (fromBaseUnit.equals(toBaseUnit)) {
                UnitConverter fromConverter = ((AbstractUnit)from).toSystemUnit().concatenate(getBaseConverter(fromSystemUnit));
                UnitConverter toConverter = ((AbstractUnit)to).toSystemUnit().concatenate(getBaseConverter(toSystemUnit));
                return toConverter.inverse().concatenate(fromConverter);
            }
        } catch (BaseUnitException e) { }
        throw new UnconvertibleException("unable to convert", from, to);
    }
    
    private Unit getBaseUnit (Unit unit) throws BaseUnitException {
        if (unit instanceof BaseUnit) {
            return unit;
        } else if (unit instanceof AlternateUnit) {
            return getBaseUnit(((AlternateUnit)unit).getParent());
        } else if (unit instanceof TransformedUnit) {
            return getBaseUnit(((TransformedUnit)unit).getParent());
        } else if (unit instanceof ProductUnit) {
            Unit result = _one;
            for (Map.Entry<Unit,Integer> entry : ((Map<Unit,Integer>)unit.getProductUnits()).entrySet()) {
                result = result.multiply(getBaseUnit(entry.getKey()).pow(entry.getValue().intValue()));
            }
            return result;
        } else {
            throw new BaseUnitException();
        }
    }
    
    private UnitConverter getBaseConverter (Unit baseUnit) throws BaseUnitException {
        UnitConverter result = AbstractUnitConverter.IDENTITY;
        Map<Unit,Integer> entries = baseUnit.getProductUnits();
        if (entries != null) {
            for (Map.Entry<Unit,Integer> entry : entries.entrySet()) {
                UnitConverter converter = getBaseConverter(entry.getKey());
                if (!converter.isLinear()) {
                    throw new BaseUnitException();
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
        }
        return result;
    }
    
    private Map<Unit,Integer> productEntries (Unit unit) {
        Map<Unit,Integer> result = unit.getProductUnits();
        if (result == null) {
            result = new HashMap<Unit,Integer>();
            result.put(unit, Integer.valueOf(1));
        }
        return result;
    }
    
    private Unit getProductInstance (Map<Unit,Integer> a, Map<Unit,Integer> b) {
        Map<Unit,Integer> entries = new HashMap<Unit,Integer>();
        for (Map.Entry<Unit,Integer> entry : a.entrySet()) {
            if (b.containsKey(entry.getKey())) {
                int exponent = entry.getValue().intValue() + b.get(entry.getKey()).intValue();
                if (exponent != 0) {
                    entries.put(entry.getKey(), Integer.valueOf(exponent));
                }
            } else {
                entries.put(entry.getKey(), entry.getValue());
            }
        }
        for (Map.Entry<Unit,Integer> entry : b.entrySet()) {
            if (!a.containsKey(entry.getKey())) {
                entries.put(entry.getKey(), entry.getValue());
            }
        }
        if (entries.size() == 0) {
            return _one;
        } else if ((entries.size() == 1) && (entries.values().iterator().next().intValue() == 1)) {
            return entries.keySet().iterator().next();
        } else {
            return new ProductUnit(this, entries);
        }
    }
}
