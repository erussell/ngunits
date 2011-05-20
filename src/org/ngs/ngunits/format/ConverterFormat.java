package org.ngs.ngunits.format;

import org.ngs.ngunits.UnitConverter;


/**
 * ConverterFormat is the interface for an object used for formatting a
 * UnitConverter to a StringBuffer. It is provided as a mechanism of extending
 * UnitFormat so that subclasses of UnitConverter defined outside of the 
 * javax.measure package (such as org.jscience.economics.money.Currency.Converter) 
 * can customize the way they're formatted without needing to subclass 
 * {@link UnitFormat}. {@link UnitFormat} itself implements ConverterFormat, 
 * and its implementation provides formatting for all of the built-in 
 * UnitConverters defined in the javax.measure.converter package.
 */
public interface ConverterFormat {
    
    /**
     * Formats the given converter to the given StringBuffer and returns the
     * operator precedence of the converter's mathematical operation.
     * @param converter the converter to be formatted
     * @param continued <code>true</code> if the converter expression should 
     *    begin with an operator, otherwise <code>false</code>. This will always 
     *    be true unless the unit being modified is equal to Unit.ONE.
     * @param unitPrecedence the operator precedence of the operation expressed
     *    by the unit being modified by the given converter. If this precedence
     *    is lower than the precedence of the converter's operation, then the
     *    buffer should be enclosed in parentheses before the converter's 
     *    operation is appended.
     * @param buffer the <code>StringBuffer</code> to append to. Contains the
     *    already-formatted unit being modified by the given converter.
     * @return the operator precedence of the given UnitConverter
     */
    public int formatConverter (UnitConverter converter, 
                                boolean continued, 
                                int unitPrecedence, 
                                StringBuffer buffer,
                                boolean ascii);
}
