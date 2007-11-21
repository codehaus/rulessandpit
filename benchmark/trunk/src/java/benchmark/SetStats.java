package benchmark;

import java.util.ArrayList;
import java.util.Iterator;

public class SetStats
    implements
    Iterable<SessionStats> {
    private String                      benchmarkName;
    private String                      engineName;
    public java.util.List<SessionStats> list = new ArrayList<SessionStats>();

    public SetStats(String benchmarkName,
                    String engineName) {
        this.benchmarkName = benchmarkName;
        this.engineName = engineName;
    }

    public void add(SessionStats singleStats) {
        this.list.add( singleStats );
    }

    public Iterator<SessionStats> iterator() {
        return this.list.iterator();
    }

    public int getIterationCount() {
        return this.list.size();
    }

    public double getRuleLoadTimeAverage() {
        int total = 0;
        int populatedCount = 0;

        for ( SessionStats stats : list ) {
            if ( stats.getRuleLoadTime() > 0 ) {
                populatedCount++;
                total += stats.getRuleLoadTime();
            }
        }

        if ( populatedCount > 1 ) {
            return total / this.list.size();
        } else {
            return total;
        }
    }

    public int getDataLoadTimeAverage() {
        int total = 0;
        for ( SessionStats stats : list ) {
            total += stats.getDataLoadTime();
        }
        return total / this.list.size();
    }

    public int getRulesFiringTimeAverage() {
        int total = 0;
        for ( SessionStats stats : list ) {
            total += stats.getRulesFiringTime();
        }
        return total / this.list.size();
    }

    public String iterationsToString() {
        StringBuilder builder = new StringBuilder();

        builder.append( "---------------------\n" );
        builder.append( "benchmark : " + this.benchmarkName + "\n" );
        builder.append( "engine : " + this.engineName + "\n" );
        builder.append( "Total Iterations : " + getIterationCount() + "\n" );
        builder.append( "---------------------\n\n" );

        int i = 0;
        for ( SessionStats singleStats : this ) {
            builder.append( "Iteration : " + i++ + "\n" );
            builder.append( "-------------\n" );
            builder.append( "RuleLoadTime   : " + singleStats.getRuleLoadTime() + "\n" );
            builder.append( "DataLoadTime   : " + singleStats.getDataLoadTime() + "\n" );
            builder.append( "ruleFiringTime : " + singleStats.getRulesFiringTime() + "\n\n" );
        }
        return builder.toString();

    }

    public String averagesToString() {
        StringBuilder builder = new StringBuilder();
        builder.append( "--------\n" );
        builder.append( "Averages\n" );
        builder.append( "--------\n" );
        builder.append( "RuleLoadTimeAverage   : " + getRuleLoadTimeAverage() + "\n" );
        builder.append( "DataLoadTimeAverage   : " + getDataLoadTimeAverage() + "\n" );
        builder.append( "ruleFiringTimeAverage : " + getRulesFiringTimeAverage() + "\n" );
        return builder.toString();
    }

    public String toString() {
        return iterationsToString() + averagesToString();
    }
}
