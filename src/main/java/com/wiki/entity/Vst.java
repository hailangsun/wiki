package com.wiki.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Vst {
    private String id;
    private String phone;
    private String wenti;
    private String wjlj;
    //1图片
    private String flagtype;
    private String battchid;
    private String filename;
    private Date sysdate;
}
