package com.wiki.test;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wiki.WikiApplicationTests;
import com.wiki.entity.Ebook;
import com.wiki.mapper.EbookMapper;
import com.wiki.mapper.TestMapper;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.sql.*;

public class Test1 extends WikiApplicationTests {


    @Resource
    TestMapper testMapper;

    @Resource
    EbookMapper ebookMapper;

    @Test
    public void test1() {
//        List<Map<String, Object>> test = testMapper.getTest();
//        System.out.println(test.size());
        Page<Ebook> page = new Page<>(1, 1);

        Page<Ebook> ebookPage = ebookMapper.selectPage(page, null);
        System.out.println(ebookPage.getSize());
    }


}
