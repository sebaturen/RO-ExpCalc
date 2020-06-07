package com.ro.models;

import com.ro.enums.ExpTypeEnum;

import java.util.Date;

public class Exp {

    private ExpTypeEnum type;
    private Date startTimeStamp;
    private long totalExp;
    private long accumulativeExp;
    private long lastExp;
    private long lvl;

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
        if (startTimeStamp == null) {
            startTimeStamp = new Date();
        }
        this.accumulativeExp += lastExp;
        this.lastExp = lastExp;
    }

    public float getExpHour() {
        if (startTimeStamp == null) {
            return 0;
        }
        Date now = new Date();
        int diffSeconds = (int) (now.getTime() - startTimeStamp.getTime())/1000;
        if (diffSeconds == 0) {
            return this.lastExp;
        } else {
            return (this.accumulativeExp / diffSeconds) * 3600;
        }
    }

    public long getLvl() {
        return lvl;
    }

    public void setLvl(long lvl) {
        this.lvl = lvl;
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
