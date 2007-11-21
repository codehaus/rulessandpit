package benchmark.banking.model;

import java.util.Date;

public class CurrentAccountingPeriod
    implements
    Comparable<CurrentAccountingPeriod> {
    private Date start;
    private Date end;

    public CurrentAccountingPeriod(AccountingPeriod current) {
        this.start = current.getStart();
        this.end = current.getEnd();
    }

    public int compareTo(CurrentAccountingPeriod accountingPeriod) {
        if ( this.start.after( accountingPeriod.getStart() ) ) {
            return 1;
        }
        if ( this.start.before( accountingPeriod.getStart() ) ) {
            return -1;
        }

        return 0;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String toString() {
        return "AccountingPeriod[" + "start=" + start + ",end=" + end + "]";
    }
}
