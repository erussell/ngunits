package org.ngs.ngunits.format;

import java.io.StringReader;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import org.ngs.ngunits.NonSI;
import org.ngs.ngunits.Prefix;
import org.ngs.ngunits.SI;
import org.ngs.ngunits.Unit;
import org.ngs.ngunits.UnitConverter;
import org.ngs.ngunits.converter.AddConverter;
import org.ngs.ngunits.converter.LogConverter;
import org.ngs.ngunits.converter.MultiplyConverter;
import org.ngs.ngunits.converter.RationalConverter;
import org.ngs.ngunits.unit.AlternateUnit;
import org.ngs.ngunits.unit.BaseUnit;
import org.ngs.ngunits.unit.DefaultUnitDelegate;
import org.ngs.ngunits.unit.ProductUnit;
import org.ngs.ngunits.unit.TransformedUnit;

/**
 * <p> 
 * <h3>Grammar for Units in Extended Backus-Naur Form (EBNF)</h3>
 * <p>
 *   Note that the grammar has been left-factored to be suitable for use by a top-down 
 *   parser generator such as <a href="https://javacc.dev.java.net/">JavaCC</a>
 * </p>
 * <table width="90%" align="center">
 *   <tr>
 *     <th colspan="3" align="left">Lexical Entities:</th>
 *   </tr>
 *   <tr valign="top">
 *     <td>&lt;sign&gt;</td>
 *     <td>:=</td>
 *     <td>"+" | "-"</td>
 *   </tr>
 *   <tr valign="top">
 *     <td>&lt;digit&gt;</td>
 *     <td>:=</td>
 *     <td>"0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"</td>
 *   </tr>
 *   <tr valign="top">
 *     <td>&lt;superscript_digit&gt;</td>
 *     <td>:=</td>
 *     <td>"⁰" | "¹" | "²" | "³" | "⁴" | "⁵" | "⁶" | "⁷" | "⁸" | "⁹"</td>
 *   </tr>
 *   <tr valign="top">
 *     <td>&lt;integer&gt;</td>
 *     <td>:=</td>
 *     <td>(&lt;digit&gt;)+</td>
 *   </tr>
 *   <tr valign="top">
 *     <td>&lt;number&gt;</td>
 *     <td>:=</td>
 *     <td>(&lt;sign&gt;)? (&lt;digit&gt;)* (".")? (&lt;digit&gt;)+ (("e" | "E") (&lt;sign&gt;)? (&lt;digit&gt;)+)? </td>
 *   </tr>
 *   <tr valign="top">
 *     <td>&lt;exponent&gt;</td>
 *     <td>:=</td>
 *     <td>( "^" ( &lt;sign&gt; )? &lt;integer&gt; ) <br>| ( "^(" (&lt;sign&gt;)? &lt;integer&gt; ( "/" (&lt;sign&gt;)? &lt;integer&gt; )? ")" ) <br>| ( &lt;superscript_digit&gt; )+</td>
 *   </tr>
 *   <tr valign="top">
 *     <td>&lt;initial_char&gt;</td>
 *     <td>:=</td>
 *     <td>? Any Unicode character excluding the following: ASCII control & whitespace (&#92;u0000 - &#92;u0020), decimal digits '0'-'9', '(' (&#92;u0028), ')' (&#92;u0029), '*' (&#92;u002A), '+' (&#92;u002B), '-' (&#92;u002D), '.' (&#92;u002E), '/' (&#92;u005C), ':' (&#92;u003A), '^' (&#92;u005E), '²' (&#92;u00B2), '³' (&#92;u00B3), '·' (&#92;u00B7), '¹' (&#92;u00B9), '⁰' (&#92;u2070), '⁴' (&#92;u2074), '⁵' (&#92;u2075), '⁶' (&#92;u2076), '⁷' (&#92;u2077), '⁸' (&#92;u2078), '⁹' (&#92;u2079) ?</td>
 *   </tr>
 *   <tr valign="top">
 *     <td>&lt;unit_identifier&gt;</td>
 *     <td>:=</td>
 *     <td>&lt;initial_char&gt; ( &lt;initial_char&gt; | &lt;digit&gt; )*</td>
 *   </tr>
 *   <tr>
 *     <th colspan="3" align="left">Non-Terminals:</th>
 *   </tr>
 *   <tr valign="top">
 *     <td>&lt;unit_expr&gt;</td>
 *     <td>:=</td>
 *     <td>&lt;compound_expr&gt;</td>
 *   </tr>
 *   <tr valign="top">
 *     <td>&lt;compound_expr&gt;</td>
 *     <td>:=</td>
 *     <td>&lt;add_expr&gt; ( ":" &lt;add_expr&gt; )*</td>
 *   </tr>
 *   <tr valign="top">
 *     <td>&lt;add_expr&gt;</td>
 *     <td>:=</td>
 *     <td>( &lt;number&gt; &lt;sign&gt; )? &lt;mul_expr&gt; ( &lt;sign&gt; &lt;number&gt; )?</td>
 *   </tr>
 *   <tr valign="top">
 *     <td>&lt;mul_expr&gt;</td>
 *     <td>:=</td>
 *     <td>&lt;exponent_expr&gt; ( ( ( "*" | "·" ) &lt;exponent_expr&gt; ) | ( "/" &lt;exponent_expr&gt; ) )*</td>
 *   </tr>
 *   <tr valign="top">
 *     <td>&lt;exponent_expr&gt;</td>
 *     <td>:=</td>
 *     <td>( &lt;atomic_expr&gt; ( &lt;exponent&gt; )? ) <br>| (&lt;integer&gt; "^" &lt;atomic_expr&gt;) <br>| ( ( "log" ( &lt;integer&gt; )? ) | "ln" ) "(" &lt;add_expr&gt; ")" )</td>
 *   </tr>
 *   <tr valign="top">
 *     <td>&lt;atomic_expr&gt;</td>
 *     <td>:=</td>
 *     <td>&lt;number&gt; <br>| &lt;unit_identifier&gt; <br>| ( "(" &lt;add_expr&gt; ")" )</td>
 *   </tr>
 * </table>
 */
