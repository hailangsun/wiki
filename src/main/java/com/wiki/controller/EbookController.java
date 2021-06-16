package com.wiki.controller;

import com.wiki.common.WikiConstants;
import com.wiki.query.EbookQueryReq;
import com.wiki.entity.EbookSaveReq;
import com.wiki.common.R;
import com.wiki.service.EbookService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/ebook")
public class EbookController {

    @Resource
    EbookService ebookService;

    @GetMapping("/list")
    public R ebook(@Valid EbookQueryReq req) {
        try {
            return ebookService.ebook(req);
        }catch (Exception ex){
            ex.printStackTrace();
            return R.error()
                    .put(WikiConstants.WIKI_MESSAGE,ex.getMessage());

        }
    }

    @PostMapping("/save")
    public R save(@RequestBody EbookSaveReq req) {
        try{
            ebookService.save(req);
            return R.ok();
        }catch (Exception ex){
            ex.printStackTrace();
            return R.error().put(WikiConstants.WIKI_MESSAGE,"");
        }
    }

    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable String id) {
        try{
            ebookService.delete(id);
            return R.ok();
        }catch (Exception ex){
            ex.printStackTrace();
            return R.error().put(WikiConstants.WIKI_MESSAGE,"");
        }
    }


}
