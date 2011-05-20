package org.ngs.ngunits;

import java.util.HashSet;
import org.ngs.ngunits.quantity.*;

/**
 * This class contains units defined in the 
 * <a href="http://aurora.regenstrief.org/~ucum/ucum.html">Uniform Code 
 * for Units of Measure</a>. 
 * <p>
 * Compatability with existing JScience units has been given priority 
 * over strict adherence to the standard. We have attempted to note 
 * every place where the definitions in this class deviate from the 
 * UCUM standard, but such notes are likely to be incomplete.
 * 
 * @see <a href="http://aurora.regenstrief.org/~ucum/ucum.html">UCUM</a>
 */
public final class UCUM extends SystemOfUnits
{
    /**
     * Holds collection of metric UCUM units. Currently it is unused, but 
     * I wanted a way to maintain the information from the UCUM standard 
     * about which units are metric and which are not. Technically, 
     * prefixes are only valid when used with metric units, but no attempt 
     * is made to enforce that restriction in the current implementation.
     */
    protected static HashSet<Unit<?>> METRIC_UNITS = new HashSet<Unit<?>>();
    
    public static final UCUM INSTANCE = new UCUM();
    
    /**
     * Adds a new unit to the collection.
     * @param  unit the unit being added.
     * @return <code>unit</code>.
     */
    protected static Unit ucum (Unit unit, boolean isMetric) {
        if (isMetric) {
            METRIC_UNITS.add(unit);
        }
        return u(unit);
    }
    
    //////////////////////////////
    // BASE UNITS: UCUM 4.2 §25 //
    //////////////////////////////
    
    /** */
    public static final Unit<Length> METER = ucum(SI.METRE, true);
    
    /** */
    public static final Unit<Time> SECOND = ucum(SI.SECOND, true);
    
    /**
     * We deviate slightly from the standard here, to maintain compatibility
     * with the existing SI units. In UCUM, the gram is the base unit of mass,
     * rather than the kilogram. This doesn't have much effect on the units
     * themselves, but it does make formatting the units a challenge.
     */
    public static final Unit<Mass> GRAM = ucum(SI.GRAM, true);
    public static final Unit<Mass> KILOGRAM = SI.KILOGRAM;
    
    /** */
    public static final Unit<Angle> RADIAN = ucum(SI.RADIAN, true);
    
    /** */
    public static final Unit<Temperature> KELVIN = ucum(SI.KELVIN, true);
    
    /** */
    public static final Unit<ElectricCharge> COULOMB = ucum(SI.COULOMB, true);
    
    /** */
    public static final Unit<LuminousIntensity> CANDELA = ucum(SI.CANDELA, true);
    
    ///////////////////////////////////////////////
    // DIMENSIONLESS DERIVED UNITS: UCUM 4.3 §26 //
    ///////////////////////////////////////////////
    
    /** */
    public static final Unit<Dimensionless> TRIILLIONS = ucum(DELEGATE.one().multiply(1000000000000L), false);
    
    /** */
    public static final Unit<Dimensionless> BILLIONS = ucum(DELEGATE.one().multiply(1000000000), false);
    
    /** */
    public static final Unit<Dimensionless> MILLIONS = ucum(DELEGATE.one().multiply(1000000), false);
    
    /** */
    public static final Unit<Dimensionless> THOUSANDS = ucum(DELEGATE.one().multiply(1000), false);
    
    /** */
    public static final Unit<Dimensionless> HUNDREDS = ucum(DELEGATE.one().multiply(100), false);
    
    /** */
    public static final Unit<Dimensionless> PI = ucum(DELEGATE.one().multiply(StrictMath.PI), false);
    
    /** */
    public static final Unit<Dimensionless> PERCENT = ucum(DELEGATE.one().divide(100), false);
    
    /** */
    public static final Unit<Dimensionless> PER_THOUSAND = ucum(DELEGATE.one().divide(1000), false);
    
    /** */
    public static final Unit<Dimensionless> PER_MILLION = ucum(DELEGATE.one().divide(1000000), false);
    
    /** */
    public static final Unit<Dimensionless> PER_BILLION = ucum(DELEGATE.one().divide(1000000000), false);
    
    /** */
    public static final Unit<Dimensionless> PER_TRILLION = ucum(DELEGATE.one().divide(1000000000000L), false);
    
    ////////////////////////////
    // SI UNITS: UCUM 4.3 §27 //
    ////////////////////////////
    
