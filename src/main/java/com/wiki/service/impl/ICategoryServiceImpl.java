package com.wiki.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wiki.common.R;
import com.wiki.common.WikiConstants;
import com.wiki.entity.Category;
import com.wiki.mapper.CategoryMapper;
import com.wiki.query.CategoryQueryReq;
import com.wiki.query.CategoryQueryResp;
import com.wiki.service.ICategoryService;
import com.wiki.utils.CopyUtil;
import com.wiki.utils.SnowFlake;
import com.wiki.vo.CategoryVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ICategoryServiceImpl implements ICategoryService {
    private static final Logger LOG = LoggerFactory.getLogger(ICategoryServiceImpl.class);

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private SnowFlake snowFlake;

    public List<CategoryQueryResp> all(){
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
//        queryWrapper.orderByAsc("clause");
        List<Category> categoryList = categoryMapper.selectList(queryWrapper);
        // 列表复制
        List<CategoryQueryResp> list = CopyUtil.copyList(categoryList, CategoryQueryResp.class);
        return list;
    }

    public R list(CategoryQueryReq req) {
        Page<Category> page = new Page<>(req.getPage(), req.getSize());
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");
        Page<Category> categoryPage = categoryMapper.selectPage(page, queryWrapper);
        LOG.info("总行数：{}", categoryPage.getTotal());
        LOG.info("总页数：{}", categoryPage.getPages());

        // 列表复制
//        List<CategoryQueryResp> list = CopyUtil.copyList(categoryPage.getRecords(), CategoryQueryResp.class);
        return R.ok()
                .put(WikiConstants.WIKI_CONTENT,categoryPage.getRecords())
                .put(WikiConstants.WIKI_TOTAL,categoryPage.getTotal());


    }

    public void save(CategoryVo req) {
        Category category = CopyUtil.copy(req,Category.class);
        if (ObjectUtils.isEmpty(req.getId())) {
            // 新增
            category.setId(snowFlake.nextId()+"");
            categoryMapper.insert(category);
        } else {
            // 更新
            categoryMapper.updateById(category);
        }
    }

    public void delete(String id) {
        if(!ObjectUtils.isEmpty(id)){
            categoryMapper.deleteById(id);
        }
    }
}
