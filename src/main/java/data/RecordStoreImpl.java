package data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class RecordStoreImpl implements RecordStore<MoneyRecord> {

    private ConcurrentHashMap<String, MoneyRecord> mapRecords;


    public RecordStoreImpl() {
        mapRecords = new ConcurrentHashMap<>();
    }

    @Override
    public void addItem(MoneyRecord item) {
        if (mapRecords.containsKey(item.getCurrency().toString())){
            MoneyRecord record = mapRecords.get(item.getCurrency().toString());
            record.setAmount(record.getAmount().add(item.getAmount()));
            mapRecords.put(record.getCurrency().toString(), record);
        } else
            mapRecords.put(item.getCurrency().toString(), item);
    }

    @Override
    public List<MoneyRecord> getAllItems() {
        return new ArrayList<>(mapRecords.values());
    }

}
