package com.wiki.controller;

import com.wiki.common.R;
import com.wiki.common.WikiConstants;
import com.wiki.query.DocQueryReq;
import com.wiki.query.DocQueryResp;
import com.wiki.service.DocService;
import com.wiki.vo.DocVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/doc")
public class DocController {
    @Resource
    private DocService docService;

    @GetMapping("/all")
    public R all() {
        try {
            List<DocQueryResp> list = docService.all();
            return R.ok().put(WikiConstants.WIKI_CONTENT,list);
        }catch (Exception ex){
            ex.printStackTrace();
            return R.error().put(WikiConstants.WIKI_MESSAGE,"");
        }
    }

    @GetMapping("/list")
    public R list(@Valid DocQueryReq req) {
        try {
            return docService.list(req);
        }catch (Exception ex){
            ex.printStackTrace();
            return R.error().put(WikiConstants.WIKI_MESSAGE,"");
        }
    }

    @PostMapping("/save")
    public R save(@Valid @RequestBody DocVo req) {
        docService.save(req);
        return R.ok();
    }

//    @DeleteMapping("/delete/{id}")
//    public R delete(@PathVariable String id) {
//        docService.delete(id);
//        return R.ok();
//    }

    @DeleteMapping("/delete/{idsStr}")
    public R delete(@PathVariable String idsStr) {
        List<String> list = Arrays.asList(idsStr.split(","));
        docService.delete(list);
        return R.ok();
    }
}
