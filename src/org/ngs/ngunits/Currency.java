package org.ngs.ngunits;

import org.ngs.ngunits.quantity.Money;
import org.ngs.ngunits.unit.BaseUnit;

public class Currency extends SystemOfUnits
{
    /**
     * The United State dollar currency.
     */
    public static final Unit<Money> USD = u(new BaseUnit(DELEGATE, "USD"));
    
    /**
     * The Australian Dollar currency unit.
     */
    public static final Unit<Money> AUD = u(USD.alternate("AUD"));

    /**
     * The Canadian Dollar currency unit.
     */
    public static final Unit<Money> CAD = u(USD.alternate("CAD"));

    /**
     * The China Yan currency.
     */
    public static final Unit<Money> CNY = u(USD.alternate("CNY"));

    /**
     * The Euro currency.
     */
    public static final Unit<Money> EUR = u(USD.alternate("EUR"));

    /**
     * The British Pound currency.
     */
    public static final Unit<Money> GBP = u(USD.alternate("GBP"));

    /**
     * The Japanese Yen currency.
     */
    public static final Unit<Money> JPY = u(USD.alternate("JPY"));

    /**
     * The Korean Republic Won currency.
     */
    public static final Unit<Money> KRW = u(USD.alternate("KRW"));

    /**
     * The Taiwanese dollar currency.
     */
    public static final Unit<Money> TWD = u(USD.alternate("TWD"));

    /**
     * The Indian Rupee currency.
     */
    public static final Unit<Money> INR = u(USD.alternate("INR"));
}