    /**
     * We deviate slightly from the standard here, to maintain compatibility
     * with the existing SI units. In UCUM, the mole is no longer a base unit,
     * but is defined as <code>DELEGATE.one().multiply(6.0221367E23)</code>.
     */
    public static final Unit<AmountOfSubstance> MOLE = ucum(SI.MOLE, true);
    
    /**
     * We deviate slightly from the standard here, to maintain compatibility
     * with the existing SI units. In UCUM, the steradian is defined as 
     * <code>RADIAN.pow(2)</code>.
     */
    public static final Unit<SolidAngle> STERADIAN = ucum(SI.STERADIAN, true);
    
    /** */
    public static final Unit<Frequency> HERTZ = ucum(DELEGATE.one().divide(SECOND), true);
    
    /** */
    public static final Unit<Force> NEWTON = ucum(SI.NEWTON, true);
    
    /** */
    public static final Unit<Pressure> PASCAL = ucum(SI.PASCAL, true);
    
    /** */
    public static final Unit<Energy> JOULE = ucum(SI.JOULE, true);
    
    /** */
    public static final Unit<Power> WATT = ucum(SI.WATT, true);
    
    /** 
     * We deviate slightly from the standard here, to maintain compatibility
     * with the existing SI units. In UCUM, the ampere is defined as 
     * <code>COULOMB.divide(SECOND)</code>.
     */
    public static final Unit<ElectricCurrent> AMPERE = ucum(SI.AMPERE, true);
    
    /** 
     * We deviate slightly from the standard here, to maintain compatibility
     * with the existing SI units. In UCUM, the volt is defined as 
     * <code>JOULE.divide(COULOMB)</code>.
     */
    public static final Unit<ElectricPotential> VOLT = ucum(SI.VOLT, true);
    
    /** */
    public static final Unit<ElectricCapacitance> FARAD = ucum(SI.FARAD, true);
    
    /** */
    public static final Unit<ElectricResistance> OHM = ucum(SI.OHM, true);
    
    /** 
     * We deviate slightly from the standard here, to maintain compatibility
     * with the existing SI units. In UCUM, the volt is defined as 
     * <code>DELEGATE.one().divide(OHM)</code>.
     */
    public static final Unit<ElectricConductance> SIEMENS = ucum(SI.SIEMENS, true);
    
    /** */
    public static final Unit<MagneticFlux> WEBER = ucum(SI.WEBER, true);
    
    /** */
    public static final Unit<Temperature> CELSIUS = ucum(SI.CELSIUS, true);
    
    /** */
    public static final Unit<MagneticFluxDensity> TESLA = ucum(SI.TESLA, true);
    
    /** */
    public static final Unit<ElectricInductance> HENRY = ucum(SI.HENRY, true);
    
    /** */
    public static final Unit<LuminousFlux> LUMEN = ucum(SI.LUMEN, true);
    
    /** */
    public static final Unit<LuminousIntensity> LUX = ucum(SI.LUX, true);
    
    /** */
    public static final Unit<RadioactiveActivity> BECQUEREL = ucum(SI.BECQUEREL, true);
    
    /** */
    public static final Unit<RadiationDoseAbsorbed> GRAY = ucum(SI.GRAY, true);
    
    /** */
    public static final Unit<RadiationDoseEffective> SIEVERT = ucum(SI.SIEVERT, true);
    
    ///////////////////////////////////////////////////////////////////////
    // OTHER UNITS FROM ISO 1000, ISO 2955, AND ANSI X3.50: UCUM 4.3 §28 //
    ///////////////////////////////////////////////////////////////////////
    
    // The order of GON and DEGREE has been inverted because GON is defined in terms of DEGREE
    
    /** 
     * We deviate slightly from the standard here, to maintain compatibility
     * with the existing NonSI units. In UCUM, the degree is defined as 
     * <code>PI.multiply(RADIAN.divide(180))</code>.
     */
    public static final Unit<Angle> DEGREE = ucum(NonSI.DEGREE_ANGLE, false);
    
    /** 
     * We deviate slightly from the standard here, to maintain compatibility
     * with the existing NonSI units. In UCUM, the grade is defined as 
     * <code>DEGREE.multiply(0.9)</code>.
     */
    public static final Unit<Angle> GRADE = ucum(NonSI.GRADE, false);
    
    /** */
    public static final Unit<Angle> GON = GRADE;
    
    /** */
    public static final Unit<Angle> MINUTE_ANGLE = ucum(NonSI.MINUTE_ANGLE, false);
    
    /** */
    public static final Unit<Angle> SECOND_ANGLE = ucum(NonSI.SECOND_ANGLE, false);
    
    /** */
    public static final Unit<Volume> LITER = ucum(NonSI.LITRE, true);
    
