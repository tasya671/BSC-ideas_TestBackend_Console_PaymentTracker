package data;

import java.util.List;

public interface RecordStore<T> {

    void addItem(T item);
    List<T> getAllItems ();

}