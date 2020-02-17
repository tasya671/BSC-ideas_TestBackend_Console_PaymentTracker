package parser;

import data.MoneyRecord;
import data.RecordStore;
import data.RecordStoreImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static org.junit.Assert.*;

public class ParserTest {

    private RecordStore<MoneyRecord> store;
    private Parser parser;

    @Before
    public void setUp(){
        store = new RecordStoreImpl();
        parser = new Parser(store);
    }

    @After
    public void clear(){
        store = null;
        parser = null;
    }

    @Test
    public void putOneRecord() {
        List<MoneyRecord> expected = new ArrayList<>();
        MoneyRecord record = new MoneyRecord(new BigDecimal(300), Currency.getInstance("USD"));
        expected.add(record);
        String rec ="USD 300";
        parser.putOneRecord(rec);
        List<MoneyRecord> actual = store.getAllItems();
        assertTrue(actual.size()==expected.size() && actual.containsAll(expected));
    }

    @Test (expected = IllegalArgumentException.class)
    public void putOneRecord_NO_VALID_DATA_CURRENCY(){
        String rec ="URR 300";
        parser.putOneRecord(rec);
    }

    @Test (expected = IllegalArgumentException.class)
    public void putOneRecord_NO_VALID_DATA_COUNT_ARG(){
        String rec ="URR 300 655";
        parser.putOneRecord(rec);
    }

    @Test
    public void putAllFromFiles() {
        MoneyRecord record1 = new MoneyRecord(new BigDecimal(560), Currency.getInstance("RUB"));
        MoneyRecord record2 = new MoneyRecord(new BigDecimal(333), Currency.getInstance("USD"));
        MoneyRecord record3 = new MoneyRecord(new BigDecimal(777), Currency.getInstance("CAD"));
        MoneyRecord record4 = new MoneyRecord(new BigDecimal(378), Currency.getInstance("EUR"));

        List<MoneyRecord> expected = new ArrayList<>();
        expected.add(record1);
        expected.add(record2);
        expected.add(record3);
        expected.add(record4);

        parser.putAllFromFiles("src/test/java/parser/paydata.txt");
        List<MoneyRecord> actual = store.getAllItems();
        assertTrue(actual.size()==expected.size() && actual.containsAll(expected));
    }
}