    /** */
    public static final Unit<Area> ARE = ucum(NonSI.ARE, true);
    
    /** */
    public static final Unit<Time> MINUTE = ucum(NonSI.MINUTE, false);
    
    /** */
    public static final Unit<Time> HOUR = ucum(NonSI.HOUR, false);
    
    /** */
    public static final Unit<Time> DAY = ucum(NonSI.DAY, false);
    
    /** */
    public static final Unit<Time> YEAR_TROPICAL = ucum(DAY.multiply(365.24219), false);
    
    /** */
    public static final Unit<Time> YEAR_JULIAN = ucum(DAY.multiply(365.25), false);
    
    /** */
    public static final Unit<Time> YEAR_GREGORIAN = ucum(DAY.multiply(365.2425), false);
    
    /** */
    public static final Unit<Time> YEAR = ucum(DAY.multiply(365.25), false);
    
    /** */
    public static final Unit<Time> WEEK = ucum(NonSI.WEEK, false);
    
    /** */
    public static final Unit<Time> MONTH_SYNODAL = ucum(DAY.multiply(29.53059), false);
    
    /** */
    public static final Unit<Time> MONTH_JULIAN = ucum(YEAR_JULIAN.divide(12), false);
    
    /** */
    public static final Unit<Time> MONTH_GREGORIAN = ucum(YEAR_GREGORIAN.divide(12), false);
    
    /** */
    public static final Unit<Time> MONTH = ucum(YEAR_JULIAN.divide(12), false);
    
    /** */
    public static final Unit<Mass> TONNE = ucum(NonSI.METRIC_TON, true);
    
    /** */
    public static final Unit<Pressure> BAR = ucum(NonSI.BAR, true);
    
    /** */
    public static final Unit<Mass> ATOMIC_MASS_UNIT = ucum(NonSI.ATOMIC_MASS, true);
    
    /** */
    public static final Unit<Energy> ELECTRON_VOLT = ucum(NonSI.ELECTRON_VOLT, true);
    
    /** */
    public static final Unit<Length> ASTRONOMIC_UNIT = ucum(NonSI.ASTRONOMICAL_UNIT, false);
    
    /** */
    public static final Unit<Length> PARSEC = ucum(NonSI.PARSEC, true);
    
    /////////////////////////////////
    // NATURAL UNITS: UCUM 4.3 §29 //
    /////////////////////////////////
    
    /** */
    public static final Unit<Velocity> C = ucum(NonSI.C, true);
    
    /** */
    public static final Unit<Action> PLANCK = ucum(JOULE.multiply(SECOND).multiply(6.6260755E-24), true);
    
    /** */
    public static final Unit<Dimensionless> BOLTZMAN = ucum(JOULE.divide(KELVIN).multiply(1.380658E-23), true);
    
    /** */
    public static final Unit<ElectricPermittivity> PERMITTIVITY_OF_VACUUM = ucum(FARAD.divide(METER).multiply(8.854187817E-12), true);
    
    /** */
    public static final Unit<MagneticPermittivity> PERMEABILITY_OF_VACUUM = ucum(NEWTON.multiply(4E-7 * StrictMath.PI).divide(AMPERE.pow(2)), true);
    
    /** */
    public static final Unit<ElectricCharge> ELEMENTARY_CHARGE = ucum(NonSI.E, true);
    
    /** */
    public static final Unit<Mass> ELECTRON_MASS = ucum(NonSI.ELECTRON_MASS, true);
    
    /** */
    public static final Unit<Mass> PROTON_MASS = ucum(GRAM.multiply(1.6726231E-24), true);
    
    /** */
    public static final Unit<Acceleration> NEWTON_CONSTANT_OF_GRAVITY = ucum(METER.pow(3).multiply(KILOGRAM.pow(-1)).multiply(SECOND.pow(-2)).multiply(6.67259E-11), true);
    
    /** */
    public static final Unit<Acceleration> ACCELLERATION_OF_FREEFALL = ucum(NonSI.G, true);
    
    /** */
    public static final Unit<Pressure> ATMOSPHERE = ucum(NonSI.ATMOSPHERE, false);
    
    /** */
    public static final Unit<Length> LIGHT_YEAR = ucum(NonSI.LIGHT_YEAR, true);
    
    /** */
    public static final Unit<Force> GRAM_FORCE = ucum(GRAM.multiply(ACCELLERATION_OF_FREEFALL), true);
    
    // POUND_FORCE contains a forward reference to avoirdupois pound weight, so it has been moved after section §36 below
    
    /////////////////////////////
    // CGS UNITS: UCUM 4.3 §30 //
    /////////////////////////////
    
