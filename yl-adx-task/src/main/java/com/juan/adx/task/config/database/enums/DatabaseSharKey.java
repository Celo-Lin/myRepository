package com.juan.adx.task.config.database.enums;

public enum DatabaseSharKey {
    SHAR_DATE_TIME("fctime", "根据日期分表的关键字段"),
    SHAR_ID("fid", "根据取模分表的关键字段");

    String name;
    String desc;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    DatabaseSharKey(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }
}
