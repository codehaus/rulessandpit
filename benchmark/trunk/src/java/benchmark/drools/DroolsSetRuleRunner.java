package benchmark.drools;

import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Map;

import org.drools.RuleBase;
import org.drools.RuleBaseConfiguration;
import org.drools.RuleBaseFactory;
import org.drools.compiler.PackageBuilder;

import benchmark.SessionRuleRunner;
import benchmark.SessionStats;
import benchmark.SetRuleRunner;
import benchmark.SetStats;
import benchmark.banking.BankingBenchmarkRunner;

public class DroolsSetRuleRunner
    implements
    SetRuleRunner {
    private Map<String, String> conf;

    private static RuleBase     ruleBase;

    private SetStats            stats;
    
    private int                 counter;

    public DroolsSetRuleRunner(Map<String, String> conf) {
        this.conf = conf;
        this.stats = new SetStats( conf.get( "benchmark-name" ),
                                   "drools" );
    }

    /* (non-Javadoc)
     * @see com.javarepository.test.RuleRunner#buildRuleBase()
     */
    private RuleBase buildRuleBase(SessionStats stats) {
        RuleBase ruleBase = null;
        long start = System.currentTimeMillis();
        try {
            PackageBuilder builder = new PackageBuilder();
            String[] files = this.conf.get( "rule-files" ).split( "," );
            for ( String string : files ) {
                builder.addPackageFromDrl( new InputStreamReader( BankingBenchmarkRunner.class.getResourceAsStream( string.trim() ) ) );
            }

            RuleBaseConfiguration conf = new RuleBaseConfiguration();
            conf.setShadowProxy( false );

            ruleBase = RuleBaseFactory.newRuleBase();
            ruleBase.addPackage( builder.getPackage() );
        } catch ( Exception e ) {
            throw new RuntimeException( e );
        }
        stats.setRuleLoadTime( System.currentTimeMillis() - start );
        return ruleBase;
    }

    /* (non-Javadoc)
     * @see com.javarepository.test.RuleRunner#createSession()
     */
    public SessionRuleRunner createSession() {
        SessionStats sessionStats = new SessionStats();
        // load the rulebase the first team, and each time if instructed
        if ( this.ruleBase == null || Boolean.parseBoolean( this.conf.get( "forceRuleBaseReload" ) ) ) {
            this.ruleBase = buildRuleBase( sessionStats );
        }

        DroolsSessionRuleRunner session = new DroolsSessionRuleRunner( this.conf,
                                                               sessionStats,
                                                               this.ruleBase.newStatefulSession() );
        try {
            session.setGlobal( "stdout",
                               new PrintStream( (String) conf.get( "stdout" ) + counter++ + ".txt" ) );
        } catch ( FileNotFoundException e ) {
            // wrap as runtime exception
            throw new RuntimeException( "unable to set PrintStream",
                                        e );
        }
        this.stats.add( sessionStats );

        return session;
    }

    public SetStats getStats() {
        return this.stats;
    }

}
