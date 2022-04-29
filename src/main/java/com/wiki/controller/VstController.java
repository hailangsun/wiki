package com.wiki.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vstsoft.files.clicent.VSFSClient;
import com.vstsoft.files.clicent.model.module.StreamFile;
import com.wiki.common.R;
import com.wiki.common.WikiConstants;
import com.wiki.common.utils.CommonFunc;
import com.wiki.common.utils.SnowFlake;
import com.wiki.entity.Doc;
import com.wiki.entity.EbookSaveReq;
import com.wiki.entity.Test;
import com.wiki.entity.Vst;
import com.wiki.mapper.DocMapper;
import com.wiki.mapper.VstMapper;
import com.wiki.query.EbookQueryReq;
import com.wiki.service.EbookService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;

@RestController
@RequestMapping("/vst")
public class VstController {
    private static final Logger LOG = LoggerFactory.getLogger(VstController.class);
//    static String serverUrl = "http://127.0.0.1:8080/vst-files";
    static String serverUrl = "http://47.95.27.24:8086/vst-files";

    static String username = "vsta";
    static String key = "12345678";

    public static final String FJXX_PATH= "vspn30";

    public VSFSClient getClient(){
        //带超时时间的客户端
//    	return new VSFSClient(serverUrl, username, key, 10000, 30000);
        //简单客户端
        return new VSFSClient(serverUrl, username, key);
    }


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