public class UnitFormat extends Format 
{
    //////////////////////////////////////////////////////
    // Class variables                                  //
    //////////////////////////////////////////////////////
    
    /** Default locale instance. If the default locale is changed after the class is
       initialized, this instance will no longer be used. */
    private static UnitFormat DEFAULT_INSTANCE = new UnitFormat(new SymbolMap(ResourceBundle.getBundle("org.ngs.ngunits.format.UnitFormat")));
    
    /** Operator precedence for the addition and subtraction operations */
    public static final int ADDITION_PRECEDENCE = 0;
    
    /** Operator precedence for the multiplication and division operations */
    public static final int PRODUCT_PRECEDENCE = ADDITION_PRECEDENCE + 2;
    
    /** Operator precedence for the exponentiation and logarithm operations */
    public static final int EXPONENT_PRECEDENCE = PRODUCT_PRECEDENCE + 2;
    
    /** 
     * Operator precedence for a unit identifier containing no mathematical 
     * operations (i.e., consisting exclusively of an identifier and possibly
     * a prefix). Defined to be <code>Integer.MAX_VALUE</code> so that no 
     * operator can have a higher precedence. 
     */
    public static final int NOOP_PRECEDENCE = Integer.MAX_VALUE;
    
    
    ///////////////////
    // Class methods //
    ///////////////////
    
    /** Returns the instance for the current default locale. */
    public static UnitFormat getInstance () {
        return getInstance(Locale.getDefault());
    }
    
    /** 
     * Returns an instance for the given locale.
     * @param locale
     */
    public static UnitFormat getInstance (Locale locale) {
        if (locale.equals(DEFAULT_INSTANCE.getSymbols().getLocale())) {
            return DEFAULT_INSTANCE;
        } else {
            return new UnitFormat(new SymbolMap(ResourceBundle.getBundle("org.ngs.ngunits.format.UnitFormat", locale)));
        }
    }
    
    /** Returns an instance for the given symbol map. */
    public static UnitFormat getInstance (SymbolMap symbols) {
        return new UnitFormat(symbols);
    }
    
    ////////////////////////
    // Instance variables //
    ////////////////////////
    
