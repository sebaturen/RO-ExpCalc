package com.ro.models;

import com.ro.enums.ExpTypeEnum;

public class Character {

    private long id;
    private long port;
    private Exp baseExp = new Exp(ExpTypeEnum.BASE);
    private Exp jobExp = new Exp(ExpTypeEnum.JOB);

    public Character(long id) {
        this.id = id;
    }

    public long getId() {
        return this.id;
    }

    public Exp getBaseExp() {
        return baseExp;
    }

    public Exp getJobExp() {
        return jobExp;
    }

    public long getPort() {
        return port;
    }

    public void setPort(long port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "Character{" +
                "id=" + id +
                ", port=" + port +
                ", baseExp=" + baseExp +
                ", jobExp=" + jobExp +
                '}';
    }
}
