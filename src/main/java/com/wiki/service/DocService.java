package com.wiki.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wiki.common.R;
import com.wiki.common.WikiConstants;
import com.wiki.common.utils.CopyUtil;
import com.wiki.common.utils.SnowFlake;
import com.wiki.entity.Doc;
import com.wiki.mapper.DocMapper;
import com.wiki.query.DocQueryReq;
import com.wiki.query.DocQueryResp;

import com.wiki.vo.DocVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DocService {
    private static final Logger LOG = LoggerFactory.getLogger(DocService.class);

    @Resource
    private DocMapper docMapper;

    @Resource
    private SnowFlake snowFlake;

    public List<DocQueryResp> all(){
        QueryWrapper<Doc> queryWrapper = new QueryWrapper<>();
//        queryWrapper.orderByAsc("clause");
        List<Doc> DocList = docMapper.selectList(queryWrapper);
        // 列表复制
        List<DocQueryResp> list = CopyUtil.copyList(DocList, DocQueryResp.class);
        return list;
    }

    public R list(DocQueryReq req) {
        Page<Doc> page = new Page<>(req.getPage(), req.getSize());
        QueryWrapper<Doc> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");
        Page<Doc> DocPage = docMapper.selectPage(page, queryWrapper);
        LOG.info("总行数：{}", DocPage.getTotal());
        LOG.info("总页数：{}", DocPage.getPages());

        // 列表复制
//        List<DocQueryResp> list = CopyUtil.copyList(DocPage.getRecords(), DocQueryResp.class);
        return R.ok()
                .put(WikiConstants.WIKI_CONTENT,DocPage.getRecords())
                .put(WikiConstants.WIKI_TOTAL,DocPage.getTotal());


    }

    public void save(DocVo req) {
        Doc doc = CopyUtil.copy(req,Doc.class);
        if (ObjectUtils.isEmpty(req.getId())) {
            // 新增
            doc.setId(snowFlake.nextId()+"");
            docMapper.insert(doc);
        } else {
            // 更新
            docMapper.updateById(doc);
        }
    }

    public void delete(String id) {
        if(!ObjectUtils.isEmpty(id)){
            docMapper.deleteById(id);
        }
    }

    public void delete(List<String> ids) {
        if(!ObjectUtils.isEmpty(ids)){
            docMapper.deleteBatchIds(ids);
        }
    }
}
