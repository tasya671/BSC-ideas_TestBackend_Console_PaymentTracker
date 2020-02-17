package parser;

import data.MoneyRecord;
import data.RecordStore;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Currency;

public class Parser {

    RecordStore<MoneyRecord> recordStore;

    public Parser(RecordStore<MoneyRecord> recordStore) { //not null cheking
        this.recordStore = recordStore;
    }

    public void putOneRecord(String str) throws IllegalArgumentException {
        String [] element = str.trim().split(" ");
        if (element.length==2){
            Currency currency = Currency.getInstance(element[0]);
            BigDecimal amount = new BigDecimal(Integer.parseInt(element[1]));
            recordStore.addItem(new MoneyRecord(amount, currency));
        } else throw new IllegalArgumentException("Invalid data format");
    }

    public void putAllFromFiles (String fileName){
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))){
            String current;
            while ((current=reader.readLine())!=null) {putOneRecord(current); }
        }
        catch (IOException e) { e.printStackTrace(); }
    }
}
