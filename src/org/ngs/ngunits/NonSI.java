package org.ngs.ngunits;

import org.ngs.ngunits.converter.LogConverter;
import org.ngs.ngunits.converter.RationalConverter;
import org.ngs.ngunits.quantity.*;

/**
 * This class contains units that are not part of the International
 * System of Units, that is, they are outside the SI, but are important
 * and widely used.
 */
public final class NonSI extends SystemOfUnits
{
    public static final NonSI INSTANCE = new NonSI();
    
    /**
     * Holds the standard gravity constant: 9.80665 m/s² exact.
     */
    private static final int STANDARD_GRAVITY_DIVIDEND = 980665;
    private static final int STANDARD_GRAVITY_DIVISOR  = 100000;

    /**
     * Holds the international foot: 0.3048 m exact.
     */
    private static final int INTERNATIONAL_FOOT_DIVIDEND =  3048;
    private static final int INTERNATIONAL_FOOT_DIViSOR  = 10000;

    /**
     * Holds the avoirdupois pound: 0.45359237 kg exact
     */
    private static final int AVOIRDUPOIS_POUND_DIVIDEND =  45359237; 
    private static final int AVOIRDUPOIS_POUND_DIVISOR  = 100000000; 

    /**
     * Holds the Avogadro constant.
     */
    private static final double AVOGADRO_CONSTANT = 6.02214199e23; // (1/mol).

    /**
     * Holds the electric charge of one electron.
     */
    private static final double ELEMENTARY_CHARGE = 1.602176462e-19; // (C).
    
    ///////////////////
    // Dimensionless //
    ///////////////////
    
    /**
     * A dimensionless unit equals to <code>0.01</code> 
     * (standard name <code>%</code>).
     */
    public static final Unit<Dimensionless> PERCENT = u(DELEGATE.one().divide(100));
    
    /** */
    public static final Unit<Dimensionless> PER_MIL = u(PERCENT.divide(10));

    /**
     * A logarithmic unit used to describe a ratio
     * (standard name <code>dB</code>).
     */
    public static final Unit<Dimensionless> DECIBEL = u(DELEGATE.one().transform(new LogConverter.Exp(10).concatenate(new RationalConverter(1, 10))));

    /////////////////////////
    // Amount of substance //
    /////////////////////////

    /**
     * A unit of amount of substance equals to one atom
     * (standard name <code>atom</code>).
     */
    public static final Unit<AmountOfSubstance> ATOM = SI.MOLE.divide(AVOGADRO_CONSTANT);

    ////////////
    // Length //
    ////////////

    /**
     * A unit of length equal to <code>0.3048 m</code> 
     * (standard name <code>ft</code>).
     */
    public static final Unit<Length> FOOT = u(SI.METRE.multiply(INTERNATIONAL_FOOT_DIVIDEND).divide(INTERNATIONAL_FOOT_DIViSOR));

    /**
     * A unit of length equal to <code>1200/3937 m</code> 
     * (standard name <code>foot_survey_us</code>).
     * See also: <a href="http://www.sizes.com/units/foot.htm">foot</a>
     */
    public static final Unit<Length> FOOT_SURVEY_US = u(SI.METRE.multiply(1200).divide(3937));

    /**
     * A unit of length equal to <code>0.9144 m</code>
     * (standard name <code>yd</code>).
     */
    public static final Unit<Length> YARD = u(FOOT.multiply(3));

    /**
     * A unit of length equal to <code>0.0254 m</code> 
     * (standard name <code>in</code>).
     */
    public static final Unit<Length> INCH = u(FOOT.divide(12));

    /**
     * A unit of length equal to <code>1609.344 m</code>
     * (standard name <code>mi</code>).
     */
    public static final Unit<Length> MILE = u(SI.METRE.multiply(1609344).divide(1000));

    /**
     * A unit of length equal to <code>1852.0 m</code>
     * (standard name <code>nmi</code>).
     */
    public static final Unit<Length> NAUTICAL_MILE = u(SI.METRE.multiply(1852));

    /**
     * A unit of length equal to <code>1E-10 m</code>
     * (standard name <code>Å</code>).
     */
    public static final Unit<Length> ANGSTROM = u(SI.METRE.divide(10000000000L));

    /**
     * A unit of length equal to the average distance from the center of the
     * Earth to the center of the Sun (standard name <code>ua</code>).
     */
    public static final Unit<Length> ASTRONOMICAL_UNIT = u(SI.METRE.multiply(149597870691.0));

