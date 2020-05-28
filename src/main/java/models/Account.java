package models;

import enums.ExpTypeEnum;

public class Account {

    private int id;
    private int port;
    private Exp baseExp = new Exp(ExpTypeEnum.BASE);
    private Exp jobExp = new Exp(ExpTypeEnum.JOB);

    public Account(int id) {
        this.id = id;
    }

    //------------------------
    // GET / SET
    //------------------------
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Exp getBaseExp() {
        return baseExp;
    }

    public void setBaseExp(Exp baseExp) {
        this.baseExp = baseExp;
    }

    public Exp getJobExp() {
        return jobExp;
    }

    public void setJobExp(Exp jobExp) {
        this.jobExp = jobExp;
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
