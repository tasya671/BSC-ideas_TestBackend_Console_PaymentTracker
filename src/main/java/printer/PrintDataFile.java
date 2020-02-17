package printer;

import data.MoneyRecord;
import data.RecordStore;

import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class PrintDataFile extends PrintDataStore {
    private final static String DEFAULTFILE = "pay_history.txt";
    private String fileName;

    public PrintDataFile(RecordStore<MoneyRecord> recordStore, String name) {
        super(recordStore);
        if(name.equalsIgnoreCase("") | name == null)
            this.fileName = DEFAULTFILE;
        else
            this.fileName = name;
    }

    @Override
    public Object call() {
        try (FileWriter writer = new FileWriter(fileName, true)) {
            while (!Thread.interrupted()) {
                writer.write(printData());
                writer.flush();
                TimeUnit.MINUTES.sleep(1);
            }
        }
        catch (InterruptedException exp){
            //exp.printStackTrace();
        }
        catch (IOException e) { System.out.println("Invalid file"); }
        return null;
    }
}