    /**
     * A unit of length equal to the distance that light travels in one year
     * through a vacuum (standard name <code>ly</code>).
     */
    public static final Unit<Length> LIGHT_YEAR = u(SI.METRE.multiply(9.460528405e15));

    /**
     * A unit of length equal to the distance at which a star would appear to
     * shift its position by one arcsecond over the course the time
     * (about 3 months) in which the Earth moves a distance of
     * {@link #ASTRONOMICAL_UNIT} in the direction perpendicular to the
     * direction to the star (standard name <code>pc</code>).
     */
    public static final Unit<Length> PARSEC = u(SI.METRE.multiply(30856770e9));

    /**
     * A unit of length equal to <code>0.013837 {@link #INCH}</code> exactly
     * (standard name <code>pt</code>).
     * @see     #PIXEL
     */
    public static final Unit<Length> POINT = u(INCH.multiply(13837).divide(1000000));

    /**
     * A unit of length equal to <code>1/72 {@link #INCH}</code>
     * (standard name <code>pixel</code>).
     * It is the American point rounded to an even 1/72 inch.
     * @see     #POINT
     */
    public static final Unit<Length> PIXEL = u(INCH.divide(72));

    /**
     * Equivalent {@link #PIXEL}
     */
    public static final Unit<Length> COMPUTER_POINT = PIXEL;
    

    /** */
    public static final Unit<Length> FOOT_MODIFIED_AMERICAN = u(SI.METRE.multiply(12.0004584).divide(39.37));
    
    /** */
    public static final Unit<Length> FOOT_CLARKE = u(SI.METRE.multiply(12.0).divide(39.370432));
    
    /** */
    public static final Unit<Length> FOOT_INDIAN = u(SI.METRE.multiply(12.0).divide(39.370141));
    
    /** */
    public static final Unit<Length> LINK = u(SI.METRE.multiply(7.92).divide(39.370432));
    
    /** */
    public static final Unit<Length> LINK_BENOIT = u(SI.METRE.multiply(7.92).divide(39.370113));
    
    /** */
    public static final Unit<Length> LINK_SEARS = u(SI.METRE.multiply(7.92).divide(39.370147));
    
    /** */
    public static final Unit<Length> CHAIN_BENOIT = u(LINK_BENOIT.multiply(100));
    
    /** */
    public static final Unit<Length> CHAIN_SEARS = u(LINK_SEARS.multiply(100));
    
    /** */
    public static final Unit<Length> YARD_SEARS = u(SI.METRE.multiply(36).divide(39.370147));
    
    /** */
    public static final Unit<Length> YARD_INDIAN = u(FOOT_INDIAN.multiply(3));
    
    /** */
    public static final Unit<Length> FATHOM = u(YARD.multiply(2));
    
    //////////////
    // Duration //
    //////////////

    /**
     * A unit of duration equal to <code>60 s</code>
     * (standard name <code>min</code>).
     */
    public static final Unit<Time> MINUTE = u(SI.SECOND.multiply(60));

    /**
     * A unit of duration equal to <code>60 {@link #MINUTE}</code>
     * (standard name <code>h</code>).
     */
    public static final Unit<Time> HOUR = u(MINUTE.multiply(60));

    /**
     * A unit of duration equal to <code>24 {@link #HOUR}</code>
     * (standard name <code>d</code>).
     */
    public static final Unit<Time> DAY = u(HOUR.multiply(24));

    /**
     * A unit of duration equal to <code>7 {@link #DAY}</code>
     * (standard name <code>week</code>).
     */
    public static final Unit<Time> WEEK = u(DAY.multiply(7));

    /**
     * A unit of duration equal to 365 days, 5 hours, 49 minutes,
     * and 12 seconds (standard name <code>year</code>).
     */
    public static final Unit<Time> YEAR = u(SI.SECOND.multiply(31556952));

    /**
     * A unit of duration equal to one twelfth of a year
     * (standard name <code>month</code>).
     */
    public static final Unit<Time> MONTH = u(YEAR.divide(12));

    /**
     * A unit of duration equal to the time required for a complete rotation of
     * the earth in reference to any star or to the vernal equinox at the
     * meridian, equal to 23 hours, 56 minutes, 4.09 seconds
     * (standard name <code>day_sidereal</code>).
     */
    public static final Unit<Time> DAY_SIDEREAL = u(SI.SECOND.multiply(86164.09));

