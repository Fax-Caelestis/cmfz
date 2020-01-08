package com.lxx.controller;

import com.alibaba.excel.EasyExcel;
import com.lxx.dao.BannerDao;
import com.lxx.data.BannerDataListener;
import com.lxx.entity.Banner;
import com.lxx.entity.BannerDto;
import com.lxx.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("banner")
public class BannerController {
    @Autowired
    BannerDao bannerDao;
    @Autowired
    BannerService bannerService;
   /* @RequestMapping("page")
    public List<Banner> page(Integer page, Integer rows){
        Integer inx=(page-1)*rows;
       *//* System.out.println(page);
        System.out.println(inx);
        System.out.println(rows);*//*
        List<Banner> list = bannerService.querryPage(new Banner(), inx, rows);
        return list;
    }*/
    //分页
    @RequestMapping("page")
    public BannerDto page(Integer page, Integer rows){
        BannerDto dto = bannerService.Bypage(new Banner(), page, rows);
        return dto;
    }
    @RequestMapping("/changeMeg")
    public Map changeMeg(Banner banner, String oper){
        if ("add".equals(oper)){
            //添加
            bannerService.insert(banner);
        } else if ("edit".equals(oper)){
            //修改
            bannerService.update(banner);
        } else {
            //删除(修改为不显示)
           bannerService.updateStatus(banner);
        }
        HashMap hashMap = new HashMap();
        hashMap.put("bannerId",banner.getId());
        return hashMap;
    }

    //上传文件
    @RequestMapping("/upload")
    public void upload(MultipartFile url, String bannerId, HttpSession session, HttpServletRequest request) throws IOException {
        // 获取真实路径
        String realPath = session.getServletContext().getRealPath("/upload/img/");
        //判断文件是否存在
        File file=new File(realPath);
        if(!file.exists()){
            //没有则创建
            file.mkdirs();
        }
        //设定新名
        String name = new Date().getTime()+"_"+url.getOriginalFilename();
       // System.out.println(realPath);
        //拷贝到要上传的目录
        url.transferTo(new File(realPath,name));
        Banner banner=new Banner();
        banner.setId(bannerId);
        banner.setUrl("upload/img/"+name);
        bannerService.update(banner);
    }
    //上传文件
    @RequestMapping("/uploadHttp")
    public void uploadHttp(MultipartFile url, String bannerId, HttpSession session, HttpServletRequest request) throws IOException {
        // 获取真实路径
        String realPath = session.getServletContext().getRealPath("/upload/img/");
        //判断文件是否存在
        File file=new File(realPath);
        if(!file.exists()){
            //没有则创建
            file.mkdirs();
        }
        //获取网络路径
        String scheme = request.getScheme();
        //获取ip地址
        String localHost = InetAddress.getLocalHost().toString();
        // 获取端口号
        int serverPort = request.getServerPort();
        // 获取项目名
        String contextPath = request.getContextPath();
        //设定文件新名
        String name = new Date().getTime()+"_"+url.getOriginalFilename();
        //拷贝到要上传的目录
        url.transferTo(new File(realPath,name));
        String pname="/upload/img/"+name;
        String uri = scheme +"://"+ localHost.split("/")[1] + ":" + serverPort + contextPath +pname;
        Banner banner=new Banner();
        banner.setId(bannerId);
        banner.setUrl(uri);
        bannerService.update(banner);
    }
    @RequestMapping("esayExcal")
    public void esayExcal(){
        String url="F:\\后期项目\\测试xls\\"+new Date().getTime()+".xls";
        List<Banner> banners = bannerDao.selectAll();
        EasyExcel.write(url,Banner.class)
                .sheet("轮播图")
                .doWrite(banners);

    }
    @RequestMapping("loadExcal")
    public void loadExcal(){
        String url = "F:\\后期项目\\测试xls\\1578040137306.xls";
        // readListener : 读取数据时的监听器  每次使用DemoDataListener都需要new  不要把DemoDataListener交给Spring工厂管理
        // 文件上传 : MFile url  文件上传  File file = new File();
        EasyExcel.read(url,Banner.class,new BannerDataListener()).sheet().doRead();
    }
}