    /** */
    public static final Unit<LineicNumber> KAYSER = ucum(DELEGATE.one().divide(Prefix.CENTI.transform(METER)), true);
    
    /** */
    public static final Unit<Acceleration> GAL = ucum(Prefix.CENTI.transform(METER).divide(SECOND.pow(2)), true);
    
    /** */
    public static final Unit<Force> DYNE = ucum(NonSI.DYNE, true);
    
    /** */
    public static final Unit<Energy> ERG = ucum(NonSI.ERG, true);
    
    /** */
    public static final Unit<DynamicViscosity> POISE = ucum(NonSI.POISE, true);
    
    /** */
    public static final Unit<ElectricCurrent> BIOT = ucum(AMPERE.multiply(10), true);
    
    /** */
    public static final Unit<KinematicViscosity> STOKES = ucum(NonSI.STOKE, true);
    
    /** */
    public static final Unit<MagneticFlux> MAXWELL = ucum(NonSI.MAXWELL, true);
    
    /** */
    public static final Unit<MagneticFluxDensity> GAUSS = ucum(NonSI.GAUSS, true);
    
    /** */
    public static final Unit<MagneticFluxIntensity> OERSTED = ucum(DELEGATE.one().divide(PI).multiply(AMPERE).divide(METER).multiply(250), true);
    
    /** */
    public static final Unit<MagneticTension> GILBERT = ucum(OERSTED.multiply(Prefix.CENTI.transform(METER)), true);
    
    /** */
    public static final Unit<LuminousIntensityDensity> STILB = ucum(CANDELA.divide(Prefix.CENTI.transform(METER).pow(2)), true);
    
    /** */
    public static final Unit<Illuminance> LAMBERT = ucum(NonSI.LAMBERT, true);
    
    /** */
    public static final Unit<Illuminance> PHOT = ucum(LUX.divide(10000), true);
    
    /** */
    public static final Unit<RadioactiveActivity> CURIE = ucum(NonSI.CURIE, true);
    
    /** */
    public static final Unit<IonDose> ROENTGEN = ucum(NonSI.ROENTGEN, true);
    
    /** */
    public static final Unit<RadiationDoseAbsorbed> RAD = ucum(NonSI.RAD, true);
    
    /** */
    public static final Unit<RadiationDoseEffective> REM = ucum(NonSI.REM, true);
    
    /////////////////////////////////////////////////
    // INTERNATIONAL CUSTOMARY UNITS: UCUM 4.4 §31 //
    /////////////////////////////////////////////////
    
    /** */
    public static final Unit<Length> INCH_INTERNATIONAL = ucum(Prefix.CENTI.transform(METER).multiply(254).divide(100), false);
    
    /** */
    public static final Unit<Length> FOOT_INTERNATIONAL = ucum(INCH_INTERNATIONAL.multiply(12), false);
    
    /** */
    public static final Unit<Length> YARD_INTERNATIONAL = ucum(FOOT_INTERNATIONAL.multiply(3), false);
    
    /** */
    public static final Unit<Length> MILE_INTERNATIONAL = ucum(FOOT_INTERNATIONAL.multiply(5280), false);
    
    /** */
    public static final Unit<Length> FATHOM_INTERNATIONAL = ucum(FOOT_INTERNATIONAL.multiply(6), false);
    
    /** */
    public static final Unit<Length> NAUTICAL_MILE_INTERNATIONAL = ucum(METER.multiply(1852), false);
    
    /** */
    public static final Unit<Length> KNOT_INTERNATIONAL = ucum(NAUTICAL_MILE_INTERNATIONAL.divide(HOUR), false);
    
    /** */
    public static final Unit<Area> SQUARE_INCH_INTERNATIONAL = ucum(INCH_INTERNATIONAL.pow(2), false);
    
    /** */
    public static final Unit<Area> SQUARE_FOOT_INTERNATIONAL = ucum(FOOT_INTERNATIONAL.pow(2), false);
    
    /** */
    public static final Unit<Area> SQUARE_YARD_INTERNATIONAL = ucum(YARD_INTERNATIONAL.pow(2), false);
    
    /** */
    public static final Unit<Volume> CUBIC_INCH_INTERNATIONAL = ucum(INCH_INTERNATIONAL.pow(3), false);
    
    /** */
    public static final Unit<Volume> CUBIC_FOOT_INTERNATIONAL = ucum(FOOT_INTERNATIONAL.pow(3), false);
    
    /** */
    public static final Unit<Volume> CUBIC_YARD_INTERNATIONAL = ucum(YARD_INTERNATIONAL.pow(3), false);
    
