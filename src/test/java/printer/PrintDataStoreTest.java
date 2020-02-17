package printer;

import data.MoneyRecord;
import data.RecordStore;
import data.RecordStoreImpl;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.*;

public class PrintDataStoreTest {

    @Test
    public void printData() {
        RecordStore<MoneyRecord> store = new RecordStoreImpl();

        MoneyRecord record1 = new MoneyRecord(new BigDecimal(600), Currency.getInstance("EUR"));
        MoneyRecord record2 = new MoneyRecord(new BigDecimal(300), Currency.getInstance("USD"));
        MoneyRecord record3 = new MoneyRecord(new BigDecimal(260), Currency.getInstance("RUB"));
        MoneyRecord record4 = new MoneyRecord(new BigDecimal(876), Currency.getInstance("CAD"));
        MoneyRecord record5 = new MoneyRecord(new BigDecimal(0), Currency.getInstance("GEL"));

        store.addItem(record1);
        store.addItem(record2);
        store.addItem(record3);
        store.addItem(record4);
        store.addItem(record5);

        PrintDataStore printDataStore = new PrintDataConsole(store);

        String expected ="\n**************************\n"+
                         "Current state:\n"+
                         "600 EUR\n" +
                         "300 USD\n" +
                         "876 CAD (USD 662,88)\n" +
                         "260 RUB (USD 3,78)\n";

        String actual = printDataStore.printData();
        assertEquals(actual, expected);

    }

    @Test
    public void printData_NULLSIZE(){
        RecordStore<MoneyRecord> store = new RecordStoreImpl();
        PrintDataStore printDataStore = new PrintDataConsole(store);
        String expected ="";
        String actual = printDataStore.printData();
        assertEquals(actual, expected);
    }
}