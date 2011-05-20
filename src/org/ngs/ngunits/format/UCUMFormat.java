package org.ngs.ngunits.format;

import java.io.StringReader;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.Map;
import java.util.ResourceBundle;
import org.ngs.ngunits.Prefix;
import org.ngs.ngunits.SI;
import org.ngs.ngunits.UCUM;
import org.ngs.ngunits.Unit;
import org.ngs.ngunits.UnitConverter;
import org.ngs.ngunits.converter.AbstractUnitConverter;
import org.ngs.ngunits.converter.MultiplyConverter;
import org.ngs.ngunits.converter.RationalConverter;
import org.ngs.ngunits.unit.AnnotatedUnit;
import org.ngs.ngunits.unit.DefaultUnitDelegate;
import org.ngs.ngunits.unit.ProductUnit;
import org.ngs.ngunits.unit.TransformedUnit;


/**
 * <p> 
 * This class provides the interface for formatting and parsing 
 * {@link org.ngs.ngunits.Unit units} according to the 
 * <a href="http://aurora.regenstrief.org/~ucum/ucum.html">Uniform 
 * Code for Units of Measure</a>.
 * <p>
 * As of revision 1.16, the BNF in the UCUM standard contains an
 * <a href="http://aurora.regenstrief.org/ucum/ticket/4">error</a>. 
 * I've attempted to work around the problem by modifying the BNF 
 * productions for &lt;Term&gt;. Once the error in the standard is
 * corrected, it may be necessary to modify the productions in the
 * UCUMParser.jj file to conform to the standard.
 * <p>
 * Currently, unit annotations (which appear between curly braces 
 * '{' and '}') are ignored in input and are not output during 
 * formatting.
 * 
 * @see <a href="http://aurora.regenstrief.org/~ucum/ucum.html">UCUM</a>
 */
public abstract class UCUMFormat extends Format {
    
    ///////////////////
    // Inner classes //
    ///////////////////
    
    /**
     * The Print format is used to output units according to the "print" 
     * column in the UCUM standard. Because "print" symbols in UCUM are 
     * not unique, this class of UCUMFormat may not be used for parsing, 
     * only for formatting.
     */
    private static class Print extends UCUMFormat {
        
        public Print (SymbolMap symbols) {
            super(symbols);
        }
        
        @Override
        public Object parseObject (String source, ParsePosition pos) {
            throw new UnsupportedOperationException("The print format is for pretty-printing of units only. Parsing is not supported.");
        }
    }
    
    /**
     * The Parsing format outputs formats and parses units according to the 
     * "c/s" or "c/i" column in the UCUM standard, depending on which SymbolMap 
     * is passed to its constructor. 
     */
    private static class Parsing extends UCUMFormat {
        
        private final boolean _caseSensitive;
        
        public Parsing (SymbolMap symbols, boolean caseSensitive) {
            super(symbols);
            _caseSensitive = caseSensitive;
        }
        
        @Override
        public Object parseObject (String source, ParsePosition pos) {
            if (!_caseSensitive) {
                source = source.toUpperCase();
            }
            return parseInternal(source, pos);
        }
    }
    
    ////////////////////////////////////////////////////////
    // Class variables                                    //
    //                                                    //
    // The symbol maps are filled in a static initializer //
    // at the bottom of this file.                         //
    ////////////////////////////////////////////////////////
    
    /** Symbols from the "print" column in the UCUM standard */
    private static final SymbolMap PRINT_SYMBOLS = new SymbolMap(ResourceBundle.getBundle("org.ngs.ngunits.format.UCUM_Print"));
    
    /** Symbols from the "c/s" column in the UCUM standard */
    private static final SymbolMap CASE_SENSITIVE_SYMBOLS = new SymbolMap(ResourceBundle.getBundle("org.ngs.ngunits.format.UCUM_CS"));
    
    /** Symbols from the "c/i" column in the UCUM standard */
    private static final SymbolMap CASE_INSENSITIVE_SYMBOLS = new SymbolMap(ResourceBundle.getBundle("org.ngs.ngunits.format.UCUM_CI"));
    