    /** */
    public static final Unit<Volume> BOARD_FOOT_INTERNATIONAL = ucum(CUBIC_INCH_INTERNATIONAL.multiply(144), false);
    
    /** */
    public static final Unit<Volume> CORD_INTERNATIONAL = ucum(CUBIC_FOOT_INTERNATIONAL.multiply(128), false);
    
    /** */
    public static final Unit<Length> MIL_INTERNATIONAL = ucum(INCH_INTERNATIONAL.divide(1000), false);
    
    /** */
    public static final Unit<Area> CIRCULAR_MIL_INTERNATIONAL = ucum(MIL_INTERNATIONAL.multiply(PI).divide(4), false);
    
    /** */
    public static final Unit<Length> HAND_INTERNATIONAL = ucum(INCH_INTERNATIONAL.multiply(4), false);
    
    //////////////////////////////////////////
    // US SURVEY LENGTH UNITS: UCUM 4.4 §32 //
    //////////////////////////////////////////
    
    /** */
    public static final Unit<Length> FOOT_US_SURVEY = ucum(METER.multiply(1200).divide(3937), false);
    
    /** */
    public static final Unit<Length> YARD_US_SURVEY = ucum(FOOT_US_SURVEY.multiply(3), false);
    
    /** */
    public static final Unit<Length> INCH_US_SURVEY = ucum(FOOT_US_SURVEY.divide(12), false);
    
    /** */
    public static final Unit<Length> ROD_US_SURVEY = ucum(FOOT_US_SURVEY.multiply(33).divide(2), false);
    
    /** */
    public static final Unit<Length> CHAIN_US_SURVEY = ucum(ROD_US_SURVEY.multiply(4), false);
    
    /** */
    public static final Unit<Length> LINK_US_SURVEY = ucum(CHAIN_US_SURVEY.divide(100), false);
    
    /** */
    public static final Unit<Length> RAMDEN_CHAIN_US_SURVEY = ucum(FOOT_US_SURVEY.multiply(100), false);
    
    /** */
    public static final Unit<Length> RAMDEN_LINK_US_SURVEY = ucum(CHAIN_US_SURVEY.divide(100), false);
    
    /** */
    public static final Unit<Length> FATHOM_US_SURVEY = ucum(FOOT_US_SURVEY.multiply(6), false);
    
    /** */
    public static final Unit<Length> FURLONG_US_SURVEY = ucum(ROD_US_SURVEY.multiply(40), false);
    
    /** */
    public static final Unit<Length> MILE_US_SURVEY = ucum(FURLONG_US_SURVEY.multiply(8), false);
    
    /** */
    public static final Unit<Area> ACRE_US_SURVEY = ucum(ROD_US_SURVEY.pow(2).multiply(160), false);
    
    /** */
    public static final Unit<Area> SQUARE_ROD_US_SURVEY = ucum(ROD_US_SURVEY.pow(2), false);
    
    /** */
    public static final Unit<Area> SQUARE_MILE_US_SURVEY = ucum(MILE_US_SURVEY.pow(2), false);
    
    /** */
    public static final Unit<Area> SECTION_US_SURVEY = ucum(MILE_US_SURVEY.pow(2), false);
    
    /** */
    public static final Unit<Area> TOWNSHP_US_SURVEY = ucum(SECTION_US_SURVEY.multiply(36), false);
    
    /** */
    public static final Unit<Length> MIL_US_SURVEY = ucum(INCH_US_SURVEY.divide(1000), false);
    
    /////////////////////////////////////////////////
    // BRITISH IMPERIAL LENGTH UNITS: UCUM 4.4 §33 //
    /////////////////////////////////////////////////
    
    /** */
    public static final Unit<Length> INCH_BRITISH = ucum(Prefix.CENTI.transform(METER).multiply(2539998).divide(1000000), false);
    
    /** */
    public static final Unit<Length> FOOT_BRITISH = ucum(INCH_BRITISH.multiply(12), false);
    
    /** */
    public static final Unit<Length> ROD_BRITISH = ucum(FOOT_BRITISH.multiply(33).divide(2), false);
    
    /** */
    public static final Unit<Length> CHAIN_BRITISH = ucum(ROD_BRITISH.multiply(4), false);
    
    /** */
    public static final Unit<Length> LINK_BRITISH = ucum(CHAIN_BRITISH.divide(100), false);
    
    /** */
    public static final Unit<Length> FATHOM_BRITISH = ucum(FOOT_BRITISH.multiply(6), false);
    
