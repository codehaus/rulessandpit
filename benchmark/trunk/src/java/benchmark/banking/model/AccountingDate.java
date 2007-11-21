package benchmark.banking.model;

import java.util.Date;

public class AccountingDate {
    private Date date;

    public AccountingDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
