package com.ro.models;

import com.ro.enums.ExpTypeEnum;

import java.util.Date;

public class Exp {

    private ExpTypeEnum type;
    private Date startTimeStamp = new Date();
    private long totalExp;
    private long accumulativeExp;
    private long lastExp;

    public Exp(ExpTypeEnum type) {
        this.type = type;
    }

    //------------------------
    // GET / SET
    //------------------------
    public ExpTypeEnum getType() {
        return type;
    }

    public long getTotalExp() {
        return totalExp;
    }

    public long getLastExp() {
        return lastExp;
    }

    public void setTotalExp(long totalExp) {
        this.totalExp = totalExp;
    }

    public void setLastExp(long lastExp) {
        this.accumulativeExp += lastExp;
        this.lastExp = lastExp;
    }

    public float getExpHour() {
        Date now = new Date();
        int diffSeconds = (int) (now.getTime() - startTimeStamp.getTime())/1000;
        if (diffSeconds == 0) {
            return this.lastExp;
        } else {
            return (this.accumulativeExp / diffSeconds) * 3600;
        }
    }

    @Override
    public String toString() {
        return "Exp{" +
                "type=" + type +
                ", totalExp=" + totalExp +
                ", lastExp=" + lastExp +
                '}';
    }
}
