package benchmark.banking.model;

import java.util.Date;

public class Cashflow
    implements
    Comparable<Cashflow> {
    public static final int CREDIT = 0;
    public static final int DEBIT  = 1;

    private Date            date;
    private double          amount;
    private int             type;
    private Account         account;

    public Cashflow() {
    }

    public Cashflow(Date date,
                    double amount,
                    int type,
                    Account account) {
        this.date = date;
        this.amount = amount;
        this.type = type;
        this.account = account;
    }

    public int compareTo(Cashflow cashflow) {
        if ( this.date.after( cashflow.getDate() ) ) {
            return 1;
        }
        if ( this.date.before( cashflow.getDate() ) ) {
            return -1;
        }

        return 0;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String toString() {
        return "Cashflow[" + "account=" + account + "date=" + date + ",amount=" + amount + ",type=" + (type == CREDIT ? "Credit" : "Debit") + "]";
    }
}
