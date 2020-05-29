package com.ro.enums;

import java.util.HashMap;
import java.util.Map;

public enum ExpTypeEnum {
    BASE(01),
    JOB(02);

    private int value;
    private static Map map = new HashMap();

    private ExpTypeEnum(int type) {
        this.value = type;
    }

    static {
        for (ExpTypeEnum type : ExpTypeEnum.values()) {
            map.put(type.value, type);
        }
    }

    public static ExpTypeEnum valueOf(int pageType) {
        return (ExpTypeEnum) map.get(pageType);
    }
}
