/**
 * Unit-API - Units of Measurement API for Java (http://unitsofmeasurement.org)
 * Copyright (c) 2005-2010, Unit-API contributors, JScience and others
 * All rights reserved.
 *
 * See LICENSE.txt for details.
 */
package org.ngs.ngunits;

/**
 * Signals that a problem of some sort has occurred due to the impossibility of
 * constructing a converter between two units. For example, the mutiplication of
 * offset units are usually units not convertible to their {@link Unit#getSystemUnit
 * system unit}.
 *
 * @author <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author <a href="mailto:jcp@catmedia.us">Werner Keil</a>
 * @version 1.1.2 ($Revision: 125 $), $Date: 2010-09-28 10:16:43 -0400 (Tue, 28 Sep 2010) $
 */
public class UnconvertibleException extends RuntimeException {

    /** */
    private static final long serialVersionUID = -4623551240019830166L;
    
    /** */
    private final Unit _fromUnit;
    
    /** */
    private final Unit _toUnit;

    /**
     * Constructs a <code>UnconvertibleException</code> with the specified
     * detail message.
     *
     * @param message the detail message.
     */
    public UnconvertibleException (String message, Unit from, Unit to) {
        super(message);
        _fromUnit = from;
        _toUnit = to;
    }

    /**
     * Constructs a <code>UnconvertibleException</code> with the specified
     * detail message.
     *
     * @param cause the original exception.
     */
    public UnconvertibleException (Throwable cause, Unit from, Unit to) {
        super(cause);
        _fromUnit = from;
        _toUnit = to;
    }

    /** */
    public Unit getFromUnit () {
        return _fromUnit;
    }
    
    /** */
    public Unit getToUnit () {
        return _toUnit;
    }
}