    /** Instance for formatting using "print" symbols */
    private static final UCUMFormat PRINT_INSTANCE = new UCUMFormat.Print(PRINT_SYMBOLS);
    
    /** Instance for formatting and parsing using "c/s" symbols */
    private static final UCUMFormat CS_INSTANCE = new UCUMFormat.Parsing(CASE_SENSITIVE_SYMBOLS, true);
    
    /** Instance for formatting and parsing using "c/i" symbols */
    private static final UCUMFormat CI_INSTANCE = new UCUMFormat.Parsing(CASE_INSENSITIVE_SYMBOLS, false);
    
    ///////////////////
    // Class methods //
    ///////////////////
    
    /** Returns the instance for formatting using "print" symbols */
    public static UCUMFormat getPrintInstance () {
        return PRINT_INSTANCE;
    }
    
    /** Returns the instance for formatting and parsing using "c/s" symbols */
    public static UCUMFormat getCaseSensitiveInstance () {
        return CS_INSTANCE;
    }
    
    /** Returns the instance for formatting and parsing using "c/i" symbols */
    public static UCUMFormat getCaseInsensitiveInstance () {
        return CI_INSTANCE;
    }
    
    ////////////////////////
    // Instance variables //
    ////////////////////////
    
    /** The symbol map used by this instance to map between {@link org.ngs.ngunits.Unit Unit}s and <code>String</code>s. */
    final SymbolMap _symbolMap;
    
    //////////////////
    // Constructors //
    //////////////////
    
    /**
     * Base constructor.
     */
    UCUMFormat (SymbolMap symbolMap) { 
        _symbolMap = symbolMap;
    }
    
    ////////////////
    // Formatting //
    ////////////////
    
    @Override
    public StringBuffer format (Object obj, StringBuffer buffer, FieldPosition pos) {
        formatInternal((Unit<?>)obj, buffer);
        return buffer;
    }
    
    private void formatInternal (Unit<?> unit, StringBuffer buffer) {
        String symbol = _symbolMap.getSymbol(unit);
        if (symbol != null) {
            buffer.append(symbol);
        } else if (unit instanceof ProductUnit) {
            Map<Unit,Integer> components = unit.getProductUnits();
            int negativeExponentCount = 0;
            boolean start = true;
            // Write positive exponents first...
            for (Map.Entry<Unit,Integer> entry : components.entrySet()) {
                if (entry.getValue().intValue() < 0) {
                    negativeExponentCount += 1;
                } else {
                    formatExponent(entry.getKey(), entry.getValue().intValue(), !start, buffer);
                    start = false;
                }
            }
            // ..then write negative exponents (if any).
            if (negativeExponentCount > 0) {
                for (Map.Entry<Unit,Integer> entry : components.entrySet()) {
                    if (entry.getValue().intValue() < 0) {
                        formatExponent(entry.getKey(), entry.getValue().intValue(), !start, buffer);
                        start = false;
                    }
                }
            }
        } else if ((unit instanceof TransformedUnit) || unit.equals(SI.KILOGRAM)) {
            StringBuffer temp = new StringBuffer();
            UnitConverter converter;
            boolean printSeparator;
            if (unit.equals(SI.KILOGRAM)) {
                // A special case because KILOGRAM is a BaseUnit instead of 
                // a transformed unit, for compatability with existing SI 
                // unit system.
                temp = format(UCUM.GRAM, temp, null);
                converter = Prefix.KILO.converter;
                printSeparator = true;
            } else {
                TransformedUnit transformedUnit = (TransformedUnit)unit;
                Unit<?> parentUnit = transformedUnit.getParent();
                converter = transformedUnit.toParent();
                if (parentUnit.equals(SI.KILOGRAM)) {
                    // More special-case hackery to work around gram/kilogram incosistency
                    parentUnit = UCUM.GRAM;
                    converter = converter.concatenate(Prefix.KILO.converter);
                }
                temp = format(parentUnit, temp, null);
                printSeparator = !parentUnit.equals(DefaultUnitDelegate.INSTANCE.one());
            }
            formatConverter(converter, printSeparator, temp);
            buffer.append(temp);
        } else {
            throw new IllegalArgumentException("Cannot format the given Object as UCUM units (unsupported unit type "+unit.getClass().getName()+")");
        }
        if (unit instanceof AnnotatedUnit) {
            buffer.append('{');
            buffer.append(((AnnotatedUnit)unit).getAnnotation());
            buffer.append('}');
        }
    }
    
