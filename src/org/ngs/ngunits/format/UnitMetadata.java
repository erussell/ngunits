package org.ngs.ngunits.format;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import org.ngs.ngunits.Unit;

/**
 * The UnitMetadata class provides a set of mappings between 
 * {@link org.ngs.ngunits.Unit Units} and extended descriptions
 * and {@link org.ngs.ngunits.quantity.Quantity Quantities} for those
 * units, so that the descriptions and quantity names may be localized.
 * <p>
 * TODO: Document the individual fields and methods
 */
public class UnitMetadata {
    
    /** */
    private Locale _locale;
    
    /** */
    private Map<Unit, Map<String,String>> _metadata;
    
    /** */
    public UnitMetadata (ResourceBundle rb) {
        _locale = rb.getLocale();
        _metadata = new HashMap<Unit, Map<String,String>>();
        for (Enumeration<String> keys = rb.getKeys(); keys.hasMoreElements();) {
            String key = keys.nextElement();
            int lastDot = key.lastIndexOf('.');
            String propertyName = key.substring(lastDot+1, key.length());
            int secondToLastDot = key.lastIndexOf('.', lastDot - 1);
            String fieldName = key.substring(secondToLastDot+1, lastDot);
            String className = key.substring(0, secondToLastDot);
            try {
                Class<?> c = Class.forName(className);
                Field field = c.getField(fieldName);
                Object value = field.get(null);
                if (value instanceof Unit) {
                    Map<String,String> metadata = _metadata.get(value);
                    if (metadata == null) {
                        metadata = new HashMap<String,String>();
                        _metadata.put((Unit)value, metadata);
                    }
                    metadata.put(propertyName.toUpperCase(), rb.getString(key));
                }
            } catch (Exception e) {
                System.err.println("ERROR reading Unit names: " + e.toString());
                e.printStackTrace();
            }
        }
    }

    /** */
    public Locale getLocale () {
        return _locale;
    }
    
    /** */
    public String getProperty (Unit unit, String property) {
        Map<String,String> metadata = _metadata.get(unit);
        if (metadata != null) {
            return metadata.get(property.toUpperCase());
        }
        return null;
    }
    
    /** */
    public void putProperty (Unit unit, String property, String value) {
        Map<String,String> metadata = _metadata.get(unit);
        if (metadata == null) {
            metadata = new HashMap<String,String>();
            _metadata.put(unit, metadata);
        }
        metadata.put(property.toUpperCase(), value);
    }
    
    /** */
    public Map<String,String> getProperties (Unit unit) {
        Map<String,String> metadata = _metadata.get(unit);
        if (metadata != null) {
            return Collections.unmodifiableMap(metadata);
        }
        return null;
    }
}