    /** 
     * The symbol map used by this instance to map between 
     * {@link org.ngs.ngunits.Unit Unit}s and <code>String</code>s, etc... 
     */
    protected SymbolMap _symbolMap;
    
    /**
     * A map that determines what instance of {@link ConverterFormat} will be used 
     * to format instances of each subclass of 
     * {@link org.ngs.ngunits.UnitConverter UnitConverter} <b>other 
     * than those that are already defined by the javax.measure.converter package</b>, 
     * which will always use the default format.
     */
    protected final Map<Class<? extends UnitConverter>,ConverterFormat> _converterFormats;
    
    /**
     * Flag that determines whether non-ASCII Unicode characters may be used in
     * formatting units. Has no effect on parsing; Unicode characters are always 
     * accepted as input for parsing.
     */
    protected boolean _asciiOnly;
    
    //////////////////
    // Constructors //
    //////////////////
    
    /**
     * Base constructor.
     */
    protected UnitFormat (SymbolMap symbolMap) { 
        _symbolMap = symbolMap;
        _converterFormats = new HashMap<Class<? extends UnitConverter>,ConverterFormat>();
        _converterFormats.put(AddConverter.class, new AddConverterFormat());
        _converterFormats.put(LogConverter.Log.class, new LogConverterFormat());
        _converterFormats.put(LogConverter.Exp.class, new ExpConverterFormat());
        _converterFormats.put(MultiplyConverter.class, new MultiplyConverterFormat());
        _converterFormats.put(RationalConverter.class, new RationalConverterFormat());
        _asciiOnly = false;
    }
    
    ////////////////////////
    // Instance methods //
    ////////////////////////
    
    /** 
     * Get the symbol map used by this instance to map between 
     * {@link org.ngs.ngunits.Unit Unit}s and <code>String</code>s, etc... 
     * @return SymbolMap the current symbol map
     */
    public SymbolMap getSymbols () {
        return _symbolMap;
    }
    
    /**
     * Set the symbol map used by this instance to map between 
     * {@link org.ngs.ngunits.Unit Unit}s and <code>String</code>s, etc... 
     * @param newSymbolMap SymbolMap the new symbol map
     */
    public void setSymbols (SymbolMap newSymbolMap) {
        _symbolMap = newSymbolMap;
    }
    
    /**
     * Get the value of the setting that determines whether non-ASCII Unicode 
     * characters may be used for formatting units. If the setting is true, only
     * ASCII characters will be used. The setting no effect on parsing; Unicode
     * characters are always accepted as input for parsing.
     * @return boolean
     */
    public boolean getAsciiOnly () {
        return _asciiOnly;
    }
    
    /**
     * Sets the value of the flag that determines whether non-ASCII Unicode 
     * characters may be used for formatting units. If the flag is set to 
     * </code>true</code>, only ASCII characters will be used. Has no effect
     * on parsing.
     * @param newAsciiOnly boolean the new flag setting
     */
    public void setAsciiOnly (boolean newAsciiOnly) {
        _asciiOnly = newAsciiOnly;
    }
    
    /**
     * Designate a {@link ConverterFormat} to be used to format instances of a
     * given subclass of {@link org.ngs.ngunits.UnitConverter UnitConverter}.
     * @param c the subclass of <code>ConverterFormat</code> to be formatted
     * @param format the formatter
     */
    public void addConverterFormat (Class<? extends UnitConverter> c, ConverterFormat format) {
        _converterFormats.put(c, format);
    }
    
    ////////////////
    // Formatting //
    ////////////////
    
    @Override
    public StringBuffer format (Object obj, StringBuffer buffer, FieldPosition pos) {
        formatInternal((Unit<?>)obj, buffer);
        return buffer;
    }
    
