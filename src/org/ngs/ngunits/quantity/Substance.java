/**
 * Unit-API - Units of Measurement API for Java (http://unitsofmeasurement.org)
 * Copyright (c) 2005-2010, Unit-API contributors, JScience and others
 * All rights reserved.
 *
 * See LICENSE.txt for details.
 */
package org.ngs.ngunits.quantity;


/**
 * Represents the number of elementary entities (molecules, for example) of a
 * substance. The metric system unit for this quantity is "mol" (mole).
 *
 * @author <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 1.2, $Date: 2010-09-28 10:16:43 -0400 (Tue, 28 Sep 2010) $
 * @deprecated renamed AmountOfSubstance
 */
public interface Substance extends Quantity<Substance> {
    // TODO name? SubstanceNumber, SubstanceElements, etc.?
}
