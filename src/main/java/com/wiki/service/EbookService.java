package com.wiki.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wiki.common.WikiConstants;
import com.wiki.query.EbookQueryReq;
import com.wiki.common.R;
import com.wiki.entity.Ebook;
import com.wiki.entity.EbookSaveReq;
import com.wiki.mapper.EbookMapper;
import com.wiki.utils.CopyUtil;
import com.wiki.utils.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

@Service
public class EbookService {
    private static final Logger LOG = LoggerFactory.getLogger(EbookService.class);

    @Resource
    EbookMapper ebookMapper;
    @Resource
    SnowFlake snowFlake;

    public R ebook(EbookQueryReq req) {
        Page<Ebook> page = new Page<>(req.getPage(), req.getSize());
        QueryWrapper<Ebook> queryWrapper = new QueryWrapper<>();
        if(req.getName() != null){
            queryWrapper.like("name",req.getName());
        }
        if(req.getCategoryId2() != null){
            queryWrapper.eq("category2_id",req.getCategoryId2());
        }
        Page<Ebook> ebookPage = ebookMapper.selectPage(page, queryWrapper);
        return R.ok()
                .put(WikiConstants.WIKI_CONTENT,ebookPage.getRecords())
                .put(WikiConstants.WIKI_TOTAL,ebookPage.getTotal());

    }

    public void save(EbookSaveReq req) {
        Ebook ebook = CopyUtil.copy(req,Ebook.class);
        if(ObjectUtils.isEmpty(req.getId())){
            ebook.setId(snowFlake.nextId()+"");
            ebookMapper.insert(ebook);
        }else {
            ebookMapper.updateById(ebook);
        }
    }

    public void delete(String id) {
        if(!ObjectUtils.isEmpty(id)){
            ebookMapper.deleteById(id);
        }
    }
}
