package org.ngs.ngunits;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import org.ngs.ngunits.format.UnitFormat;
import org.ngs.ngunits.format.SymbolMap;


/** */
public final class UserDefined extends SystemOfUnits
{
    /** */
    protected static final class UserSymbolMap extends SymbolMap 
    {
        /** */
        private static final Pattern RESERVED_CHARS = Pattern.compile("[\u0000-\u001F\\(\\)\\*\\+\\-\\./0-9:\\^\u00B2\u00B3\u00B7\u00B9\u2070\u2074-\u2079]"); 
        
        /** */
        private Map<String, Unit<?>> _symbolToUnit;

        /** */
        private Map<Unit<?>, String> _unitToSymbol;
        
        /** */
        private boolean _definitionEnabled;
        
        /** */
        protected UserSymbolMap (ResourceBundle rb) {
            super(rb);
            _symbolToUnit = new HashMap<String, Unit<?>>();
            _unitToSymbol = new HashMap<Unit<?>, String>();
            _definitionEnabled = true;
        }
        
        @Override
        public Entry lookup (String symbol) {
            Entry result = super.lookup(symbol);
            if (result == null) {
                String validatedSymbol = makeValidUnitName(symbol);
                if (validatedSymbol == null) {
                    return null;
                } else if (!validatedSymbol.equals(symbol)) {
                    return lookup(validatedSymbol);
                } else {
                    Unit userUnit = _symbolToUnit.get(symbol);
                    if ((userUnit == null) && _definitionEnabled) {
                        userUnit = u(DELEGATE.one().alternate(symbol));
                        _symbolToUnit.put(symbol, userUnit);
                        _unitToSymbol.put(userUnit, symbol);
                    }
                    if (userUnit != null) {
                        return new SymbolMap.Entry(null, userUnit);
                    } else {
                        return null;
                    }
                }
            } else {
                return result;
            }
        }
        
        @Override
        public String getSymbol (Unit<?> unit) {
            String result = super.getSymbol(unit);
            if (result != null) {
                return result;
            } else {
                return _unitToSymbol.get(unit);
            }
        }

        /** */
        public boolean isDefinitionEnabled () {
            return _definitionEnabled;
        }
        
        /** */
        public void setDefinitionEnabled (boolean newValue) {
            _definitionEnabled = newValue;
        }
        
        
        /** */
        private static final String makeValidUnitName (String name) {
            StringBuilder newName = new StringBuilder(name.length());
            boolean hasValidNonWhitespaceChar = false;
            for (int i = 0; i < name.length(); i += 1) {
                if (!RESERVED_CHARS.matcher(name.substring(i,i+1)).matches()) {
                    char c = name.charAt(i);
                    if (Character.isWhitespace(c)) {
                        newName.append('_');
                    } else {
                        newName.append(c);
                        hasValidNonWhitespaceChar = true;
                    }
                }    
            }
            return hasValidNonWhitespaceChar ? newName.toString() : null;
        }
    }
    
    /** */
    public static UnitFormat FORMAT = UnitFormat.getInstance(new UserSymbolMap(ResourceBundle.getBundle("org.ngs.ngunits.format.UnitFormat")));
    
    /**
     * Default constructor (prevents this class from being instantiated).
     */
    private UserDefined () { }
    
    /** */
    public static boolean isDefinitionEnabled () { 
        return ((UserSymbolMap)FORMAT.getSymbols()).isDefinitionEnabled();
    }
    
    /** */
    public static void setDefinitionEnabled (boolean newValue) {
        ((UserSymbolMap)FORMAT.getSymbols()).setDefinitionEnabled(newValue);
    }
}
