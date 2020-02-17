package printer;

import data.MoneyRecord;
import data.RecordStore;
import data.RecordStoreImpl;
import org.junit.Test;

import static org.junit.Assert.*;

public class PrintDataFactoryTest {

    private RecordStore<MoneyRecord> recordStore = new RecordStoreImpl();

    @Test
    public void create_PrintConsole() {
        PrintDataConsole expected = new PrintDataConsole(recordStore);
        assertTrue(PrintDataFactory.create("Console", recordStore,"").getClass() == expected.getClass());
    }

    @Test
    public void create_PrintFile() {
        PrintDataFile expected = new PrintDataFile(recordStore, "");
        assertTrue(PrintDataFactory.create("File", recordStore,"").getClass() == expected.getClass());
    }

    @Test
    public void create_Default() {
        PrintDataConsole expected = new PrintDataConsole(recordStore);
        assertTrue(PrintDataFactory.create("", recordStore,"").getClass() == expected.getClass());
    }

}