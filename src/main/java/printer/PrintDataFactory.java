package printer;

import data.MoneyRecord;
import data.RecordStore;

public class PrintDataFactory {

    public final static String CONSOLE = "Console";
    public final static String FILE = "File";

    public static PrintDataStore create(String type, RecordStore<MoneyRecord> store, String fileName){
        switch (type){
            case CONSOLE:
                return new PrintDataConsole(store);
            case FILE:
                return new PrintDataFile(store, fileName);
            default:
                return new PrintDataConsole(store);
        }
    }
}