    protected void formatExponent (Unit<?> unit, int pow, boolean continued, StringBuffer buffer) {
        if (pow < 0) {
            buffer.append('/');
        } else if (continued) {
            buffer.append('.');
        }
        StringBuffer temp = new StringBuffer();
        formatInternal(unit, temp);
        if ((temp.indexOf(".") >= 0) || (temp.indexOf("/") >= 0)) {
            temp.insert(0, '(');
            temp.append(')');
        }
        buffer.append(temp);
        if (Math.abs(pow) == 1) {
            // do nothing
        } else {
            buffer.append(Integer.toString(Math.abs(pow)));
        }
    }
    
    /**
     * Formats the given converter to the given StringBuffer. This is similar
     * to what {@link ConverterFormat} does, but there's no need to worry about
     * operator precedence here, since UCUM only supports multiplication, 
     * division, and exponentiation and expressions are always evaluated left-
     * to-right.
     * @param converter the converter to be formatted
     * @param continued <code>true</code> if the converter expression should 
     *    begin with an operator, otherwise <code>false</code>. This will always 
     *    be true unless the unit being modified is equal to Unit.ONE.
     * @param buffer the <code>StringBuffer</code> to append to. Contains the
     *    already-formatted unit being modified by the given converter.
     */
    protected void formatConverter (UnitConverter converter, boolean continued, StringBuffer buffer) {
        boolean unitIsExpression = ((buffer.indexOf(".") >= 0) || (buffer.indexOf("/") >= 0));
        Prefix prefix = _symbolMap.getPrefix(converter);
        if ((prefix != null) && (!unitIsExpression)) {
            buffer.insert(0, _symbolMap.getSymbol(prefix));
        } else if (converter == AbstractUnitConverter.IDENTITY) {
            // do nothing
        } else if (converter instanceof MultiplyConverter) {
            if (unitIsExpression) {
                buffer.insert(0, '(');
                buffer.append(')');
            }
            MultiplyConverter multiplyConverter = (MultiplyConverter)converter;
            double factor = multiplyConverter.getFactor();
            long lFactor = (long)factor;
            if ((lFactor != factor) || (lFactor < -9007199254740992L) || (lFactor > 9007199254740992L)) {
                throw new IllegalArgumentException("Only integer factors are supported in UCUM");
            }
            if (continued) {
                buffer.append('.');
            }
            buffer.append(lFactor);
        } else if (converter instanceof RationalConverter) {
            if (unitIsExpression) {
                buffer.insert(0, '(');
                buffer.append(')');
            }
            RationalConverter rationalConverter = (RationalConverter)converter;
            if (rationalConverter.getDividend() != 1) {
                if (continued) {
                    buffer.append('.');
                }
                buffer.append(rationalConverter.getDividend());
            }
            if (rationalConverter.getDivisor() != 1) {
                buffer.append('/');
                buffer.append(rationalConverter.getDivisor());
            }
        } else {
            throw new IllegalArgumentException("Unable to format units in UCUM (unsupported UnitConverter "+converter+")");
        }
    }
    

    ////////////////
    // Parsing //
    ////////////////
    

    protected Unit parseInternal (String source, ParsePosition pos) {
        if ((source == null) || (source.length() == 0)) {
            pos.setErrorIndex(0);
            return null;
        }
        UCUMParser parser = new UCUMParser(_symbolMap, new StringReader(source));
        try {
            Unit result = parser.parseUnit();
            pos.setIndex(source.length());
            return result;
        } catch (ParseException e) {
            // According to the general contract of java.text.Format, if an
            // error occurs, we set the error index of our ParsePosition, 
            // and return null
            if (e.currentToken != null) {
                pos.setErrorIndex(e.currentToken.endColumn);
            } else {
                pos.setErrorIndex(0);
            }
            return null;
        } catch (TokenMgrError e) {
            pos.setErrorIndex(0);
            return null;
        }
    }
    