    /** */
    public static final Unit<Length> PACE_BRITISH = ucum(FOOT_BRITISH.multiply(5).divide(20), false);
    
    /** */
    public static final Unit<Length> YARD_BRITISH = ucum(FOOT_BRITISH.multiply(3), false);
    
    /** */
    public static final Unit<Length> MILE_BRITISH = ucum(FOOT_BRITISH.multiply(5280), false);
    
    /** */
    public static final Unit<Length> NAUTICAL_MILE_BRITISH = ucum(FOOT_BRITISH.multiply(6080), false);
    
    /** */
    public static final Unit<Length> KNOT_BRITISH = ucum(NAUTICAL_MILE_BRITISH.divide(HOUR), false);
    
    /** */
    public static final Unit<Area> ACRE_BRITISH = ucum(YARD_BRITISH.pow(2).multiply(4840), false);
    
    ///////////////////////////////////
    // US VOLUME UNITS: UCUM 4.4 §34 //
    ///////////////////////////////////
    
    /** */
    public static final Unit<Volume> GALLON_US = ucum(CUBIC_INCH_INTERNATIONAL.multiply(231), false);
    
    /** */
    public static final Unit<Volume> BARREL_US = ucum(GALLON_US.multiply(42), false);
    
    /** */
    public static final Unit<Volume> QUART_US = ucum(GALLON_US.divide(4), false);
    
    /** */
    public static final Unit<Volume> PINT_US = ucum(QUART_US.divide(2), false);
    
    /** */
    public static final Unit<Volume> GILL_US = ucum(PINT_US.divide(4), false);
    
    /** */
    public static final Unit<Volume> FLUID_OUNCE_US = ucum(GILL_US.divide(4), false);
    
    /** */
    public static final Unit<Volume> FLUID_DRAM_US = ucum(FLUID_OUNCE_US.divide(8), false);
    
    /** */
    public static final Unit<Volume> MINIM_US = ucum(FLUID_DRAM_US.divide(60), false);
    
    /** */
    public static final Unit<Volume> CORD_US = ucum(CUBIC_FOOT_INTERNATIONAL.multiply(128), false);
    
    /** */
    public static final Unit<Volume> BUSHEL_US = ucum(CUBIC_INCH_INTERNATIONAL.multiply(215042).divide(100), false);
    
    /** */
    public static final Unit<Volume> GALLON_WINCHESTER = ucum(BUSHEL_US.divide(8), false);
    
    /** */
    public static final Unit<Volume> PECK_US = ucum(BUSHEL_US.divide(4), false);
    
    /** */
    public static final Unit<Volume> DRY_QUART_US = ucum(PECK_US.divide(8), false);
    
    /** */
    public static final Unit<Volume> DRY_PINT_US = ucum(DRY_QUART_US.divide(2), false);
    
    /** */
    public static final Unit<Volume> TABLESPOON_US = ucum(FLUID_OUNCE_US.divide(2), false);
    
    /** */
    public static final Unit<Volume> TEASPOON_US = ucum(TABLESPOON_US.divide(3), false);
    
    /** */
    public static final Unit<Volume> CUP_US = ucum(TABLESPOON_US.multiply(16), false);
    
    /////////////////////////////////////////////////
    // BRITISH IMPERIAL VOLUME UNITS: UCUM 4.4 §35 //
    /////////////////////////////////////////////////
    
    /** */
    public static final Unit<Volume> GALLON_BRITISH = ucum(LITER.multiply(454609).divide(100000), false);
    
    /** */
    public static final Unit<Volume> PECK_BRITISH = ucum(GALLON_BRITISH.multiply(2), false);
    
    /** */
    public static final Unit<Volume> BUSHEL_BRITISH = ucum(PECK_BRITISH.multiply(4), false);
    
    /** */
    public static final Unit<Volume> QUART_BRITISH = ucum(GALLON_BRITISH.divide(4), false);
    
    /** */
    public static final Unit<Volume> PINT_BRITISH = ucum(QUART_BRITISH.divide(2), false);
    
    /** */
    public static final Unit<Volume> GILL_BRITISH = ucum(PINT_BRITISH.divide(4), false);
    
    /** */
    public static final Unit<Volume> FLUID_OUNCE_BRITISH = ucum(GILL_BRITISH.divide(5), false);
    
    /** */
    public static final Unit<Volume> FLUID_DRAM_BRITISH = ucum(FLUID_OUNCE_BRITISH.divide(8), false);
    
    /** */
    public static final Unit<Volume> MINIM_BRITISH = ucum(FLUID_DRAM_BRITISH.divide(60), false);
    
