package com.wiki.test;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wiki.WikiApplicationTests;
import com.wiki.entity.Ebook;
import com.wiki.mapper.EbookMapper;
import com.wiki.mapper.TestMapper;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.*;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test1 extends WikiApplicationTests {


    @Resource
    TestMapper testMapper;

    @Resource
    EbookMapper ebookMapper;

    @Test
    public void test1() {
        List<Map<String, Object>> test = testMapper.getTest();
        System.out.println(test.size());
//        Page<Ebook> page = new Page<>(1, 1);

//        Page<Ebook> ebookPage = ebookMapper.selectPage(page, null);
//        System.out.println(ebookPage.getSize());
    }

    public static void main(String[] args) {
        try {
            BigDecimal grcount = new BigDecimal("433");
            Integer divide = grcount.divide(BigDecimal.valueOf(500),0,BigDecimal.ROUND_HALF_UP).intValue();
            System.out.println(divide);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Boolean isLessUseWord(String org) throws UnsupportedEncodingException {
        return !org.equals(new String(org.getBytes("gb18030"),"gb2312"));
    }



    public static boolean checkWj(String fileName)
    {
        Pattern pattern = Pattern.compile("^[A-Z]{3}\\d{12}$");
        Matcher matcher = pattern.matcher(fileName);
        return matcher.find();
    }

    public static boolean GATidCard(String fileName)
    {
        Pattern pattern = Pattern.compile("^8[123]0000(?:19|20)\\d{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[12]\\d|3[01])\\d{3}[\\dX]$");
        Matcher matcher = pattern.matcher(fileName);
        return matcher.find();
    }

    public static boolean checkGa(String fileName)
    {
        Pattern pattern = Pattern.compile("^8[123]0000(?:19|20)\\d{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[12]\\d|3[01])\\d{3}[\\dX]$");
        Matcher matcher = pattern.matcher(fileName);
        if(matcher.find()){//台湾居民证
            return matcher.find();
        }
        pattern = Pattern.compile("^8[123]0000(?:19|20)\\d{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[12]\\d|3[01])\\d{3}[\\dX]$");
        matcher = pattern.matcher(fileName);
        if(matcher.find()){//中国香港
            return matcher.find();
        }

        pattern = Pattern.compile("^[1|5|7][0-9]{6}\\([0-9Aa]\\)");
        matcher = pattern.matcher(fileName);
        if(matcher.find()){//中国澳门
            return matcher.find();
        }

        return false;
    }
}
