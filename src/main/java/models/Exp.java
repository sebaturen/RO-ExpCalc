package models;

import enums.ExpTypeEnum;

public class Exp {

    private ExpTypeEnum type;
    private int totalExp;
    private int lastExp;

    public Exp(ExpTypeEnum type) {
        this.type = type;
    }

    //------------------------
    // GET / SET
    //------------------------
    public ExpTypeEnum getType() {
        return type;
    }

    public void setType(ExpTypeEnum type) {
        this.type = type;
    }

    public int getTotalExp() {
        return totalExp;
    }

    public void setTotalExp(int totalExp) {
        this.totalExp = totalExp;
    }

    public int getLastExp() {
        return lastExp;
    }

    public void setLastExp(int lastExp) {
        this.lastExp = lastExp;
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