    ////////////////////////////////////////////
    // AVOIRDUPOIS WIEGHT UNITS: UCUM 4.4 §36 //
    ////////////////////////////////////////////
    
    /** */
    public static final Unit<Mass> GRAIN = ucum(Prefix.MILLI.transform(GRAM).multiply(6479891).divide(100000), false);
    
    /** */
    public static final Unit<Mass> POUND = ucum(GRAM.multiply(7000), false);
    
    /** */
    public static final Unit<Mass> OUNCE = ucum(POUND.divide(16), false);
    
    /** */
    public static final Unit<Mass> DRAM = ucum(OUNCE.divide(16), false);
    
    /** */
    public static final Unit<Mass> SHORT_HUNDREDWEIGHT = ucum(POUND.multiply(100), false);
    
    /** */
    public static final Unit<Mass> LONG_HUNDREDWEIGHT = ucum(POUND.multiply(112), false);
    
    /** */
    public static final Unit<Mass> SHORT_TON = ucum(SHORT_HUNDREDWEIGHT.multiply(20), false);
    
    /** */
    public static final Unit<Mass> LONG_TON = ucum(LONG_HUNDREDWEIGHT.multiply(20), false);
    
    /** */
    public static final Unit<Mass> STONE = ucum(POUND.multiply(14), false);
    
    // CONTINUED FROM SECTION §29
    // contains a forward reference to POUND, so we had to move it here, below section §36
    
    /** */
    public static final Unit<Force> POUND_FORCE = ucum(POUND.multiply(ACCELLERATION_OF_FREEFALL), false);
    
    /////////////////////////////////////
    // TROY WIEGHT UNITS: UCUM 4.4 §37 //
    /////////////////////////////////////
    
    /** */
    public static final Unit<Mass> PENNYWEIGHT_TROY = ucum(GRAIN.multiply(24), false);
    
    /** */
    public static final Unit<Mass> OUNCE_TROY = ucum(PENNYWEIGHT_TROY.multiply(24), false);
    
    /** */
    public static final Unit<Mass> POUND_TROY = ucum(OUNCE_TROY.multiply(12), false);
    
    /////////////////////////////////////////////
    // APOTECARIES' WEIGHT UNITS: UCUM 4.4 §38 //
    /////////////////////////////////////////////
    
    /** */
    public static final Unit<Mass> SCRUPLE_APOTHECARY = ucum(GRAIN.multiply(20), false);
    
    /** */
    public static final Unit<Mass> DRAM_APOTHECARY = ucum(SCRUPLE_APOTHECARY.multiply(3), false);
    
    /** */
    public static final Unit<Mass> OUNCE_APOTHECARY = ucum(DRAM_APOTHECARY.multiply(8), false);
    
    /** */
    public static final Unit<Mass> POUND_APOTHECARY = ucum(OUNCE_APOTHECARY.multiply(12), false);
    
    /////////////////////////////////////////////
    // TYPESETTER'S LENGTH UNITS: UCUM 4.4 §39 //
    /////////////////////////////////////////////
    
    /** */
    public static final Unit<Length> LINE = ucum(INCH_INTERNATIONAL.divide(12), false);
    
    /** */
    public static final Unit<Length> POINT = ucum(LINE.divide(6), false);
    
    /** */
    public static final Unit<Length> PICA = ucum(POINT.multiply(12), false);
    
    /** */
    public static final Unit<Length> POINT_PRINTER = ucum(INCH_INTERNATIONAL.multiply(13837).divide(1000000), false);
    
    /** */
    public static final Unit<Length> PICA_PRINTER = ucum(POINT_PRINTER.multiply(12), false);
    
    /** */
    public static final Unit<Length> PIED = ucum(Prefix.CENTI.transform(METER).multiply(3248).divide(100), false);
    
    /** */
    public static final Unit<Length> POUCE = ucum(PIED.divide(12), false);
    
    /** */
    public static final Unit<Length> LINGE = ucum(POUCE.divide(12), false);
    
    /** */
    public static final Unit<Length> DIDOT = ucum(LINGE.divide(6), false);
    
    /** */
    public static final Unit<Length> CICERO = ucum(DIDOT.multiply(12), false);
    
    //////////////////////////////////////
    // OTHER LEGACY UNITS: UCUM 4.5 §40 //
    //////////////////////////////////////
    
    /** */
    public static final Unit<Temperature> FAHRENHEIT = ucum(KELVIN.multiply(5).divide(9).add(459.67), false);
    
    /** */
    public static final Unit<Energy> CALORIE_AT_15C = ucum(JOULE.multiply(41858).divide(10000), true);
    
