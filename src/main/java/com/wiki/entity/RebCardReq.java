package com.wiki.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
public class RebCardReq {

    private Integer id;
    private Integer page;
    private Integer pageSize;
    private String author;
    private String browseCount;
    private String mode;
    private String title;
    private String cover;
    private String classify;


}
