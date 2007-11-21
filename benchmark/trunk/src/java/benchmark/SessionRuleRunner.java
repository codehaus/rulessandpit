package benchmark;

public interface SessionRuleRunner {

    public void insert(Object object);

    public void setGlobal(String name,
                          Object value);

    public void run();

    public SessionStats getSingleStats();

}