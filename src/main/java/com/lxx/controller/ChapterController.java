package com.lxx.controller;

import com.lxx.dao.AlbumDao;
import com.lxx.entity.Album;
import com.lxx.entity.Chapter;
import com.lxx.service.AlbumService;
import com.lxx.service.ChapterService;
import org.apache.commons.io.FileUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.tag.TagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("chapter")
public class ChapterController {
    @Autowired
    AlbumService albumService;
    @Autowired
    ChapterService chapterService;
    @Autowired
    AlbumDao albumDao;

    @RequestMapping("/page")
    //分页
    public Map page(Integer page, Integer rows,String albumId){
        // records 总条数 page 当前页 rows--> 数据 total 总页数
        HashMap hashMap = new HashMap();
        //查询总条数
        int records = albumDao.selectCount(null);
        System.out.println(records);
        //总页数
        Integer total=records % rows == 0 ? records / rows : records / rows + 1;
        //当前页展示数据
        List<Chapter> chapters = chapterService.querryPage(page, rows, albumId);
        hashMap.put("records", records);
        hashMap.put("page", page);
        hashMap.put("total", total);
        hashMap.put("rows", chapters);
        return hashMap;

    }
    //查所有
   /* @RequestMapping("/all")
    public List<Chapter> all(String albumId){
        return chapterService.querryAllByAlbumId(albumId);
    }*/
    //添加修改查所有
    @RequestMapping("changeMeg")
    public Map changeMeg(Chapter chapter,String oper,String albumId){
        //System.out.println(chapter);
        if ("add".equals(oper)){
            //添加
            ///chapter.setAlbumId(albumId);
            chapter.setCreateTime(new Date());
            chapterService.insert(chapter);
            //修改专辑数量
            Album album = new Album();
            album.setId(chapter.getAlbumId());
            Album album1 = albumService.querryOne(album);
            album1.setCount(album1.getCount()+1);
            albumService.update(album1);

        } else if ("edit".equals(oper)){
            //修改
           chapterService.update(chapter);
        } else {
            //删除
            chapterService.delete(chapter);
            //专辑内文章数量-1
            Album album = new Album();
            album.setId(chapter.getAlbumId());
            Album album1 = albumService.querryOne(album);
            album1.setCount(album1.getCount()-1);
            albumService.update(album1);
        }
        HashMap hashMap = new HashMap();
        hashMap.put("chapterId",chapter.getId());
        return hashMap;
    }
    //上传音频
    @RequestMapping("/upload")
    public void upload(String chapterId, MultipartFile url, HttpServletRequest request, HttpServletResponse response) throws IOException, TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException {
        Chapter chapter = new Chapter();

        //设置接收格式及响应格式
        String s = url.getOriginalFilename();
        //获取后缀
        //获取文件名===》无后缀
        String realtitle = s.substring(0,s.lastIndexOf("."));

        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        //获取真实路径
        String realPath = request.getSession().getServletContext().getRealPath("/upload/music/");
        //判断文件加是否存在
        File file = new File(realPath);
        if(!file.exists()){
            //没有则多级创建
            file.mkdirs();
        }
        // 设定新名
        String name =new Date().getTime()+"_"+url.getOriginalFilename();
        //获取网络路径
        String scheme = request.getScheme();
        //获取IP地址
        String localHost = InetAddress.getLocalHost().toString();
        //获取端口
        int serverPort = request.getServerPort();
        //获取项目名
        String path = request.getContextPath();
        //拷贝到要上传的目录
        url.transferTo(new File(realPath,name));
        String hname="/upload/music/"+name;
        //生成网络存储路径
        String uri = scheme +"://"+localHost.split("/")[1]+":"+ serverPort + path +hname;

        // 计算文件大小
        Double size = Double.valueOf(url.getSize()/1024/1024);
        chapter.setSize(size);
        // 计算音频时长
        // 使用三方计算音频时间工具类 得出音频时长
        String[] split = uri.split("/");
        // 获取文件名
        String sname = split[split.length-1];
        // 通过文件获取AudioFile对象 音频解析对象
        AudioFile read = AudioFileIO.read(new File(realPath, sname));
        // 通过音频解析对象 获取 头部信息 为了信息更准确 需要将AudioHeader转换为MP3AudioHeader
        MP3AudioHeader audioHeader = (MP3AudioHeader) read.getAudioHeader();
        // 获取音频时长 秒
        int trackLength = audioHeader.getTrackLength();
        String time = trackLength/60 + "分" + trackLength%60 + "秒";
        chapter.setTime(time);
        //设置该文件名
        chapter.setTitle(realtitle);
        //存储id
        chapter.setId(chapterId);
        //存储路径
        chapter.setUrl(uri);
        chapterService.update(chapter);
    }
    //下载
    @RequestMapping("downloadChapter")
    public void downloadChapter(String url, HttpServletResponse response, HttpSession session) throws IOException {
        //处理路径
        String[] split = url.split("/");
        String realPath = session.getServletContext().getRealPath("/upload/music/");
        //获取文件名
        String name = split[split.length - 1];
        File file = new File(realPath,name);
        //设置响应头
        response.setHeader("Content-Disposition", "attachment; filename="+name);
        ServletOutputStream outputStream = response.getOutputStream();
        FileUtils.copyFile(file,outputStream);
    }
}
