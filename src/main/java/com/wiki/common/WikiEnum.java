package com.wiki.common;

public enum WikiEnum {
    msg_A001("A001", "系统出现异常，请联系管理员");


    private String code;
    private String name;

    WikiEnum(String code, String name) {

        this.code = code;

        this.name = name;

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
