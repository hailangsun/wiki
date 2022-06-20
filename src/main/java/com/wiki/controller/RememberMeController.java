package com.wiki.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wiki.common.R;
import com.wiki.entity.Remember;
import com.wiki.mapper.RememberMapper;
import com.wiki.mapper.VstMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 英语项目
 */
@RestController
@RequestMapping("/rebme")
public class RememberMeController {

    @Autowired
    private RememberMapper rememberMapper;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public R list(Remember req){

        QueryWrapper<Remember> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("id");
        List<Remember> remembers = rememberMapper.selectList(queryWrapper);
        return R.ok().put("list",remembers);
    }

}
