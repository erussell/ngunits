/**
 * Unit-API - Units of Measurement API for Java (http://unitsofmeasurement.org)
 * Copyright (c) 2005-2010, Unit-API contributors, JScience and others
 * All rights reserved.
 *
 * See LICENSE.txt for details.
 */
package org.ngs.ngunits.quantity;


/**
 * This interface represents the moment of a force. The system unit for this
 * quantity is "N.m" (Newton-Metre).
 *
 * <p>
 * Note: The Newton-metre ("N.m") is also a way of expressing a Joule (unit of
 * energy). However, torque is not energy. So, to avoid confusion, we will use
 * the units "N.m" for torque and not "J". This distinction occurs due to the
 * scalar nature of energy and the vector nature of torque.
 * </p>
 *
 * @author <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 1.2.1, $Date: 2010-09-28 10:16:43 -0400 (Tue, 28 Sep 2010) $
 */
public interface Torque extends Quantity<Torque> {
}
