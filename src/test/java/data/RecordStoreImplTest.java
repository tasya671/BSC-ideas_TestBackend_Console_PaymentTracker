package data;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;

import static org.junit.Assert.*;

public class RecordStoreImplTest {

    private RecordStoreImpl recordStore;
    private MoneyRecord record1 = new MoneyRecord(new BigDecimal(600), Currency.getInstance("EUR"));
    private MoneyRecord record2 = new MoneyRecord(new BigDecimal(300), Currency.getInstance("USD"));
    private MoneyRecord record3 = new MoneyRecord(new BigDecimal(260), Currency.getInstance("RUB"));
    private MoneyRecord record4 = new MoneyRecord(new BigDecimal(876), Currency.getInstance("CAD"));

    @Before
    public void setUp() throws Exception {
        recordStore = new RecordStoreImpl();
    }

    @After
    public void tearDown() throws Exception {
        recordStore = null;
    }

    @Test
    public void addItem_NEWElement() {
        recordStore.addItem(record1);
        assertTrue(recordStore.getAllItems().contains(record1));
    }

    @Test
    public void addItem_UpdateData(){
        recordStore.addItem(record1);
        recordStore.addItem(record2);
        recordStore.addItem(record1);
        MoneyRecord record = new MoneyRecord(new BigDecimal(1200), Currency.getInstance("EUR"));
        assertTrue(recordStore.getAllItems().contains(record));
    }

    @Test
    public void getAllItems() {
        List<MoneyRecord> expected = new ArrayList(Arrays.asList(record1, record2, record3, record4));
        recordStore.addItem(record1);
        recordStore.addItem(record2);
        recordStore.addItem(record3);
        recordStore.addItem(record4);
        List<MoneyRecord> actual = recordStore.getAllItems();
        assertTrue(actual.size() == expected.size() && actual.containsAll(expected));
    }
}