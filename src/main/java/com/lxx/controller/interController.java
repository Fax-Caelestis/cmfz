package com.lxx.controller;

import com.lxx.dao.*;
import com.lxx.entity.*;
import com.lxx.util.MesageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping("inter")
public class interController {
    @Autowired
    FocusDao focusDao;
    @Autowired
    UserDao userDao;
    @Autowired
    CounterDao counterDao;
    @Autowired
    AlbumDao albumDao;
    @Autowired
    ArticleDao articleDao;
    @Autowired
    ChapterDao chapterDao;
    @Autowired
    GuruDao guruDao;
    @Autowired
    CourseDao courseDao;

    //查询问文章详情
    @GetMapping("selectOneArticle")
    public Map selectOneArticle(String uid,String id){
        HashMap hashMap = new HashMap();
        try {
            Example example = new Example(Article.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("id",id);
            criteria.andEqualTo("guruId",uid);
            Article article = articleDao.selectOneByExample(example);
            hashMap.put("status","200");
            hashMap.put("article",article);
        }catch (Exception e){
            hashMap.put("status","-200");
            e.printStackTrace();
        }

        return hashMap;
    }
    //查询专辑详情
    @GetMapping("selectOneAlbumMeg")
    public Map selectOneAlbumMeg(String uid,String id){
        HashMap hashMap = new HashMap();
            Album album = new Album();
            album.setId(id);
            Album album1 = albumDao.selectOne(album);
            //查询该专辑下的所有章节
            Example example = new Example(Chapter.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("albumId",id);
            List<Chapter> chapters = chapterDao.selectByExample(example);
            hashMap.put("status","200");
            hashMap.put("ablum",album1);
            hashMap.put("list",chapters);
           if(chapters.size()==0){
               hashMap.put("status","-200");
               hashMap.put("meg","未找到");
           }


        return hashMap;
    }
    //展示上师列表
    @GetMapping("allGuru")
    public Map selectOneAlbumMeg(String id){
        HashMap hashMap = new HashMap();
        try {
            List<Guru> all = guruDao.selectAll();
            hashMap.put("status","200");
            hashMap.put("message","成功");
            hashMap.put("list",all);
        }catch (Exception e){
            hashMap.put("status","-200");
            hashMap.put("message","未找到");
            e.printStackTrace();
        }
        return hashMap;
    }
    //查询所有功课
    @GetMapping("showAllCourse")
    public Map showAllCourse(String uid){
        HashMap hashMap = new HashMap();
        List<Course> courses = courseDao.querryByUserId(uid);
        hashMap.put("status","200");
        hashMap.put("option",courses);
        return hashMap;
    }
    //添加功课
    @PutMapping("addcourse")
    public Map addcourse(String uid,String title){
        HashMap hashMap = new HashMap();
        try {
            Course course = new Course();
            course.setId(UUID.randomUUID().toString());
            course.setCreateDate(new Date());
            course.setTitle(title);
            course.setUser_id(uid);
            courseDao.insert(course);
            List<Course> courses = courseDao.querryByUserId(uid);
            hashMap.put("status","200");
            hashMap.put("option",courses);
        }catch (Exception e){
            hashMap.put("status","200");
            hashMap.put("option","添加出错");
        }
        return hashMap;

    }
    //删除一个功课
    @DeleteMapping("deecourse")
    public Map deecourse(String id,String uid){
        HashMap hashMap = new HashMap();
        courseDao.deleteByPrimaryKey(id);
        List<Course> courses = courseDao.querryByUserId(uid);
        hashMap.put("status","200");
        hashMap.put("option",courses);
        return hashMap;
    }
    //展示计数器
    @GetMapping("showAllCounter")
    public Map showAllCounter(String uid,String cid){
        HashMap hashMap = new HashMap();
        Example example = new Example(Counter.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",uid);
        criteria.andEqualTo("courseId",cid);
        List<Counter> counters = counterDao.selectByExample(example);
        hashMap.put("status","200");
        hashMap.put("counters",counters);
        return hashMap;
    }
    //添加计数器
    @PutMapping("addCounter")
    public Map addCounter(String uid,String title,String cid){
        HashMap hashMap = new HashMap();
        Counter counter = new Counter();
        counter.setId(UUID.randomUUID().toString());
        counter.setCounts(0);
        counter.setCourseId(cid);
        counter.setCreateDate(new Date());
        counter.setUserId(uid);
        counter.setTitle(title);
        counterDao.insert(counter);
        Example example = new Example(Counter.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",uid);
        criteria.andEqualTo("courseId",cid);
        List<Counter> counters = counterDao.selectByExample(example);
        hashMap.put("status","200");
        hashMap.put("counters",counters);
        return hashMap;
    }
    //删除计数器
    @DeleteMapping("deeCounter")
    public Map deeCounter(String uid,String id,String cid){
        HashMap hashMap = new HashMap();
        counterDao.deleteByPrimaryKey(id);
        Example example = new Example(Counter.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",uid);
        criteria.andEqualTo("courseId",cid);
        List<Counter> counters = counterDao.selectByExample(example);
        hashMap.put("status","200");
        hashMap.put("counters",counters);
        return hashMap;
    }
    //修改计数器
    @PostMapping("updateCounter")
    public Map updateCounter(String uid,String id,String cid,Integer counts){
        HashMap hashMap = new HashMap();

        counterDao.UpdateCount(id,counts);
        Example example = new Example(Counter.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",uid);
        criteria.andEqualTo("courseId",cid);
        List<Counter> counters = counterDao.selectByExample(example);
        hashMap.put("status","200");
        hashMap.put("counters",counters);
        return hashMap;
    }
    //修改个人信息
    @PostMapping("updateUser")
    public Map updateUser(String id,String sex,String location,String sign,String nick_name,String password){
        HashMap hashMap = new HashMap();
        User user = new User();
        user.setId(id);
        user.setSex(sex);
        user.setLocation(location);
        user.setSign(sign);
        user.setNickName(nick_name);
        user.setPassword(password);
        userDao.updateByPrimaryKeySelective(user);
        User user1 = userDao.selectByPrimaryKey(id);
        hashMap.put("status","200");
        hashMap.put("user",user1);
        return hashMap;
    }
    //寻找金刚道友
    @GetMapping("querryFriend")
    public Map querryFriend(String id){
        HashMap hashMap = new HashMap();
        List<User> users = userDao.querryFriend(id);
        hashMap.put("users",users);
        return hashMap;
    }
    //发送验证码
    @GetMapping("sendcode")
    public Map sendcode(String phone){

        String s = UUID.randomUUID().toString();
        String code = s.substring(0, 4);
        MesageUtil.send(phone,code);
        // 将验证码保存值Redis  Key phone_186XXXX Value code 1分钟有效
        HashMap hashMap = new HashMap();
        hashMap.put("status","200");
        hashMap.put("message","短信发送成功");
        Jedis jedis = new Jedis("192.168.202.15");
        jedis.setex(phone,1200,code);
        if(phone==""){
            hashMap.put("status","-200");
            hashMap.put("message","没填手机号");
        }else if(phone.length()!=11){
            hashMap.put("status","-200");
            hashMap.put("message","不是一个手机号");
        }
        return hashMap;
    }
    //判断验证码
    @GetMapping("ifcodeok")
    public Map ifcodeok(String code, String phone, HttpServletRequest request){
        HashMap hashMap = new HashMap();
        Jedis jedis = new Jedis("192.168.202.15");
        String s = jedis.get(phone);
        HttpSession session = request.getSession();
        session.setAttribute("phone",phone);
        if (s.equals(code)){
            hashMap.put("status","200");
            hashMap.put("message","验证成功");
        }else {
            hashMap.put("status","-200");
            hashMap.put("message","失败");
        }
        return hashMap;
    }
    //添加用户
    @PutMapping("addUser")
    public Map addUser(HttpServletRequest request,String name,String sex,String location,String sign,String nick_name,String password){
        HttpSession session = request.getSession();
        String phone = (String) session.getAttribute("phone");
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setPhone(phone);
        user.setPassword(password);
        user.setSex(sex);
        user.setName(name);
        user.setNickName(nick_name);
        user.setLocation(location);
        user.setSign(sign);
        userDao.insert(user);
        HashMap hashMap = new HashMap();
        hashMap.put("200","添加成功");
        hashMap.put("user",user);
        return hashMap;
    }
    //添加关注上师  uid===>用户id   id=====》大师id
    @PutMapping("addFocus")
    public Map addFocus(String uid,String id){
        Focus focus = new Focus();
        focus.setUid(uid);
        focus.setGid(id);
        focusDao.insert(focus);//添加
        //查询一个用户关注的所有上师  先从focus查出该用户关注的上师id
        //设置查询条件   实体类uid====uid
        Example example = new Example(Focus.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("uid",uid);
        List<Focus> foci = focusDao.selectByExample(example);

        ArrayList<Guru> guruslist = new ArrayList<>();
        //便利foci找到该用户关注的所有上师   炳根句id查出上师相关信息
        for (Focus focus1 : foci) {
            Guru guru = guruDao.selectByPrimaryKey(focus1.getGid());
            guruslist.add(guru);
        }
        HashMap hashMap = new HashMap();
        hashMap.put("status","200");
        hashMap.put("message","-------");
        hashMap.put("list",guruslist);
        return hashMap;
    }
}
