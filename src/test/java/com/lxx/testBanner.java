package com.lxx;

import com.lxx.dao.BannerDao;
import com.lxx.entity.Banner;
import com.lxx.service.BannerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest(classes = CmfzApplication.class)
@RunWith(SpringRunner.class)
public class testBanner {
    @Autowired
    BannerDao bannerDao;
    @Autowired
    BannerService bannerService;
    @Test
    public void TestPage() {
        Banner banner=new Banner();
        //第一个参数从下标开始  每页展示第二个参数数据
        List<Banner> banners = bannerService.querryPage(banner,0, 2);
        for (Banner banner1 : banners) {
            System.out.println(banner1);
        }
    }

    @Test
    public void name() {
        Banner banner = new Banner();
        banner.setId("1");
        bannerService.updateStatus(banner);
       /* List<Banner> banners = bannerDao.selectAll();
        for (Banner banner : banners) {
            System.out.println(banner);
        }*//*
        int i = bannerDao.selectCount(new Banner());
        System.out.println(i);*/
    }
}
