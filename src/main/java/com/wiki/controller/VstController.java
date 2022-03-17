package com.wiki.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wiki.common.R;
import com.wiki.common.WikiConstants;
import com.wiki.common.utils.SnowFlake;
import com.wiki.entity.Doc;
import com.wiki.entity.EbookSaveReq;
import com.wiki.entity.Test;
import com.wiki.entity.Vst;
import com.wiki.mapper.DocMapper;
import com.wiki.mapper.VstMapper;
import com.wiki.query.EbookQueryReq;
import com.wiki.service.EbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/vst")
public class VstController {

    @Autowired
    private SnowFlake snowFlake;

    @Autowired
    private VstMapper vstMapper;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public R save(@RequestBody Vst req) {
        System.out.println(req);
        System.out.println(req.getPhone());

        try {
            req.setId(snowFlake.nextId()+"");
            vstMapper.insert(req);
            return R.ok().put("ceshi",req);
        }catch (Exception e){
            return R.error("新增错误！");
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public R list(Vst req){
        Page<Vst> page = new Page<>(1, 100);
        QueryWrapper<Vst> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("id");
        Page<Vst> vstlist = vstMapper.selectPage(page, queryWrapper);
        return R.ok().put("vstlist",vstlist);
    }

}