    /**
     * A unit of duration equal to one complete revolution of the
     * earth about the sun, relative to the fixed stars, or 365 days, 6 hours,
     * 9 minutes, 9.54 seconds (standard name <code>year_sidereal</code>).
     */
    public static final Unit<Time> YEAR_SIDEREAL = u(SI.SECOND.multiply(31558149.54));

    /**
     * A unit of duration equal to <code>365 {@link #DAY}</code>
     * (standard name <code>year_calendar</code>).
     */
    public static final Unit<Time> YEAR_CALENDAR = u(DAY.multiply(365));
    
    /** */
    public static final Unit<Time> MILLISECOND = u(SI.SECOND.divide(1000));

    //////////
    // Mass //
    //////////

    /**
     * A unit of mass equal to 1/12 the mass of the carbon-12 atom
     * (standard name <code>u</code>).
     */
    public static final Unit<Mass> ATOMIC_MASS = u(SI.KILOGRAM.multiply(1e-3 / AVOGADRO_CONSTANT));

    /**
     * A unit of mass equal to the mass of the electron
     * (standard name <code>me</code>).
     */
    public static final Unit<Mass> ELECTRON_MASS = u(SI.KILOGRAM.multiply(9.10938188e-31));

    /**
     * A unit of mass equal to <code>453.59237 grams</code> (avoirdupois pound,
     * standard name <code>lb</code>).
     */
    public static final Unit<Mass> POUND = u(SI.KILOGRAM.multiply(AVOIRDUPOIS_POUND_DIVIDEND).divide(AVOIRDUPOIS_POUND_DIVISOR));

    /**
     * A unit of mass equal to <code>1 / 16 {@link #POUND}</code>
     * (standard name <code>oz</code>).
     */
    public static final Unit<Mass> OUNCE = u(POUND.divide(16));

    /**
     * A unit of mass equal to <code>2000 {@link #POUND}</code> (short ton, 
     * standard name <code>ton_us</code>).
     */
    public static final Unit<Mass> TON_US = u(POUND.multiply(2000));

    /**
     * A unit of mass equal to <code>2240 {@link #POUND}</code> (long ton,
     * standard name <code>ton_uk</code>).
     */
    public static final Unit<Mass> TON_UK = u(POUND.multiply(2240));

    /**
     * A unit of mass equal to <code>1000 kg</code> (metric ton,
     * standard name <code>t</code>).
     */
    public static final Unit<Mass> METRIC_TON = u(SI.KILOGRAM.multiply(1000));

    /////////////////////
    // Electric charge //
    /////////////////////

    /**
     * A unit of electric charge equal to the charge on one electron
     * (standard name <code>e</code>).
     */
    public static final Unit<ElectricCharge> E = u(SI.COULOMB.multiply(ELEMENTARY_CHARGE));

    /**
     * A unit of electric charge equal to equal to the product of Avogadro's
     * number (see {@link SI#MOLE}) and the charge (1 e) on a single electron
     * (standard name <code>Fd</code>).
     */
    public static final Unit<ElectricCharge> FARADAY = u(SI.COULOMB.multiply(ELEMENTARY_CHARGE * AVOGADRO_CONSTANT)); // e/mol

    /**
     * A unit of electric charge which exerts a force of one dyne on an equal
     * charge at a distance of one centimeter
     * (standard name <code>Fr</code>).
     */
    public static final Unit<ElectricCharge> FRANKLIN = u(SI.COULOMB.multiply(3.3356e-10));

    /////////////////
    // Temperature //
    /////////////////

    /**
     * A unit of temperature equal to <code>5/9 °K</code>
     * (standard name <code>°R</code>).
     */
    public static final Unit<Temperature> RANKINE = u(SI.KELVIN.multiply(5).divide(9));

    /**
     * A unit of temperature equal to degree Rankine minus 
     * <code>459.67 °R</code> (standard name <code>°F</code>).
     * @see    #RANKINE
     */
    public static final Unit<Temperature> FAHRENHEIT = u(RANKINE.add(459.67));

    ///////////
    // Angle //
    ///////////

    /**
     * A unit of angle equal to a full circle or <code>2<i>&pi;</i> 
     * {@link SI#RADIAN}</code> (standard name <code>rev</code>).
     */
    public static final Unit<Angle> REVOLUTION = u(SI.RADIAN.multiply(2.0 * Math.PI));

    /**
     * A unit of angle equal to <code>1/360 {@link #REVOLUTION}</code>
     * (standard name <code>°</code>).
     */
    public static final Unit<Angle> DEGREE_ANGLE = u(REVOLUTION.divide(360));

