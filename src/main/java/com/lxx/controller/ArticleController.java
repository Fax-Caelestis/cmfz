package com.lxx.controller;

import com.lxx.dao.ArticleDao;
import com.lxx.entity.Article;
import com.lxx.service.ArticleService;
import com.lxx.util.HttpUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("article")
public class ArticleController {
    @Autowired
    ArticleDao articleDao;
    @Autowired
    ArticleService articleService;
    //分页
    @RequestMapping("page")
    public Map page(Integer page, Integer rows){
        HashMap hashMap = new HashMap();
        //查询总条数
        Integer records = articleDao.selectCount(null);
        //总页数
        Integer total=records % rows == 0 ? records / rows : records / rows + 1;
        //当前页展示数据
        List<Article> list = articleService.selectBypage(page, rows);
        hashMap.put("records", records);
        hashMap.put("page", page);
        hashMap.put("total", total);
        hashMap.put("rows", list);
        return hashMap;
    }
    @RequestMapping("add")
    public void add(Article article, MultipartFile inputfile, HttpServletRequest request) throws IOException {
       if("".equals(article.getId())) {
           // 获取真实路径
           String realPath = request.getSession().getServletContext().getRealPath("/upload/articleimg/");
           //判断文件加是否存在
           File file = new File(realPath);
           if (!file.exists()) {
               //没有则多级创建
               file.mkdirs();
           }
           // 设定新名
           String name = new Date().getTime() + "_" + inputfile.getOriginalFilename();
           //获取网络路径
           String scheme = request.getScheme();
           //获取IP地址
           String localHost = InetAddress.getLocalHost().toString();
           //获取端口
           int serverPort = request.getServerPort();
           //获取项目名
           String path = request.getContextPath();
           //拷贝到要上传的目录
           inputfile.transferTo(new File(realPath, name));
           String hname = "/upload/articleimg/" + name;
           String uri = scheme + "://" + localHost.split("/")[1] + ":" + serverPort + path + hname;
           article.setId(UUID.randomUUID().toString());
           article.setImg(uri);
           article.setCreateDate(new Date());
           article.setPublishDate(new Date());
           articleService.insert(article);

       }else{
               String realPath = request.getSession().getServletContext().getRealPath("/upload/articleimg/");
               File file = new File(realPath);
               if (!file.exists()) {
                   //没有则多级创建
                   file.mkdirs();
               }
               String http = HttpUtil.getHttp(inputfile, request, "/upload/articleimg/");
               article.setImg(http);
           }
           articleService.upload(article);

    }
    @RequestMapping("uploadImg")
    public Map uploadImg(MultipartFile imgFile, HttpSession session, HttpServletRequest request){
        // 该方法需要返回的信息 error 状态码 0 成功 1失败   成功时 url 图片路径
        HashMap hashMap = new HashMap();
        String realPath = session.getServletContext().getRealPath("/upload/articleimg/");
        File file = new File(realPath);
        if (!file.exists()){
            file.mkdirs();
        }
        // 网络路径
        try{
            String http = HttpUtil.getHttp(imgFile, request, "/upload/articleimg/");
            hashMap.put("error",0);
            hashMap.put("url",http);
        }catch (Exception e){
            hashMap.put("error",1);
            e.printStackTrace();
        }
        return hashMap;
    }
    @RequestMapping("showAllImg")
    public Map showAllImg(HttpServletRequest request,HttpSession session){
        HashMap hashMap = new HashMap();
        hashMap.put("current_url",request.getContextPath()+"/upload/articleimg/");
        String realPath = session.getServletContext().getRealPath("/upload/articleimg/");
        File file = new File(realPath);
        File[] files = file.listFiles();
        hashMap.put("total_count",files.length);
        ArrayList arrayList = new ArrayList();
        for (File file1 : files) {
            HashMap fileMap = new HashMap();
            fileMap.put("is_dir",false);
            fileMap.put("has_file",false);
            fileMap.put("filesize",file1.length());
            fileMap.put("is_photo",true);
            String name = file1.getName();
            String extension = FilenameUtils.getExtension(name);
            fileMap.put("filetype",extension);
            fileMap.put("filename",name);
            // 通过字符串拆分获取时间戳
            String time = name.split("_")[0];
            // 创建SimpleDateFormat对象 指定yyyy-MM-dd hh:mm:ss 样式
            //  simpleDateFormat.format() 获取指定样式的字符串(yyyy-MM-dd hh:mm:ss)
            // format(参数)  参数:时间类型   new Date(long类型指定时间)long类型  现有数据:字符串类型时间戳
            // 需要将String类型 转换为Long类型 Long.valueOf(str);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String format = simpleDateFormat.format(new Date(Long.valueOf(time)));
            fileMap.put("datetime",format);
            arrayList.add(fileMap);
        }
        hashMap.put("file_list",arrayList);
        return hashMap;
    }
    //删除
    @RequestMapping("dee")
    public void dee(Article article){
        articleService.deleteOne(article);
    }
}