    @RequestMapping(value = "/fjxxsave", method = RequestMethod.POST)
    @ResponseBody
    public R fjxxsave(HttpServletRequest request,String filePath) {
        try{
            LOG.info("=====进入保存成功======");
            Map<String, String[]> parameterMap = request.getParameterMap();
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            List<MultipartFile> fileMap = multipartRequest.getFiles("file");
            LOG.info("=====参数======"+parameterMap);
            if(fileMap != null && fileMap.size() > 0){

                VSFSClient client = getClient();
                String retJson = client.uploadSingleStreamFile(FJXX_PATH, new StreamFile(fileMap.get(0).getOriginalFilename(), fileMap.get(0).getInputStream()));
                JSONObject jsonObject = JSONObject.parseObject(retJson);
                if (!jsonObject.get("result").toString().equals("success")) {
                    throw new RuntimeException("附件上传服务器失败！");
                }

                System.out.println(jsonObject.get("filePath").toString());
                Vst req = getParms(request, Vst.class);
                req.setId(snowFlake.nextId()+"");
                req.setWjlj(jsonObject.get("filePath").toString());
                req.setSysdate(new Date());
                vstMapper.insert(req);
                return R.ok().put("data",req);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return R.ok();
    }


    /**
     * 存储多个文件
     * @param request
     * @param filePath
     * @return
     */
    @RequestMapping(value = "/fjxxsaveMultipart", method = RequestMethod.POST)
    @ResponseBody
    public R fjxxsaveMultipart(HttpServletRequest request,String filePath) {
        try{
            LOG.info("=====fjxxsaveMultipart======");
            Map<String, String[]> parameterMap = request.getParameterMap();
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            List<MultipartFile> fileMap = multipartRequest.getFiles("file");
            Vst req = getParms(request, Vst.class);
            if(fileMap != null && fileMap.size() > 0){

                VSFSClient client = getClient();
                String retJson = client.uploadSingleStreamFile(FJXX_PATH, new StreamFile(fileMap.get(0).getOriginalFilename(), fileMap.get(0).getInputStream()));
                JSONObject jsonObject = JSONObject.parseObject(retJson);
                if (!jsonObject.get("result").toString().equals("success")) {
                    throw new RuntimeException("附件上传服务器失败！"+"原因："+jsonObject.get("reason").toString());
//                    return R.error("message").put("message",jsonObject.get("reason").toString());
                }
                req.setId(snowFlake.nextId()+"");
                req.setWjlj(jsonObject.get("filePath").toString());
                req.setFilename(jsonObject.get("fileName").toString());
                req.setSysdate(new Date());
                vstMapper.insert(req);
                return R.ok().put("data",req);
            }


//            Map<String, MultipartFile> imagefiles = multipartRequest.getFileMap();
//            LOG.info("=====参数======"+parameterMap);
//            VSFSClient client = getClient();
//            List<StreamFile> streamFiles = new ArrayList<>();
//            Map<String,String> fileNames = new HashMap<>();
//            for (Map.Entry<String, MultipartFile> entry : imagefiles.entrySet()) {
////                String[] suffixName = null;
////                if(StringUtils.hasLength(entry.getKey())){
////                    suffixName = entry.getKey().split("\\.");
////                }
////                String filenameid = snowFlake.nextId()+"."+suffixName[1];
////                streamFiles.add(new StreamFile(filenameid, entry.getValue().getInputStream()));
////                fileNames.put(filenameid,entry.getKey());
//                streamFiles.add(new StreamFile(entry.getKey(), entry.getValue().getInputStream()));
//            }

//            String retJson = client.uploadMultipleStreamFile(FJXX_PATH, streamFiles);
//            List<Map> maps = JSONObject.parseArray(retJson, Map.class);
//            Vst req = getParms(request, Vst.class);
//            req.setBattchid(snowFlake.nextId()+"");
//            maps.stream().forEach(item ->{
//                if (!item.get("result").toString().equals("success")) {
//                    throw new RuntimeException("附件上传服务器失败！");
//                }
//                req.setId(snowFlake.nextId()+"");
//                req.setWjlj(item.get("filePath").toString());
////                req.setFilename(fileNames.get(item.get("fileName").toString()));
//                req.setFilename(item.get("fileName").toString());
//                req.setSysdate(new Date());
//                vstMapper.insert(req);
//            });
//
//            return R.ok().put("data",req);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return R.ok();
    }

    /**
     * 下载附件
     * @param request
     * @param response
     */
    @RequestMapping(value = "/fjxxDownload", method = RequestMethod.GET)
    public void fjxxDownload(HttpServletRequest request, HttpServletResponse response) {
        LOG.info("=====进入下载播放音频======");
        VSFSClient client = getClient();
        QueryWrapper<Vst> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("sysdate");
        queryWrapper.isNotNull("sysdate");
        List<Vst> vsts = vstMapper.selectList(queryWrapper);
        try {

            InputStream inputStream = client.downloadSingleFile(vsts.get(0).getWjlj());
//            response.setContentType("application/octet-stream");
//            response.setContentType("audio/mp4");
            response.setHeader("Last-Modified", new Date().toString());
            response.setHeader("Accept-Ranges", "bytes");
//            response.setContentType("video/mp4");
//            response.addHeader("Content-Type","audio/mp4;charset=UTF-8");

            response.setContentType("audio/mpeg");
            response.addHeader("Content-Type","audio/mpeg;charset=UTF-8");

//            response.setHeader("Content-disposition","attachment;filename=\""+vst.getWjlj()+"\"");
            IOUtils.copy(inputStream,response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        }


//        try {
//
//                InputStream inputStream = fjxxService.getStreamByPath(fkfjxxByYwid.get(0));
//                if (inputStream != null) {
//                    String hzmc = fkfjxxByYwid.get(0).get("HZMC") == null ? ".docx" : "."+fkfjxxByYwid.get(0).get("HZMC");
//                    OutputStream outputStream = response.getOutputStream();
//                    wjmc = URLEncoder.encode(wjmc, "UTF-8");
//                    response.setContentType("application/octet-stream");
//                    response.setHeader("Content-disposition","attachment;filename=\""+wjmc+hzmc+"\"");
//                    try {
//                        int bytesRead = 0;
//                        byte[] buffer = new byte[1024];
//                        while ((bytesRead = inputStream.read(buffer, 0, 1024)) != -1) {
//                            outputStream.write(buffer, 0, bytesRead);
//                        }
//                    }catch (Exception e){
//                        throw new RuntimeException(e.getMessage());
//                    }finally {
//                        outputStream.close();
//                        inputStream.close();
//                    }
//                }
//
//        } catch (Exception e) {
//            VSTLogger.localLog("===========下载附件出错===============参数:"+paramMap);
//            e.printStackTrace();
//        }
    }

    @RequestMapping(value = "/fjxxDownloadImage", method = RequestMethod.GET)
    public void fjxxDownloadImage(HttpServletRequest request, HttpServletResponse response) {
        LOG.info("=====fjxxDownloadImage======");
        VSFSClient client = getClient();
        QueryWrapper<Vst> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("sysdate");
        queryWrapper.isNotNull("sysdate");
        queryWrapper.eq("flagtype",'1');
        List<Vst> vsts = vstMapper.selectList(queryWrapper);
        try {

            String[] suffixName = null;
            if(StringUtils.hasLength(vsts.get(0).getFilename())){
                suffixName = vsts.get(0).getFilename().split("\\.");
            }
            String suffixTemp = suffixName[1];
            if("png".equals(suffixName[1])){
                response.setContentType("image/png");
                response.addHeader("Content-Type","image/png;charset=UTF-8");
            }else {
                response.setContentType("image/jpeg");
                response.addHeader("Content-Type","image/jpeg;charset=UTF-8");
            }

            InputStream inputStream = client.downloadSingleFile(vsts.get(0).getWjlj());
//            response.setContentType("application/octet-stream");

            response.setHeader("Last-Modified", new Date().toString());
            response.setHeader("Accept-Ranges", "bytes");
//            String wjmc = URLEncoder.encode(vsts.get(0).getFilename(), "UTF-8");
//            response.setHeader("Content-disposition","attachment;filename=\""+wjmc+"\"");
            IOUtils.copy(inputStream,response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        }


//        try {
//
//                InputStream inputStream = fjxxService.getStreamByPath(fkfjxxByYwid.get(0));
//                if (inputStream != null) {
//                    String hzmc = fkfjxxByYwid.get(0).get("HZMC") == null ? ".docx" : "."+fkfjxxByYwid.get(0).get("HZMC");
//                    OutputStream outputStream = response.getOutputStream();
//                    wjmc = URLEncoder.encode(wjmc, "UTF-8");
//                    response.setContentType("application/octet-stream");
//                    response.setHeader("Content-disposition","attachment;filename=\""+wjmc+hzmc+"\"");
//                    try {
//                        int bytesRead = 0;
//                        byte[] buffer = new byte[1024];
//                        while ((bytesRead = inputStream.read(buffer, 0, 1024)) != -1) {
//                            outputStream.write(buffer, 0, bytesRead);
//                        }
//                    }catch (Exception e){
//                        throw new RuntimeException(e.getMessage());
//                    }finally {
//                        outputStream.close();
//                        inputStream.close();
//                    }
//                }
//
//        } catch (Exception e) {
//            VSTLogger.localLog("===========下载附件出错===============参数:"+paramMap);
//            e.printStackTrace();
//        }
    }


    /**
     * 下载附件
     * @param request
     * @param response
     */
    @RequestMapping(value = "/fjxxDownloadVideo", method = RequestMethod.GET)
    public void fjxxDownloadVideo(HttpServletRequest request, HttpServletResponse response) {
        LOG.info("=====进入下载播放音频======");
        VSFSClient client = getClient();
        QueryWrapper<Vst> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("sysdate");
        queryWrapper.isNotNull("sysdate");
        queryWrapper.eq("flagtype","2");
        List<Vst> vsts = vstMapper.selectList(queryWrapper);
        try {

            InputStream inputStream = client.downloadSingleFile(vsts.get(0).getWjlj());
//            response.setContentType("application/octet-stream");
//            response.setContentType("audio/mp4");
            response.setHeader("Last-Modified", new Date().toString());
            response.setHeader("Accept-Ranges", "bytes");
            response.setContentType("video/mp4");
            response.addHeader("Content-Type","video/mp4;charset=UTF-8");

//            response.setContentType("audio/mpeg");
//            response.addHeader("Content-Type","audio/mpeg;charset=UTF-8");

//            response.setHeader("Content-disposition","attachment;filename=\""+vst.getWjlj()+"\"");
            IOUtils.copy(inputStream,response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static <T> T getParms(HttpServletRequest request, Class<T> parmClass) {
        Map<String,String> parmMap=new HashMap<String,String>();
        Enumeration<String> all = request.getParameterNames();
        String name  = "";
        String value = "";
        while(all.hasMoreElements()){
            name = all.nextElement();
            value = request.getParameter(name);
            parmMap.put(name, value);
        }
        T result = JSON.parseObject(JSON.toJSONString(parmMap), parmClass);
        return result;
    }
}
