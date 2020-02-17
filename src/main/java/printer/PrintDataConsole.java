package printer;

import data.MoneyRecord;
import data.RecordStore;

import java.util.concurrent.TimeUnit;

public class PrintDataConsole extends PrintDataStore {

    public PrintDataConsole(RecordStore<MoneyRecord> recordStore) {
        super(recordStore);
    }

    public Object call() {
        try {
            while (!Thread.interrupted()){
                synchronized (System.out){
                    System.out.print(printData()); }
                TimeUnit.MINUTES.sleep(1);}
        } catch (InterruptedException e) {
            //e.printStackTrace();
        }
        return null;
    }
}
