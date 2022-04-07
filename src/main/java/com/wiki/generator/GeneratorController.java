package com.wiki.generator;

import com.wiki.mapper.GeneratorMapper;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/generator")
public class GeneratorController {

    @Resource
    GeneratorService generatorService;



    /**
     * 生成代码
     */
    @RequestMapping("/code")
    public void code(String tables, HttpServletResponse response) throws IOException {
//        tables = "doc";
        byte[] data = generatorService.generatorCode(tables.split(","));

        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"renren.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");

        IOUtils.write(data, response.getOutputStream());
    }
}
