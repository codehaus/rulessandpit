package benchmark.banking;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import benchmark.Benchmark;
import benchmark.SessionRuleRunner;
import benchmark.banking.model.Account;
import benchmark.banking.model.AccountingPeriod;
import benchmark.banking.model.Cashflow;
import benchmark.banking.model.UpdatingAccount;
import benchmark.utils.SimpleDate;

public class BankingBenchmarkRunner
    implements
    Benchmark {
    private Map conf;
    public BankingBenchmarkRunner(Map conf) {
        this.conf = conf;
    }

    public List<Cashflow> getAllCashflows(Account acc1) throws Exception {
        List<Cashflow> cashflows = new ArrayList<Cashflow>();

        Date startDate = new SimpleDate( "01/01/2007" );
        Date endDate = new SimpleDate( "31/12/2007" );

        long approxCashFlows = Integer.parseInt( (String) this.conf.get( "approxCashFlows" ) );
        long interval = (endDate.getTime() - startDate.getTime()) / (approxCashFlows / 17);

        for ( Date date = startDate; !date.after( endDate ); date = new Date( date.getTime() + interval ) ) {
            int[] credits = new int[]{800, 475, 422, 300, 150, 200, 50, 120, 20, 10};
            int[] debits = new int[]{760, 300, 290, 200, 50, 50, 30};

            for ( int credit : credits ) {
                Cashflow c = new Cashflow( date,
                                           credit,
                                           Cashflow.CREDIT,
                                           acc1 );
                cashflows.add( c );
            }

            for ( int debit : debits ) {
                Cashflow c = new Cashflow( date,
                                           debit,
                                           Cashflow.DEBIT,
                                           acc1 );
                cashflows.add( c );
            }
        }

        return cashflows;
    }

    public List<AccountingPeriod> getAccountingPeriods() throws Exception {
        List<AccountingPeriod> accountingPeriods = new ArrayList<AccountingPeriod>();

        accountingPeriods.add( new AccountingPeriod( new SimpleDate( "01/01/2007" ),
                                                     new SimpleDate( "31/03/2007" ) ) );
        accountingPeriods.add( new AccountingPeriod( new SimpleDate( "01/04/2007" ),
                                                     new SimpleDate( "30/06/2007" ) ) );
        accountingPeriods.add( new AccountingPeriod( new SimpleDate( "01/07/2007" ),
                                                     new SimpleDate( "30/09/2007" ) ) );
        accountingPeriods.add( new AccountingPeriod( new SimpleDate( "01/10/2007" ),
                                                     new SimpleDate( "31/12/2007" ) ) );

        return accountingPeriods;
    }
    
    private static SimpleDateFormat sdf =  new SimpleDateFormat("hh:mm:ss:ms");

    public void runSession(SessionRuleRunner session,
                           PrintStream stream) throws Exception {
        stream.println( "Running Speed Test [" + sdf.format( new Date() ) + "]" );
        stream.println( "--------------------" );

        Account acc1 = new Account( 1 );

        UpdatingAccount updatingAccount = new UpdatingAccount( 1,
                                                               acc1 );

        List<AccountingPeriod> accountingPeriods = getAccountingPeriods();
        List<Cashflow> cashflows = getAllCashflows( acc1 );

        stream.println( "cash flows: " + cashflows.size() + "\n" );

        for ( Object fact : accountingPeriods ) {
            session.insert( fact );
        }

        for ( Object fact : cashflows ) {
            session.insert( fact );
        }

        session.insert( updatingAccount );

        session.run();
    }

}
