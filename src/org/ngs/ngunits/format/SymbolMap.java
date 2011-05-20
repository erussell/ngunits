package org.ngs.ngunits.format;

import org.ngs.ngunits.UnitConverter;
import org.ngs.ngunits.Unit;
import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import org.ngs.ngunits.Prefix;

/**
 * <p>
 * The SymbolMap class provides a set of mappings between 
 * {@link org.ngs.ngunits.Unit Units} and symbols (both ways),
 * between {@link org.ngs.ngunits.Prefix Prefix}es and symbols
 * (both ways), and from {@link org.ngs.ngunits.UnitConverter 
 * UnitConverter}s to {@link org.ngs.ngunits.Prefix Prefix}es 
 * (one way). No attempt is made to verify the uniqueness of the
 * mappings.
 * <p>
 * Mappings are read from a <code>ResourceBundle</code>, the keys
 * of which should consist of a fully-qualified class name, followed
 * by a dot ('.'), and then the name of a static field belonging
 * to that class. The values in the resource bundle should consist
 * of one or more labels separated by commas (,). If more than one
 * label is present, the first is used as a {@link SymbolMap#label(org.ngs.ngunits.Unit, String) label},
 * and the rest are used as {@link SymbolMap#alias(org.ngs.ngunits.Unit,String) alias}es.
 * Aliases map from String to Unit only, whereas labels map in both 
 * directions. A given unit may have any number of aliases, but may 
 * have only one label. 
 * <p>
 * TODO: Document the individual fields and methods
 */
public class SymbolMap {
    
    public static class Entry {
        
        public final Prefix prefix;
        public final Unit<?> unit;
        
        public Entry (Prefix prefix, Unit<?> unit) {
            this.prefix = prefix;
            this.unit = unit;
        }
    }
    
    /** */
    private Locale _locale;
    
    /** */
    private Map<String, Unit<?>> _symbolToUnit;

    /** */
    private Map<Unit<?>, String> _unitToSymbol;

    /** */
    private Map<String, Prefix> _symbolToPrefix;

    /** */
    private Map<Prefix, String> _prefixToSymbol;

    /** */
    private Map<UnitConverter, Prefix> _converterToPrefix;
    
    /** */
    public SymbolMap (ResourceBundle rb) {
        _locale = rb.getLocale();
        _symbolToUnit = new HashMap<String, Unit<?>>();
        _unitToSymbol = new HashMap<Unit<?>, String>();
        _symbolToPrefix = new HashMap<String, Prefix>();
        _prefixToSymbol = new HashMap<Prefix, String>();
        _converterToPrefix = new HashMap<UnitConverter, Prefix>();
        for (Enumeration<String> keys = rb.getKeys(); keys.hasMoreElements();) {
            String fqn = keys.nextElement();
            String[] symbols = rb.getString(fqn).split(",");
            int lastDot = fqn.lastIndexOf('.');
            String className = fqn.substring(0, lastDot);
            String fieldName = fqn.substring(lastDot+1, fqn.length());
            
            try {
                Class<?> c = Class.forName(className);
                Field field = c.getField(fieldName);
                Object value = field.get(null);
                if (value instanceof Unit) {
                    label((Unit)value, symbols[0]);
                    for (int i = 1; i < symbols.length; i += 1) {
                        alias((Unit)value, symbols[i]);
                    }
                } else if (value instanceof Prefix) {
                    label((Prefix)value, symbols[0]);
                } else {
                    throw new ClassCastException("unable to cast "+value+" to Unit or Prefix");
                }
            } catch (Exception e) {
                System.err.println("ERROR reading Unit names: " + e.toString());
                e.printStackTrace();
            }
        }
    }

    /** */
    public Locale getLocale () {
        return _locale;
    }

    /** */
    public void label (Unit<?> unit, String symbol) {
        _symbolToUnit.put(symbol, unit);
        if (!_unitToSymbol.containsKey(unit)) {
            _unitToSymbol.put(unit, symbol);
        }
    }
    
    /** */
    public void alias (Unit<?> unit, String symbol) { 
        _symbolToUnit.put(symbol, unit);
    }
    
    /** */
    public void label (Prefix prefix, String symbol) {
        _symbolToPrefix.put(symbol, prefix);
        _prefixToSymbol.put(prefix, symbol);
        _converterToPrefix.put(prefix.converter, prefix);
    }
    
    /** */
    public Entry lookup (String symbol) {
        Unit u = _symbolToUnit.get(symbol);
        if (u != null) {
            return new Entry(null, u);
        }
        for (Map.Entry<String,Prefix> entry : _symbolToPrefix.entrySet()) {
            if (symbol.startsWith(entry.getKey())) {
                u = _symbolToUnit.get(symbol.substring(entry.getKey().length()));
                if (u != null) {
                    return new Entry(entry.getValue(), u);
                }
            }
        }
        return null;
    }
    
    /** */
    public String getSymbol (Unit<?> unit) {
        return _unitToSymbol.get(unit);
    }
    
    /** */
    public Prefix getPrefix (UnitConverter converter) {
        return _converterToPrefix.get(converter);
    }
    
    /** */
    public String getSymbol (Prefix prefix) {
        return _prefixToSymbol.get(prefix);
    }
}
