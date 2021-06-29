package com.wiki.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wiki.entity.Ebook;

import java.util.List;
import java.util.Map;

public interface GeneratorMapper extends BaseMapper<Ebook> {

    Map<String, String> queryTable(String tableName);

    List<Map<String, String>> queryColumns(String tableName);

}