    /**
     * Format the given unit to the given StringBuffer, then return the operator
     * precedence of the outermost operator in the unit expression that was 
     * formatted. See {@link ConverterFormat} for the constants that define the
     * various precedence values.
     * @param unit the unit to be formatted
     * @param buffer the <code>StringBuffer</code> to be written to
     * @return the operator precedence of the outermost operator in the unit 
     *   expression that was output
     */
    protected int formatInternal (Unit<?> unit, StringBuffer buffer) {
        String symbol = _symbolMap.getSymbol(unit);
        if (symbol != null) {
            buffer.append(symbol);
            return NOOP_PRECEDENCE;
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
                if (start) {
                    buffer.append('1');
                }
                buffer.append('/');
                if (negativeExponentCount > 1) {
                    buffer.append('(');
                }
                start = true;
                for (Map.Entry<Unit,Integer> entry : components.entrySet()) {
                    if (entry.getValue().intValue() < 0) {
                        formatExponent(entry.getKey(), -entry.getValue().intValue(), !start, buffer);
                        start = false;
                    }
                }
                if (negativeExponentCount > 1) {
                    buffer.append(')');
                }
            }
            return PRODUCT_PRECEDENCE;
        } else if ((unit instanceof TransformedUnit) || unit.equals(SI.KILOGRAM)) {
            UnitConverter converter = null;
            boolean printSeparator = false;
            StringBuffer temp = new StringBuffer();
            int unitPrecedence = NOOP_PRECEDENCE;
            if (unit.equals(SI.KILOGRAM)) {
                // A special case because KILOGRAM is a BaseUnit instead of 
                // a transformed unit, even though it has a prefix.
                converter = Prefix.KILO.converter;
                unitPrecedence = formatInternal(SI.GRAM, temp);
                printSeparator = true;
            } else {
                TransformedUnit transformedUnit = (TransformedUnit)unit;
                Unit parentUnit = transformedUnit.getParent();
                converter = transformedUnit.toParent();
                if (parentUnit.equals(SI.KILOGRAM)) {
                    // More special-case hackery to work around gram/kilogram inconsistency
                    parentUnit = SI.GRAM;
                    converter = converter.concatenate(Prefix.KILO.converter);
                }
                unitPrecedence = formatInternal(parentUnit, temp);
                printSeparator = !parentUnit.equals(DefaultUnitDelegate.INSTANCE.one());
            }
            int result = formatConverter(converter, printSeparator, unitPrecedence, temp);
            buffer.append(temp);
            return result;
        } else if (unit instanceof AlternateUnit) {
            buffer.append(((AlternateUnit)unit).getSymbol());
            return NOOP_PRECEDENCE;
        } else if (unit instanceof BaseUnit) {
            buffer.append(((BaseUnit)unit).getSymbol());
            return NOOP_PRECEDENCE;
        } else {
            throw new IllegalArgumentException("Cannot format the given Object as a Unit (unsupported unit type "+unit.getClass().getName()+")");
        }
    }
    
    /**
     * Format the given unit raised to the given power to the given <code>StringBuffer</code>.
     * @param unit Unit the unit to be formatted
     * @param pow int the exponent
     * @param continued boolean <code>true</code> if the converter expression 
     *    should begin with an operator, otherwise <code>false</code>. This will 
     *    always be true unless the unit being modified is equal to Unit.ONE.
     * @param buffer StringBuffer the buffer to append to. No assumptions should
     *    be made about its content.
     */
    protected void formatExponent (Unit unit, int pow, boolean continued, StringBuffer buffer) {
        if (continued) {
            buffer.append(_asciiOnly ? '*' : '·');
        }
        StringBuffer temp = new StringBuffer();
        int unitPrecedence = formatInternal(unit, temp);
        if (unitPrecedence < PRODUCT_PRECEDENCE) {
            temp.insert(0, '(');
            temp.append(')');
        }
        buffer.append(temp);
        if (pow == 1) {
            // do nothing
        } else if ((pow > 1) && (!_asciiOnly)) {
            String powStr = Integer.toString(pow);
            for (int i = 0; i < powStr.length(); i += 1) {
                char c = powStr.charAt(i);
                switch (c) {
                    case '0': buffer.append('\u2070'); break;
                    case '1': buffer.append('\u00B9'); break;
                    case '2': buffer.append('\u00B2'); break;
                    case '3': buffer.append('\u00B3'); break;
                    case '4': buffer.append('\u2074'); break;
                    case '5': buffer.append('\u2075'); break;
                    case '6': buffer.append('\u2076'); break;
                    case '7': buffer.append('\u2077'); break;
                    case '8': buffer.append('\u2078'); break;
                    case '9': buffer.append('\u2079'); break;
                }
            }
        } else {
            buffer.append("^");
            buffer.append(Integer.toString(pow));
        }
    }
    
    /**
     * Formats the given converter to the given StringBuffer and returns the
     * operator precedence of the converter's mathematical operation. This is
     * the default implementation, which supports all built-in UnitConverter
     * implementations. Note that it recursively calls itself in the case of 
     * a {@link org.ngs.ngunits.converter.AbstractUnitConverter.Compound Compound} 
     * converter.
     * @param converter the converter to be formatted
     * @param continued <code>true</code> if the converter expression should 
     *    begin with an operator, otherwise <code>false</code>.
     * @param unitPrecedence the operator precedence of the operation expressed
     *    by the unit being modified by the given converter.
     * @param buffer the <code>StringBuffer</code> to append to.
     * @return the operator precedence of the given UnitConverter
     */
    public int formatConverter (UnitConverter converter, 
                                boolean continued, 
                                int unitPrecedence, 
                                StringBuffer buffer) {
        Prefix prefix = _symbolMap.getPrefix(converter);
        if ((prefix != null) && (unitPrecedence == NOOP_PRECEDENCE)) {
            buffer.insert(0, _symbolMap.getSymbol(prefix));
            return NOOP_PRECEDENCE;
        }
        List<? extends UnitConverter> compound = converter.getCompoundConverters();
        if (compound != null) {
            for (ListIterator<? extends UnitConverter> i = compound.listIterator(compound.size()); i.hasPrevious();) {
                unitPrecedence = formatConverter(i.previous(), continued, unitPrecedence, buffer);
            }
            return unitPrecedence;
        }
        ConverterFormat format = _converterFormats.get(converter.getClass());
        if (format != null) {
            return format.formatConverter(converter, continued, unitPrecedence, buffer, _asciiOnly);
        }
        throw new IllegalArgumentException("Unable to format converter: " + converter);
    }
    
    /////////////
    // Parsing //
    /////////////
    
    @Override
    public Object parseObject (String source, ParsePosition pos) {
        if ((source == null) || (source.length() == 0)) {
            pos.setErrorIndex(0);
            return null;
        }
        try {
            UnitParser parser = new UnitParser(_symbolMap, new StringReader(source));
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
    
    ///////////////////
    // Inner classes //
    ///////////////////
    
    
    private static class AddConverterFormat implements ConverterFormat 
    {
        AddConverterFormat() { }
        
        public int formatConverter (UnitConverter converter, 
                                    boolean continued, 
                                    int unitPrecedence, 
                                    StringBuffer buffer,
                                    boolean ascii) {
            if (unitPrecedence < ADDITION_PRECEDENCE) {
                buffer.insert(0, '(');
                buffer.append(')');
            }
            double offset = ((AddConverter)converter).getOffset();
            if (offset < 0) {
                buffer.append("-");
                offset = -offset;
            } else if (continued) {
                buffer.append("+");
            }
            long lOffset = (long)offset;
            if (lOffset == offset) {
                buffer.append(lOffset);
            } else {
                buffer.append(offset);
            }
            return ADDITION_PRECEDENCE;
        }
    }
    
    private static class LogConverterFormat implements ConverterFormat 
    {
        LogConverterFormat () { }
        
        public int formatConverter (UnitConverter converter, 
                                    boolean continued, 
                                    int unitPrecedence, 
                                    StringBuffer buffer,
                                    boolean ascii) {
            double base = ((LogConverter)converter).getBase();
            StringBuffer expr = new StringBuffer();
            if (base == StrictMath.E) {
                expr.append("ln");
            } else {
                expr.append("log");
                if (base != 10) {
                    expr.append((int)base);
                }
            }
            expr.append("(");
            buffer.insert(0, expr);
            buffer.append(")");
            return EXPONENT_PRECEDENCE;
        }
    }

    
    private static class ExpConverterFormat implements ConverterFormat 
    {
        ExpConverterFormat () { }
        
        public int formatConverter (UnitConverter converter, 
                                    boolean continued, 
                                    int unitPrecedence, 
                                    StringBuffer buffer,
                                    boolean ascii) {
            if (unitPrecedence < EXPONENT_PRECEDENCE) {
                buffer.insert(0, '(');
                buffer.append(')');
            }
            StringBuffer expr = new StringBuffer();
            double base = ((LogConverter)converter).getBase();
            if (base == StrictMath.E) {
                expr.append('e');
            } else {
                expr.append((int)base);
            }
            expr.append('^');
            buffer.insert(0, expr);
            return EXPONENT_PRECEDENCE;
        }
    }


    
    private static class MultiplyConverterFormat implements ConverterFormat 
    {
        MultiplyConverterFormat () { }
        
        public int formatConverter (UnitConverter converter, 
                                    boolean continued, 
                                    int unitPrecedence, 
                                    StringBuffer buffer,
                                    boolean ascii) {
            if (unitPrecedence < PRODUCT_PRECEDENCE) {
                buffer.insert(0, '(');
                buffer.append(')');
            }
            if (continued) {
                buffer.append(ascii ? '*' : '·');
            }
            double factor = ((MultiplyConverter)converter).getFactor();
            long lFactor = (long)factor;
            if (lFactor == factor) {
                buffer.append(lFactor);
            } else {
                buffer.append(factor);
            }
            return PRODUCT_PRECEDENCE;
        }
    }

    private static class RationalConverterFormat implements ConverterFormat 
    {
        RationalConverterFormat () { }
        
        public int formatConverter (UnitConverter converter, 
                                    boolean continued, 
                                    int unitPrecedence, 
                                    StringBuffer buffer,
                                    boolean ascii) {
            if (unitPrecedence < PRODUCT_PRECEDENCE) {
                buffer.insert(0, '(');
                buffer.append(')');
            }
            RationalConverter rationalConverter = (RationalConverter)converter;
            if (rationalConverter.getDividend() != 1) {
                if (continued) {
                    buffer.append(ascii ? '*' : '·');
                }
                buffer.append(rationalConverter.getDividend());
            }
            if (rationalConverter.getDivisor() != 1) {
                buffer.append('/');
                buffer.append(rationalConverter.getDivisor());
            }
            return PRODUCT_PRECEDENCE;
        }
    }
    
    /////////////
    // Testing //
    /////////////
    
    /** */
    private static void test (String input) {
        String formatted = "";
        boolean formats = false;
        try {
            Unit u = (Unit)DEFAULT_INSTANCE.parseObject(input);
            formatted = DEFAULT_INSTANCE.format(u);
            formats = formatted.equals(input);
        } catch (java.text.ParseException e) { }   
        System.out.println(input + " " + formats + (formats ? "" : ", " + formatted));
    }
    
    /** */
    public static void main (String[] args) {
        // Test operator precedence
        test("m·s+3");
        test("(m+2)·s+3");
        test("(km+2)·s+3");
        test("(m+2)·1000·s+3");
        test("(m+2)·1000·(s+3)");
        test("m·(s+3)");
        test("10^(m·5)");
        test("10^m·5");
        test("k");
        
        // Test locales
        System.out.println(Locale.getDefault());
        System.out.println("  "+DEFAULT_INSTANCE.format(NonSI.GALLON_LIQUID_US));
        System.out.println("  "+DEFAULT_INSTANCE.format(NonSI.OUNCE_LIQUID_US));
        System.out.println("  "+DEFAULT_INSTANCE.format(NonSI.GALLON_UK));
        System.out.println("  "+DEFAULT_INSTANCE.format(NonSI.OUNCE_LIQUID_UK));
        System.out.println(Locale.UK);
        UnitFormat test = UnitFormat.getInstance(Locale.UK);
        System.out.println("  "+test.format(NonSI.GALLON_LIQUID_US));
        System.out.println("  "+test.format(NonSI.OUNCE_LIQUID_US));
        System.out.println("  "+test.format(NonSI.GALLON_UK));
        System.out.println("  "+test.format(NonSI.OUNCE_LIQUID_UK));
    }
}

