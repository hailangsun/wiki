package com.wiki.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("reb_card")
public class RebCard {

    private Integer id;
    private String author;
    private String browseCount;
    private String mode;
    private String title;
    private String cover;
    private String classify;

}
