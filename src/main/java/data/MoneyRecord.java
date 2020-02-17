package data;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

public class MoneyRecord {

    private BigDecimal amount;
    private Currency currency;

    public MoneyRecord(BigDecimal amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public BigDecimal getAmount() { return amount; }

    public Currency getCurrency() { return currency; }

    public synchronized void setAmount(BigDecimal amount) { this.amount = amount; }

    public void setCurrency(Currency currency) { this.currency = currency; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MoneyRecord record = (MoneyRecord) o;
        return Objects.equals(amount, record.amount) &&
                Objects.equals(currency, record.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currency);
    }
}
