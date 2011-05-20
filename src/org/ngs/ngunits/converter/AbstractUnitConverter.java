package org.ngs.ngunits.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.ngs.ngunits.UnitConverter;


/** */
public abstract class AbstractUnitConverter implements UnitConverter {

    public static final UnitConverter IDENTITY = new Identity();
    
    public boolean isIdentity() {
        return false;
    }
    
    public boolean isLinear() {
        return true;
    }

    public UnitConverter concatenate (UnitConverter converter) {
        return converter.isIdentity() ? this : new Compound(converter, this);
    }
    
    public List<UnitConverter> getCompoundConverters() {
        return null;
    }
    
    @Override
    public boolean equals(Object cvtr) {
        if (!(cvtr instanceof UnitConverter)) return false;
        return this.concatenate(((UnitConverter)cvtr).inverse()) == IDENTITY;        
    }

    @Override
    public int hashCode () {
        return Float.floatToIntBits((float)convert(1.0));
    }
    
    /**
     * This inner class represents the identity converter (singleton).
     */
    public static final class Identity extends AbstractUnitConverter {
        
        Identity() { }
        
        @Override
        public boolean isIdentity() {
            return true;
        }

        @Override
        public UnitConverter concatenate (UnitConverter converter) {
            return converter;
        }

        public UnitConverter inverse () {
            return this;
        }

        public double convert (double value) {
            return value;
        }
        
        @Override
        public boolean equals (Object that) {
            return (that instanceof UnitConverter) && ((UnitConverter)that).isIdentity();
        }
    }

    /**
     * This inner class represents a compound converter.
     */
    public static final class Compound extends AbstractUnitConverter {

        private final List<UnitConverter> _contents;
        
        Compound (UnitConverter first, UnitConverter second) {
            _contents = new ArrayList<UnitConverter>(2);
            _contents.add(first);
            _contents.add(second);
        }

        @Override
        public boolean isLinear() {
            return _contents.get(0).isLinear() && _contents.get(1).isLinear();
        }
        
        @Override
        public UnitConverter concatenate (UnitConverter converter) {
            if (converter instanceof Compound) {
                Compound c = (Compound)converter;
                if (_contents.get(0).equals(c._contents.get(1).inverse()) && 
                    _contents.get(1).equals(c._contents.get(0).inverse())) {
                    return IDENTITY;
                }
            }
            return super.concatenate(converter);
        }

        @Override
        public List<UnitConverter> getCompoundConverters () {
            return Collections.unmodifiableList(_contents);
        }

        public UnitConverter inverse () {
            return new Compound(_contents.get(1).inverse(), _contents.get(0).inverse());
        }

        public double convert (double value) {
            return _contents.get(1).convert(_contents.get(0).convert(value));
        }
    }
}