    /////////////
    // Testing //
    /////////////
    
    /** */
    private static void test (String input) {
        boolean formats = false;
        String formatted = "";
        try {
            Unit u = (Unit)CS_INSTANCE.parseObject(input);
            formatted = CS_INSTANCE.format(u);
            formats = formatted.equals(input);
        } catch (java.text.ParseException e) {
            System.err.println("error parsing " + input);
            e.printStackTrace();
        }
        System.out.println(input + " " + formats + (formats ? "" : " ("+formatted+")"));
    }
    
    public static void main (String[] args) {
        // A bunch of definition units from the UCUM standard to use for testing 
        String[] strs = { "rad2", "s-1", "kg.m/s2", "N/m2", "N.m", "J/s", "C/s", 
                          "J/C", "C/V", "V/A", "Ohm-1", "V.s", "Wb/m2", "Wb/A", 
                          "cd.sr", "lm/m2", "s-1", "J/kg", "J/kg", "deg", 
                          "[pi].rad/360", "deg/60", "'/60", "dm3", "m2", 
                          "a_j/12", "a_g/12", "kg", "[e].V", "Mm", "m/s", "J.s", 
                          "J/K", "F/m", "m3.kg-1/s2", "m/s2", "[c].a_j", 
                          "g.[g]", "[lb_av].[g]", "cm-1{salt}", "cm/s2", "g.cm/s2", 
                          "dyn.cm", "dyn.s/cm2", "cm2/s", "/[pi].A/m", "Oe.cm", 
                          "cd/cm2", "cd/cm2/[pi]", "lx", "Bq", "C/kg", "erg/g", 
                          "RAD", "cm", "[nmi_i]/h", "[in_i]2", "[ft_i]2", 
                          "[yd_i]2", "[in_i]3", "[ft_i]3", "[yd_i]3", "[in_i]3", 
                          "[ft_i]3", "[pi]/4.[mil_i]2", "m/3937", "[ft_us]/12", 
                          "[ch_us]/100", "[rch_us]/100", "[rd_us]2", "[rd_us]2", 
                          "[mi_us]2", "[mi_us]2", "cm", "[ch_br]/100", 
                          "[nmi_br]/h", "[yd_br]2", "[in_i]3", "[gal_us]/4", 
                          "[qt_us]/2", "[pt_us]/4", "[gil_us]/4", "[foz_us]/8", 
                          "[fdr_us]/60", "[ft_i]3", "[in_i]3", "[bu_us]/8", 
                          "[bu_us]/4", "[pk_us]/8", "[dqt_us]/2", "[foz_us]/2", 
                          "[tbs_us]/3", "[gal_br]/4", "[qt_br]/2", "[pt_br]/4", 
                          "[gil_br]/5", "[foz_br]/8", "[fdr_br]/60", "mg", 
                          "[lb_av]/16", "[oz_av]/16", "[in_i]/12", "[lne]/6", 
                          "[pied]/12", "[pouce]/12", "[ligne]/6", "[didot]", 
                          "[Btu_th]", "[ft_i].[lbf_av]/s", "kPa", "kPa", "/m", 
                          "/[in_i]", "mm/[pi]", "ml/12", "mL/min/kg", "g/dl", 
                          "mol/s", "umol/min", "m3", "nm", "fm2", "kgf/cm2", 
                          "S", "[lbf_av]/[in_i]2", "[pi].rad", "[pi].sr", "/24", 
                          "/s", "1/s", "" };
        for (int i = 0; i < strs.length; i += 1) {
            //test(strs[i]);
            test("cm-1{salt}");
            //System.out.println(CS_INSTANCE.format(CS_INSTANCE.parseObject(test[i])));
        }
    }
}
