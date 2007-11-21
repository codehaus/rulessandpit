/**
 * 
 */
package benchmark;

public class SessionStats {
    private long ruleLoadTime;

    private long dataLoadTime;

    private long rulesFiringTime;

    private int  activationsFired;

    public long getRuleLoadTime() {
        return ruleLoadTime;
    }

    public void setRuleLoadTime(long ruleLoadTime) {
        this.ruleLoadTime = ruleLoadTime;
    }

    public long getDataLoadTime() {
        return dataLoadTime;
    }

    public void setDataLoadTime(long dataLoadTime) {
        this.dataLoadTime = dataLoadTime;
    }

    public void setRulesFiringTime(long time) {
        this.rulesFiringTime = time;
    }

    public long getRulesFiringTime() {
        return this.rulesFiringTime;
    }

    public void setActivationsFired(int activationsFired) {
        this.activationsFired = activationsFired;
    }

    public int getActivationsFired() {
        return this.activationsFired;
    }

}