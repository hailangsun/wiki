package com.wiki.controller;

import com.wiki.common.R;
import com.wiki.common.WikiConstants;
import com.wiki.query.CategoryQueryReq;
import com.wiki.query.CategoryQueryResp;
import com.wiki.service.ICategoryService;
import com.wiki.vo.CategoryVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/category")
public class CategoryController {
    @Resource
    private ICategoryService ICategoryService;


    @GetMapping("/all")
    public R all() {
        try {
            List<CategoryQueryResp> list = ICategoryService.all();
            return R.ok().put(WikiConstants.WIKI_CONTENT,list);
        }catch (Exception ex){
            ex.printStackTrace();
            return R.error().put(WikiConstants.WIKI_MESSAGE,"");
        }
    }

    @GetMapping("/list")
    public R list(@Valid CategoryQueryReq req) {
        try {
            return ICategoryService.list(req);
        }catch (Exception ex){
            ex.printStackTrace();
            return R.error().put(WikiConstants.WIKI_MESSAGE,"");
        }
    }

    @PostMapping("/save")
    public R save(@Valid @RequestBody CategoryVo req) {
        ICategoryService.save(req);
        return R.ok();
    }

    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable String id) {
        ICategoryService.delete(id);
        return R.ok();
    }

}
