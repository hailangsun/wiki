package com.wiki.controller;

import com.wiki.common.R;
import com.wiki.common.WikiConstants;
import com.wiki.query.CategoryQueryReq;
import com.wiki.query.CategoryQueryResp;
import com.wiki.service.CategoryService;
import com.wiki.vo.CategoryVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    @GetMapping("/all")
    public R all() {
        try {
            List<CategoryQueryResp> list = categoryService.all();
            return R.ok().put(WikiConstants.WIKI_CONTENT,list);
        }catch (Exception ex){
            ex.printStackTrace();
            return R.error().put(WikiConstants.WIKI_MESSAGE,"");
        }
    }

    @GetMapping("/list")
    public R list(@Valid CategoryQueryReq req) {
        try {
            return categoryService.list(req);
        }catch (Exception ex){
            ex.printStackTrace();
            return R.error().put(WikiConstants.WIKI_MESSAGE,"");
        }
    }

    @PostMapping("/save")
    public R save(@Valid @RequestBody CategoryVo req) {
        categoryService.save(req);
        return R.ok();
    }

    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable String id) {
        categoryService.delete(id);
        return R.ok();
    }

}
