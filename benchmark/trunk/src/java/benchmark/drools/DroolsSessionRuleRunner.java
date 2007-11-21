package benchmark.drools;

import java.io.PrintStream;
import java.util.Map;

import org.drools.StatefulSession;

import benchmark.SessionRuleRunner;
import benchmark.SessionStats;

public class DroolsSessionRuleRunner
    implements
    SessionRuleRunner {

    private Map<String, String> conf;

    private SessionStats        stats;

    private long                startDataLoadTime;

    private StatefulSession     session;

    public DroolsSessionRuleRunner(Map<String, String> conf,
                                   SessionStats stats,
                                   StatefulSession session) {
        this.startDataLoadTime = System.currentTimeMillis();
        this.stats = stats;
        this.conf = conf;
        this.session = session;
    }

    /* (non-Javadoc)
     * @see com.javarepository.test.RuleRunner#insertObject(java.lang.Object)
     */
    public void insert(Object object) {
        session.insert( object );
    }

    /* (non-Javadoc)
     * @see com.javarepository.test.RuleRunner#setGlobal(java.lang.String, java.lang.Object)
     */
    public void setGlobal(String name,
                          Object value) {
        session.setGlobal( name,
                           value );
    }

    /* (non-Javadoc)
     * @see com.javarepository.test.RuleRunner#run()
     */
    public void run() {
        this.stats.setDataLoadTime( System.currentTimeMillis() - this.startDataLoadTime );
        long startRuleFiringTime = System.currentTimeMillis();
        session.fireAllRules();
        // must clode stdout
        ((PrintStream) session.getGlobal( "stdout" )).close();
        session.dispose();
        this.stats.setRulesFiringTime( System.currentTimeMillis() - startRuleFiringTime );
    }

    public SessionStats getSingleStats() {
        return this.stats;
    }

}
