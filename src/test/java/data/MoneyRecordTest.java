package data;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.*;

public class MoneyRecordTest {

    @Test
    public void testEquals_SameObjects_ReturnTrue() {
        MoneyRecord moneyOne = new MoneyRecord(new BigDecimal(600), Currency.getInstance("RUB"));
        MoneyRecord moneyTwo = new MoneyRecord(new BigDecimal(600), Currency.getInstance("RUB"));
        assertTrue(moneyOne.equals(moneyTwo));
    }

    @Test
    public void testEquals_DifferentObjects_ReturnFalse() {
        MoneyRecord moneyOne = new MoneyRecord(new BigDecimal(600), Currency.getInstance("USD"));
        MoneyRecord moneyTwo = new MoneyRecord(new BigDecimal(600), Currency.getInstance("RUB"));
        assertFalse(moneyOne.equals(moneyTwo));
    }

    @Test
    public void testEquals_NULLObject_ReturnFalse() {
        MoneyRecord moneyOne = new MoneyRecord(new BigDecimal(600), Currency.getInstance("RUB"));
        assertFalse(moneyOne.equals(null));
    }

}