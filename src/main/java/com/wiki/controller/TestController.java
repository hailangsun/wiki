package com.wiki.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wiki.entity.Ebook;
import com.wiki.mapper.EbookMapper;
import com.wiki.common.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestController {

    private static final Logger LOG = LoggerFactory.getLogger(TestController.class);

    @Value("${test.hello:HHH}")
    private String test;

    @Resource
    EbookMapper ebookMapper;

    /**
     * GET, POST, PUT, DELETE
     *
     * /user?id=1
     * /user/1
     * @return
     */
    // @PostMapping
    // @PutMapping
    // @DeleteMapping
    // @RequestMapping(value = "/user/1", method = RequestMethod.GET)
    // @RequestMapping(value = "/user/1", method = RequestMethod.DELETE)
    @GetMapping("/hello")
    public R hello() {
//        StringUtils.isEmpty()
        Page<Ebook> page = new Page<>(1, 10);
        Page<Ebook> ebookPage = ebookMapper.selectPage(page, null);
        return R.ok().put("content",ebookPage.getRecords());
    }


    @PostMapping("/hello1")
    public String hello1(String post) {
        return "Hello World!" + post;
    }

}
