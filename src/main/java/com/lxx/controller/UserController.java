package com.lxx.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.lxx.dao.*;
import com.lxx.entity.*;
import com.lxx.service.UserService;
import com.lxx.util.HttpUtil;
import io.goeasy.GoEasy;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    FocusDao focusDao;
    @Autowired
    UserService userService;
    @Autowired
    UserDao userDao;

    @RequestMapping("page")
    public Map querryPage(Integer page, Integer rows){
        HashMap hashMap = new HashMap();
        //查询总条数
        Integer records = userDao.selectCount(null);
        //总页数
        Integer total=records % rows == 0 ? records / rows : records / rows + 1;
        //当前页展示数据
        List<User> users = userService.querryBypage(page, rows);
        hashMap.put("records", records);
        hashMap.put("page", page);
        hashMap.put("total", total);
        hashMap.put("rows", users);
        return hashMap;
    }
    @RequestMapping("showUserTime")
    public Map showUserTime(){
        HashMap hashMap = new HashMap();
        ArrayList manList = new ArrayList();
        manList.add(userDao.queryUserByTime("0",1));
        manList.add(userDao.queryUserByTime("0",7));
        manList.add(userDao.queryUserByTime("0",30));
        manList.add(userDao.queryUserByTime("0",365));
        ArrayList womenList = new ArrayList();
        womenList.add(userDao.queryUserByTime("1",1));
        womenList.add(userDao.queryUserByTime("1",7));
        womenList.add(userDao.queryUserByTime("1",30));
        womenList.add(userDao.queryUserByTime("1",365));
        hashMap.put("man",manList);
        hashMap.put("women",womenList);
        return hashMap;
    }
    @RequestMapping("selectLocationBySex")
    public Map selectLocationBySex(){
        HashMap hashMap = new HashMap();
        List<UserMapDTO> man = userService.querryByLocation("0");
        List<UserMapDTO> women = userService.querryByLocation("1");
        hashMap.put("man",man);
        hashMap.put("women",women);
        return hashMap;
    }
    @RequestMapping("addUser")
    public void addUser(){
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setSex("1");
        user.setLocation("河南");
        user.setRigestDate(new Date());
        userDao.insert(user);
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-3d071f1482a74517b136d152a1465b6b");
        Map map = showUserTime();
        String s = JSONUtils.toJSONString(map);
        System.out.println(s);
        goEasy.publish("cmfz", s);
    }
    @RequestMapping("changMeg")
    public Map changMeg(String oper, User user){
        if ("add".equals(oper)){
            //添加
            user.setId(UUID.randomUUID().toString());
            user.setRigestDate(new Date());
            userService.insert(user);
            GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-3d071f1482a74517b136d152a1465b6b");
            Map map = showUserTime();
            String s = JSONUtils.toJSONString(map);
            //System.out.println(s);
            goEasy.publish("cmfz", s);
        } else if ("edit".equals(oper)){
            //修改
            userService.update(user);
        } else {
            //删除(修改为不显示)
            userService.delete(user);
            GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-3d071f1482a74517b136d152a1465b6b");
            Map map = showUserTime();
            String s = JSONUtils.toJSONString(map);
            //System.out.println(s);
            goEasy.publish("cmfz", s);
        }
        HashMap hashMap = new HashMap();
        hashMap.put("userId",user.getId());
        return hashMap;
    }
    @RequestMapping("upload")
    public void upload(String userId, MultipartFile photo, HttpServletRequest request){
        String originalFilename = photo.getOriginalFilename();
        System.out.println(originalFilename);
        User user=new User();
        String realPath = request.getSession().getServletContext().getRealPath("/upload/userImg/");
        File file = new File(realPath);
        if (!file.exists()) {
            //没有则多级创建
            file.mkdirs();
        }
        String http = HttpUtil.getHttp(photo, request, "/upload/userImg/");
        System.out.println(http);
        user.setPhoto(http);
       user.setId(userId);
        System.out.println(user);
        userService.update(user);
    }
    //接口
    @GetMapping("login")
    public Map login(String phone,String password){
        HashMap hashMap = new HashMap();
        User user = userDao.querryByPhone(phone);
        if(user==null){
            hashMap.put("status","-200");
            hashMap.put("message","用户不存在");
        }else{
            if(user.getPassword().equals(password)){
                hashMap.put("status","200");
                hashMap.put("user",user);
            }else {
                hashMap.put("status","-200");
                hashMap.put("message","密码错误");
            }
        }
        return hashMap;
    }
    //聊天
    @RequestMapping("talk")
    public void talkOn(String meg){
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-3d071f1482a74517b136d152a1465b6b");
        Map map = showUserTime();
        String s = JSONUtils.toJSONString(meg);
        //System.out.println(s);
        goEasy.publish("cmfz", s);
    }





    @Autowired
    BannerDao bannerDao;
    @Autowired
    AlbumDao albumDao;
    @Autowired
    ArticleDao articleDao;
    //一级页面
    @GetMapping("OnePage")
    public Map OnePage(String uid,String type,String sub_type){
        HashMap hashMap = new HashMap();
        try {
            if (type.equals("all")){
                List<Banner> banners = bannerDao.queryBannersByTime();
               // System.out.println(banners.size());
                List<Album> albums = albumDao.selectByRowBounds(null, new RowBounds(0, 5));
               // System.out.println(albums.size());
                List<Article> articles = articleDao.selectAll();
              //  System.out.println(articles.size());
                hashMap.put("status",200);
                hashMap.put("head",banners);
                hashMap.put("albums",albums);
                hashMap.put("articles",articles);
            }else if (type.equals("wen")){
                List<Album> albums = albumDao.selectByRowBounds(null, new RowBounds(0, 5));
                hashMap.put("status",200);
                hashMap.put("albums",albums);
            }else {
                if (sub_type.equals("ssyj")){
                    List<Article> articles = articleDao.selectAll();
                    hashMap.put("status",200);
                    //差需该用户
                   /* Example example = new Example(Focus.class);
                    Example.Criteria criteria = example.createCriteria();
                    criteria.andEqualTo("uid",uid);
                    List<Focus> foci = focusDao.selectByExample(example);
                    List articles = new ArrayList<>();
                    for (Focus focus : foci) {
                        Example example1 = new Example(Article.class);
                        Example.Criteria criteria1 = example.createCriteria();
                        criteria.andEqualTo("guruId",focus.getGid());
                        List<Article> articles1 = articleDao.selectByExample(example1);
                        articles.add(articles);
                    }*/

                    hashMap.put("status",200);
                    hashMap.put("articles",articles);
                }else {
                    List<Article> articles = articleDao.selectAll();
                    hashMap.put("status",200);
                    hashMap.put("articles",articles);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            hashMap.put("status","-200");
            hashMap.put("message","error");
        }

        return hashMap;
    }
}