    /**
     * A unit of angle equal to <code>1/60 {@link #DEGREE_ANGLE}</code>
     * (standard name <code>′</code>).
     */
    public static final Unit<Angle> MINUTE_ANGLE = u(DEGREE_ANGLE.divide(60));

    /**
     *  A unit of angle equal to <code>1/60 {@link #MINUTE_ANGLE}</code>
     * (standard name <code>"</code>).
     */
    public static final Unit<Angle> SECOND_ANGLE = u(MINUTE_ANGLE.divide(60));

    /**
     * A unit of angle equal to <code>0.01 {@link SI#RADIAN}</code>
     * (standard name <code>centiradian</code>).
     */
    public static final Unit<Angle> CENTIRADIAN = u(SI.RADIAN.divide(100));

    /**
     * A unit of angle measure equal to <code>1/400 {@link #REVOLUTION}</code>
     * (standard name <code>grade</code>).
     */
    public static final Unit<Angle> GRADE = u(REVOLUTION.divide(400));

    //////////////
    // Velocity //
    //////////////

    /**
     * A unit of velocity expressing the number of international {@link 
     * #MILE miles} per {@link #HOUR hour} (abbreviation <code>mph</code>).
     */
    public static final Unit<Velocity> MILES_PER_HOUR = u(MILE.divide(HOUR));

    /**
     * A unit of velocity expressing the number of {@link SI#KILOMETRE} per 
     * {@link #HOUR hour}.
     */
    public static final Unit<Velocity> KILOMETRES_PER_HOUR = u(SI.KILOMETRE.divide(HOUR));

    /**
     * Equivalent to {@link #KILOMETRES_PER_HOUR}.
     */
    public static final Unit<Velocity> KILOMETERS_PER_HOUR = KILOMETRES_PER_HOUR;

    /**
     * A unit of velocity expressing the number of  {@link #NAUTICAL_MILE
     * nautical miles} per {@link #HOUR hour} (abbreviation <code>kn</code>).
     */
    public static final Unit<Velocity> KNOT = u(NAUTICAL_MILE.divide(HOUR));

    /**
     * A unit of velocity to express the speed of an aircraft relative to
     * the speed of sound (standard name <code>Mach</code>).
     */
    public static final Unit<Velocity> MACH = u(SI.METRES_PER_SECOND.multiply(331.6));

    /**
     * A unit of velocity relative to the speed of light
     * (standard name <code>c</code>).
     */
    public static final Unit<Velocity> C = u(SI.METRES_PER_SECOND.multiply(299792458));

    //////////////////
    // Acceleration //
    //////////////////

    /**
     * A unit of acceleration equal to the gravity at the earth's surface
     * (standard name <code>grav</code>).
     */
    public static final Unit<Acceleration> G = u(SI.METRES_PER_SQUARE_SECOND.multiply(STANDARD_GRAVITY_DIVIDEND).divide(STANDARD_GRAVITY_DIVISOR));

    //////////
    // Area //
    //////////

    /**
     * A unit of area equal to <code>100 m²</code>
     * (standard name <code>a</code>).
     */
    public static final Unit<Area> ARE = u(SI.SQUARE_METRE.multiply(100));

    /**
     * A unit of area equal to <code>100 {@link #ARE}</code>
     * (standard name <code>ha</code>).
     */
    public static final Unit<Area> HECTARE = u(ARE.multiply(100)); // Exact.
    
    /** */
    public static final Unit<Area> SQUARE_KILOMETER = u(SI.KILOMETER.pow(2));
    
    /** */
    public static final Unit<Area> SQUARE_FOOT = u(FOOT.pow(2));
    
    /** */
    public static final Unit<Area> ACRE = u(SQUARE_FOOT.multiply(43560));

    /////////////////
    // Data Amount //
    /////////////////

    /**
     * A unit of data amount equal to <code>8 {@link SI#BIT}</code>
     * (BinarY TErm, standard name <code>byte</code>).
     */
    public static final Unit<Information> BYTE = u(SI.BIT.multiply(8));

    /**
     * Equivalent {@link #BYTE}
     */
    public static final Unit<Information> OCTET = BYTE;


    //////////////////////
    // Electric current //
    //////////////////////

    /**
     * A unit of electric charge equal to the centimeter-gram-second
     * electromagnetic unit of magnetomotive force, equal to <code>10/4
     * &pi;ampere-turn</code> (standard name <code>Gi</code>).
     */
    public static final Unit<ElectricCurrent> GILBERT = u(SI.AMPERE.multiply(10.0 / (4.0 * Math.PI)));

