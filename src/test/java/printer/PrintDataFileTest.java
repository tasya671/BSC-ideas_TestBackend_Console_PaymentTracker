package printer;

import data.MoneyRecord;
import data.RecordStore;
import data.RecordStoreImpl;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Currency;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.*;

public class PrintDataFileTest {

    private RecordStore<MoneyRecord> recordStore = new RecordStoreImpl();

    @Before
    public void setUpStreams() {
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
        String fileName = "src/test/java/printer/testprint.txt";
        PrintDataFile printDataFile = new PrintDataFile(recordStore,fileName);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<PrintDataConsole> future = executorService.submit(printDataFile);
        try { TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) { e.printStackTrace(); }
        future.cancel(true);
        executorService.shutdown();
        String expected ="\n**************************\n"+
                "Current state:\n"+
                "600 EUR\n" +
                "300 USD\n" +
                "876 CAD (USD 662,88)\n" +
                "260 RUB (USD 3,78)\n";

        String actual ="";
        try {
            Path path = Paths.get(fileName);
            actual = new String(Files.readAllBytes(path), UTF_8);}
        catch (IOException exp){}

        assertEquals(expected, actual);

    }
}