    /** */
    public static final Unit<Energy> CALORIE_AT_20C = ucum(JOULE.multiply(41819).divide(10000), true);
    
    /** */
    public static final Unit<Energy> CALORIE_MEAN = ucum(JOULE.multiply(419002).divide(100000), true);
    
    /** */
    public static final Unit<Energy> CALORIE_INTERNATIONAL_TABLE = ucum(JOULE.multiply(41868).divide(10000), true);
    
    /** */
    public static final Unit<Energy> CALORIE_THERMOCHEMICAL = ucum(JOULE.multiply(4184).divide(1000), true);
    
    /** */
    public static final Unit<Energy> CALORIE = ucum(CALORIE_THERMOCHEMICAL, true);
    
    /** */
    public static final Unit<Energy> CALORIE_FOOD = ucum(Prefix.KILO.transform(CALORIE_THERMOCHEMICAL), false);
    
    /** */
    public static final Unit<Energy> BTU_AT_39F = ucum(Prefix.KILO.transform(JOULE).multiply(105967).divide(100000), false);
    
    /** */
    public static final Unit<Energy> BTU_AT_59F = ucum(Prefix.KILO.transform(JOULE).multiply(105480).divide(100000), false);
    
    /** */
    public static final Unit<Energy> BTU_AT_60F = ucum(Prefix.KILO.transform(JOULE).multiply(105468).divide(100000), false);
    
    /** */
    public static final Unit<Energy> BTU_MEAN = ucum(Prefix.KILO.transform(JOULE).multiply(105587).divide(100000), false);
    
    /** */
    public static final Unit<Energy> BTU_INTERNATIONAL_TABLE = ucum(Prefix.KILO.transform(JOULE).multiply(105505585262L).divide(100000000000L), false);
    
    /** */
    public static final Unit<Energy> BTU_THERMOCHEMICAL = ucum(Prefix.KILO.transform(JOULE).multiply(105735).divide(100000), false);
    
    /** */
    public static final Unit<Energy> BTU = ucum(BTU_THERMOCHEMICAL, false);
    
    /** */
    public static final Unit<Power> HORSEPOWER = ucum(FOOT_INTERNATIONAL.multiply(POUND_FORCE).divide(SECOND), false);
    
    /////////////////////////////////////////////////////////
    // SECTIONS §41-§43 skipped; implement later if needed //
    /////////////////////////////////////////////////////////
    
    ///////////////////////////////////////
    // MISCELLANEOUS UNITS: UCUM 4.5 §44 //
    ///////////////////////////////////////
    
    /** */
    public static final Unit<Volume> STERE = ucum(METER.pow(3), true);
    
    /** */
    public static final Unit<Length> ANGSTROM = ucum(Prefix.NANO.transform(METER).divide(10), false);
    
    /** */
    public static final Unit<Area> BARN = ucum(Prefix.FEMTO.transform(METER).pow(2).multiply(100), false);
    
    /** */
    public static final Unit<Pressure> ATMOSPHERE_TECHNICAL = ucum(Prefix.KILO.transform(GRAM_FORCE).divide(Prefix.CENTI.transform(METER).pow(2)), false);
    
    /** */
    public static final Unit<ElectricConductance> MHO = ucum(SIEMENS.alternate("mho"), true);
    
    /** */
    public static final Unit<Pressure> POUND_PER_SQUARE_INCH = ucum(POUND_FORCE.divide(INCH_INTERNATIONAL.pow(2)), false);
    
    /** */
    public static final Unit<Pressure> CIRCLE = ucum(PI.multiply(RADIAN).multiply(2), false);
    
    /** */
    public static final Unit<Pressure> SPHERE = ucum(PI.multiply(STERADIAN).multiply(4), false);
    
    /** */
    public static final Unit<Mass> CARAT_METRIC = ucum(GRAM.divide(5), false);
    
    /** */
    public static final Unit<Dimensionless> CARAT_GOLD = ucum(DELEGATE.one().divide(24), false);
    
    ////////////////////////////////////////////////
    // INFORMATION TECHNOLOGY UNITS: UCUM 4.6 §45 //
    ////////////////////////////////////////////////
    
    /** */
    public static final Unit<Information> BIT = ucum(SI.BIT, true);
    
    /** */
    public static final Unit<Information> BYTE = ucum(NonSI.BYTE, true);
    
    /** */
    public static final Unit<InformationRate> BAUD = ucum(DELEGATE.one().divide(SECOND), true);
    

    /**
     * Default constructor (prevents this class from being instantiated).
     */
    private UCUM () { }
    
}
