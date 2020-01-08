package com.lxx.controller;

import com.lxx.entity.Guru;
import com.lxx.service.GuruService;
import com.lxx.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("guru")
public class GuruController {
    @Autowired
    GuruService guruService;

    //查所有上师
    @RequestMapping("all")
    public List<Guru> all(){
        return guruService.querryAll();
    }
    @RequestMapping("changeMeg")
    public Map changeMeg( String oper,Guru guru){
        if ("add".equals(oper)){
            //添加
            guru.setId(UUID.randomUUID().toString());
                guruService.insert(guru);
        } else if ("edit".equals(oper)){
            //修改
                guruService.upload(guru);
        } else {
            //删除(修改为不显示)
            guruService.delete(guru);
        }
        HashMap hashMap = new HashMap();
        hashMap.put("guruId",guru.getId());
        System.out.println(hashMap.get("guruId"));
        return hashMap;
    }
    @RequestMapping("uploadHttp")
    public void uploadHttp(MultipartFile photo, String guruId, HttpServletRequest request){
        System.out.println(guruId+"4444444");
        String realPath = request.getSession().getServletContext().getRealPath("/upload/guruimg/");
        File file = new File(realPath);
        if (!file.exists()) {
            //没有则多级创建
            file.mkdirs();
        }
        String http = HttpUtil.getHttp(photo, request, "/upload/guruimg/");
        Guru guru= new Guru();
        guru.setId(guruId);
        guru.setPhoto(http);
        System.out.println(guru+"11111");
        guruService.upload(guru);
    }
}
