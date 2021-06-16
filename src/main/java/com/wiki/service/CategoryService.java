package com.wiki.service;

import com.wiki.common.R;
import com.wiki.query.CategoryQueryReq;
import com.wiki.query.CategoryQueryResp;
import com.wiki.vo.CategoryVo;

import java.util.List;


public interface CategoryService {
    List<CategoryQueryResp> all() throws Exception;

    R list(CategoryQueryReq req) throws Exception;

    void save(CategoryVo req);

    void delete(String id);
}