    ////////////
    // Energy //
    ////////////

    /**
     * A unit of energy equal to <code>1E-7 J</code>
     * (standard name <code>erg</code>).
     */
    public static final Unit<Energy> ERG = u(SI.JOULE.divide(10000000));

    /**
     * A unit of energy equal to one electron-volt (standard name 
     * <code>eV</code>, also recognized <code>keV, MeV, GeV</code>).
     */
    public static final Unit<Energy> ELECTRON_VOLT = u(SI.JOULE.multiply(ELEMENTARY_CHARGE));

    /////////////////
    // Illuminance //
    /////////////////

    /**
     * A unit of illuminance equal to <code>1E4 Lx</code>
     * (standard name <code>La</code>).
     */
    public static final Unit<Illuminance> LAMBERT = u(SI.LUX.multiply(10000));

    ///////////////////
    // Magnetic Flux //
    ///////////////////

    /**
     * A unit of magnetic flux equal <code>1E-8 Wb</code>
     * (standard name <code>Mx</code>).
     */
    public static final Unit<MagneticFlux> MAXWELL = u(SI.WEBER.divide(100000000));

    ///////////////////////////
    // Magnetic Flux Density //
    ///////////////////////////

    /**
     * A unit of magnetic flux density equal <code>1000 A/m</code>
     * (standard name <code>G</code>).
     */
    public static final Unit<MagneticFluxDensity> GAUSS = u(SI.TESLA.divide(10000));

    ///////////
    // Force //
    ///////////

    /**
     * A unit of force equal to <code>1E-5 N</code>
     * (standard name <code>dyn</code>).
     */
    public static final Unit<Force> DYNE = u(SI.NEWTON.divide(100000));

    /**
     * A unit of force equal to <code>9.80665 N</code>
     * (standard name <code>kgf</code>).
     */
    public static final Unit<Force> KILOGRAM_FORCE = u(SI.NEWTON.multiply(STANDARD_GRAVITY_DIVIDEND).divide(STANDARD_GRAVITY_DIVISOR));

    /**
     * A unit of force equal to <code>{@link #POUND}·{@link #G}</code>
     * (standard name <code>lbf</code>).
     */
    public static final Unit<Force> POUND_FORCE = u(SI.NEWTON.multiply(1L * AVOIRDUPOIS_POUND_DIVIDEND * STANDARD_GRAVITY_DIVIDEND).divide(1L * AVOIRDUPOIS_POUND_DIVISOR * STANDARD_GRAVITY_DIVISOR));

    ///////////
    // Power //
    ///////////

    /**
     * A unit of power equal to the power required to raise a mass of 75
     * kilograms at a velocity of 1 meter per second (metric,
     * standard name <code>hp</code>).
     */
    public static final Unit<Power> HORSEPOWER = u(SI.WATT.multiply(735.499));

    //////////////
    // Pressure //
    //////////////

    /**
     * A unit of pressure equal to the average pressure of the Earth's
     * atmosphere at sea level (standard name <code>atm</code>).
     */
    public static final Unit<Pressure> ATMOSPHERE = u(SI.PASCAL.multiply(101325));

    /**
     * A unit of pressure equal to <code>100 kPa</code>
     * (standard name <code>bar</code>).
     */
    public static final Unit<Pressure> BAR = u(SI.PASCAL.multiply(100000));

    /**
     * A unit of pressure equal to the pressure exerted at the Earth's
     * surface by a column of mercury 1 millimeter high
     * (standard name <code>mmHg</code>).
     */
    public static final Unit<Pressure> MILLIMETER_OF_MERCURY =u(SI.PASCAL.multiply(133.322));

    /**
     * A unit of pressure equal to the pressure exerted at the Earth's
     * surface by a column of mercury 1 inch high
     * (standard name <code>inHg</code>).
     */
    public static final Unit<Pressure> INCH_OF_MERCURY = u(SI.PASCAL.multiply(3386.388));
    
    /////////////////////////////
    // Radiation dose absorbed //
    /////////////////////////////

    /**
     * A unit of radiation dose absorbed equal to a dose of 0.01 joule of
     * energy per kilogram of mass (J/kg) (standard name <code>rd</code>).
     */
    public static final Unit<RadiationDoseAbsorbed> RAD = u(SI.GRAY.divide(100));

