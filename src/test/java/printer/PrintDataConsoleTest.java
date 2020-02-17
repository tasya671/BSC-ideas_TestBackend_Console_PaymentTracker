package printer;

import com.sun.deploy.util.Waiter;
import data.MoneyRecord;
import data.RecordStore;
import data.RecordStoreImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class PrintDataConsoleTest {

    private RecordStore<MoneyRecord> recordStore = new RecordStoreImpl();
    private PrintDataConsole console = new PrintDataConsole(recordStore);
    private ByteArrayOutputStream stream;

    @Before
    public void setUpStreams() {
        stream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(stream));
        MoneyRecord record1 = new MoneyRecord(new BigDecimal(600), Currency.getInstance("EUR"));
        MoneyRecord record2 = new MoneyRecord(new BigDecimal(300), Currency.getInstance("USD"));
        MoneyRecord record3 = new MoneyRecord(new BigDecimal(260), Currency.getInstance("RUB"));
        MoneyRecord record4 = new MoneyRecord(new BigDecimal(876), Currency.getInstance("CAD"));
        MoneyRecord record5 = new MoneyRecord(new BigDecimal(0), Currency.getInstance("GEL"));

        recordStore.addItem(record1);
        recordStore.addItem(record2);
        recordStore.addItem(record3);
        recordStore.addItem(record4);
        recordStore.addItem(record5);
    }


    @Test
    public void call() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<PrintDataConsole> future = executorService.submit(console);
        try { TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) { e.printStackTrace(); }
        future.cancel(true);
        executorService.shutdown();
        String actual = stream.toString();
        String expected ="\n**************************\n"+
                "Current state:\n"+
                "600 EUR\n" +
                "300 USD\n" +
                "876 CAD (USD 662,88)\n" +
                "260 RUB (USD 3,78)\n";
        assertEquals(expected, actual);
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
    }
}