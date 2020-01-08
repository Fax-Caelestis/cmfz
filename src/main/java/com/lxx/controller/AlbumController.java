package com.lxx.controller;

import com.lxx.dao.AlbumDao;
import com.lxx.entity.Album;
import com.lxx.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("album")
public class AlbumController {
    @Autowired
    AlbumDao albumDao;
    @Autowired
    AlbumService albumService;
    //分页
    @RequestMapping("page")
    public Map page(Integer page, Integer rows){
        // records 总条数 page 当前页 rows--> 数据 total 总页数
        HashMap hashMap = new HashMap();
        //查询总条数
        Integer records = albumDao.selectCount(null);
        //总页数
        Integer total=records % rows == 0 ? records / rows : records / rows + 1;
        //当前页展示数据
        List<Album> list = albumService.querrypage(page, rows);
        hashMap.put("records", records);
        hashMap.put("page", page);
        hashMap.put("total", total);
        hashMap.put("rows", list);
        return hashMap;
    }
    //添加修改删除
    @RequestMapping("changeMeg")
    public Map changeMeg(Album album, String oper){
        if ("add".equals(oper)){
            //添加
            album.setCount(0);
            albumService.insert(album);
        } else if ("edit".equals(oper)){
            //修改
            albumService.update(album);
        } else {
            //删除
           albumService.delete(album);
        }
        HashMap hashMap = new HashMap();
        hashMap.put("albumId",album.getId());
        return hashMap;
    }
    /*//上传文件
    @RequestMapping("uploadHttp")
    public void uploadHttp(String albumId, MultipartFile status, HttpServletRequest request) throws IOException {
        // 获取真实路径
        String realPath = request.getSession().getServletContext().getRealPath("/upload/img/");
        //判断文件加是否存在
        File file = new File(realPath);
        if(!file.exists()){
            //没有则多级创建
            file.mkdirs();
        }
        // 设定新名
        String name = new Date().getTime()+"_"+status.getOriginalFilename();
       //获取网络路径
        String scheme = request.getScheme();
        //获取IP地址
        String localHost = InetAddress.getLocalHost().toString();
        //获取端口
        int serverPort = request.getServerPort();
        //获取项目名
        String path = request.getContextPath();
        //拷贝到要上传的目录
        status.transferTo(new File(realPath,name));
        String hname="/upload/img/"+name;
        String uri = scheme +"://"+localHost.split("/")[1]+":"+ serverPort + path +hname;

        Album album = new Album();
        album.setId(albumId);
        album.setStatus(uri);
        albumService.update(album);
    }*/
    //上传文件
    @RequestMapping("uploadHttp")
    public void uploadHttp(String albumId, MultipartFile status, HttpServletRequest request) throws IOException {
        // 获取真实路径
        String realPath = request.getSession().getServletContext().getRealPath("/upload/img/");
        //判断文件加是否存在
        File file = new File(realPath);
        if(!file.exists()){
            //没有则多级创建
            file.mkdirs();
        }
        // 设定新名
        String name = new Date().getTime()+"_"+status.getOriginalFilename();
        //获取网络路径
        String scheme = request.getScheme();
        //获取IP地址
        String localHost = InetAddress.getLocalHost().toString();
        //获取端口
        int serverPort = request.getServerPort();
        //获取项目名
        String path = request.getContextPath();
        //拷贝到要上传的目录
        status.transferTo(new File(realPath,name));
        String hname="/upload/img/"+name;
        String uri = scheme +"://"+localHost.split("/")[1]+":"+ serverPort + path +hname;

        Album album = new Album();
        album.setId(albumId);
        album.setStatus(uri);
        albumService.update(album);
    }
}
