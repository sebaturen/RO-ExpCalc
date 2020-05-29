package com.ro.models;

import com.ro.enums.ExpTypeEnum;

public class Account {

    private long id;
    private int port;
    private Exp baseExp = new Exp(ExpTypeEnum.BASE);
    private Exp jobExp = new Exp(ExpTypeEnum.JOB);

    public Account(long id) {
        this.id = id;
    }

    //------------------------
    // GET / SET
    //------------------------
    public long getId() {
        return id;
    }

    public Exp getBaseExp() {
        return baseExp;
    }

    public Exp getJobExp() {
        return jobExp;
    }

    public void setBaseExp(long baseExp) {
        this.baseExp.setLastExp(baseExp);
    }

    public void setJobExp(long jobExp) {
        this.jobExp.setLastExp(jobExp);
    }

    public void setTotalBaseExp(long totalBaseExp) {
        this.baseExp.setTotalExp(totalBaseExp);
    }

    public void setTotalJobExp(long totalJobExp) {
        this.baseExp.setTotalExp(totalJobExp);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", baseExp=" + baseExp +
                ", jobExp=" + jobExp +
                '}';
    }
}
