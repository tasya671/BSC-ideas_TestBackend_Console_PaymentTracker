package printer;

import data.MoneyRecord;
import data.RecordStore;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Set;
import java.util.concurrent.Callable;

public abstract class PrintDataStore implements Callable {

    private static Set<Currency> currencies = Currency.getAvailableCurrencies();
    private RecordStore<MoneyRecord> recordStore;

    public PrintDataStore(RecordStore<MoneyRecord> recordStore) {
        this.recordStore = recordStore;
    }

    public String printData() {
        StringBuilder builder = new StringBuilder();
        if (recordStore.getAllItems().size() == 0)
            return builder.toString();
        builder.append("\n**************************\n");
        builder.append("Current state:\n");
        for (MoneyRecord entry: recordStore.getAllItems()) {
            if (!entry.getAmount().equals(new BigDecimal(0))) {
                builder.append(entry.getAmount() + " ");
                builder.append(entry.getCurrency());
                if (DollarRateData.dollarRate.containsKey(entry.getCurrency().toString()) & !entry.getCurrency().toString().equals("USD")) {
                    builder.append(" (USD ");
                    builder.append(String.format("%.2f", entry.getAmount().doubleValue()/(DollarRateData.dollarRate.get(entry.getCurrency().toString()))));
                    builder.append(")\n");
                } else
                    builder.append("\n");
            }
        }
        return builder.toString();
    }
}

