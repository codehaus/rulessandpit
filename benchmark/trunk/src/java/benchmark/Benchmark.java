package benchmark;

import java.io.PrintStream;

public interface Benchmark {
    public void runSession(SessionRuleRunner session,
                           PrintStream stream) throws Exception;
}
