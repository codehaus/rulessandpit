package benchmark.banking.model;

public class UpdatingAccount {
    private int     accountNo;
    private Account account;

    public UpdatingAccount(int accountNo,
                           Account account) {
        this.accountNo = accountNo;
        this.account = account;
    }

    public int getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(int accountNo) {
        this.accountNo = accountNo;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