    /**
     * A unit of radiation dose effective equal to <code>0.01 Sv</code>
     * (standard name <code>rem</code>).
     */
    public static final Unit<RadiationDoseAbsorbed> REM = u(SI.SIEVERT.divide(100));

    //////////////////////////
    // Radioactive activity //
    //////////////////////////

    /**
     * A unit of radioactive activity equal to the activity of a gram of radium
     * (standard name <code>Ci</code>).
     */
    public static final Unit<RadioactiveActivity> CURIE = u(SI.BECQUEREL.multiply(37000000000L));

    /**
     * A unit of radioactive activity equal to 1 million radioactive
     * disintegrations per second (standard name <code>Rd</code>).
     */
    public static final Unit<RadioactiveActivity> RUTHERFORD = u(SI.BECQUEREL.multiply(1000000));

    /////////////////
    // Solid angle //
    /////////////////

    /**
     * A unit of solid angle equal to <code>4 <i>&pi;</i> steradians</code>
     * (standard name <code>sphere</code>).
     */
    public static final Unit<SolidAngle> SPHERE = u(SI.STERADIAN.multiply(4.0 * Math.PI));
    
    ////////////
    // Volume //
    ////////////

    /**
     * A unit of volume equal to one cubic decimeter (default label
     * <code>L</code>, also recognized <code>µL, mL, cL, dL</code>).
     */
    public static final Unit<Volume> LITRE = u(SI.CUBIC_METRE.divide(1000));

    /**
     * Equivalent to {@link #LITRE} (American spelling).
     */
    public static final Unit<Volume> LITER = LITRE;

    /**
     * A unit of volume equal to one cubic inch (<code>in³</code>).
     */
    public static final Unit<Volume> CUBIC_INCH = u(INCH.pow(3));

    /**
     * A unit of volume equal to one US gallon, Liquid Unit. The U.S. liquid
     * gallon is based on the Queen Anne or Wine gallon occupying 231 cubic
     * inches (standard name <code>gal</code>).
     */
    public static final Unit<Volume> GALLON_LIQUID_US = u(CUBIC_INCH.multiply(231));

    /**
     * A unit of volume equal to <code>1 / 128 {@link #GALLON_LIQUID_US}</code>
     * (standard name <code>oz_fl</code>).
     */
    public static final Unit<Volume> OUNCE_LIQUID_US = u(GALLON_LIQUID_US.divide(128));

    /**
     * A unit of volume equal to one US dry gallon.
     * (standard name <code>gallon_dry_us</code>).
     */
    public static final Unit<Volume> GALLON_DRY_US = u(CUBIC_INCH.multiply(2688025).divide(10000));

    /**
     * A unit of volume equal to <code>4.546 09 {@link #LITRE}</code>
     * (standard name <code>gal_uk</code>).
     */
    public static final Unit<Volume> GALLON_UK = u(LITRE.multiply(454609).divide(100000));

    /**
     * A unit of volume equal to <code>1 / 160 {@link #GALLON_UK}</code>
     * (standard name <code>oz_fl_uk</code>).
     */
    public static final Unit<Volume> OUNCE_LIQUID_UK = u(GALLON_UK.divide(160));

    ///////////////
    // Viscosity //
    ///////////////

    /**
     * A unit of dynamic viscosity equal to <code>1 g/(cm·s)</code>
     * (cgs unit).
     */
    public static final Unit<DynamicViscosity> POISE = u(SI.GRAM.divide(Prefix.CENTI.transform(SI.METRE).multiply(SI.SECOND)));

    /**
     * A unit of kinematic viscosity equal to <code>1 cm²/s</code>
     * (cgs unit).
     */
    public static final Unit<KinematicViscosity> STOKE = u(Prefix.CENTI.transform(SI.METRE).pow(2).divide(SI.SECOND));
    
    ////////////////////////
    // VolumetricFlowRate //
    ////////////////////////
    
    /** */
    public static final Unit<VolumetricFlowRate> CFS = u(FOOT.pow(3).divide(SI.SECOND));
    

    ////////////
    // Others //
    ////////////

    /**
     * A unit used to measure the ionizing ability of radiation
     * (standard name <code>Roentgen</code>).
     */
    public static final Unit<?> ROENTGEN = u(SI.COULOMB.divide(SI.KILOGRAM).multiply(2.58e-4));

    
    /////////////////////
    // Collection View //
    /////////////////////
    
    /**
     * Default constructor (prevents this class from being instantiated).
     */
    private NonSI() { }
}