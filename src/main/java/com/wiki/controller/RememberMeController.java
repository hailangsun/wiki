package com.wiki.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wiki.common.R;
import com.wiki.entity.RebCard;
import com.wiki.entity.Remember;
import com.wiki.mapper.RebCardMapper;
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

    @Autowired
    private RebCardMapper rebCardMapper;

    /**
     * 首页导航
     * @param req
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public R list(Remember req){

        QueryWrapper<Remember> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("id");
        List<Remember> remembers = rememberMapper.selectList(queryWrapper);
        return R.ok().put("list",remembers);
    }

    @RequestMapping(value = "/swiperListItem", method = RequestMethod.GET)
    public R swiperListItem(RebCard req){

        QueryWrapper<RebCard> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("id");
        List<RebCard> remembers = rebCardMapper.selectList(queryWrapper);
        return R.ok().put("list",remembers);
    }

}
