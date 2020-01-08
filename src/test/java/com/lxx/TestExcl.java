package com.lxx;

import com.alibaba.excel.EasyExcel;
import com.lxx.dao.BannerDao;
import com.lxx.data.BannerDataListener;
import com.lxx.entity.Banner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@SpringBootTest(classes = CmfzApplication.class)
@RunWith(SpringRunner.class)
public class TestExcl {
    @Autowired
    BannerDao bannerDao;
    @Test
    public void name() {
        String url="F:\\后期项目\\测试xls\\"+new Date().getTime()+".xls";
        List<Banner> banners = bannerDao.selectAll();
        EasyExcel.write(url,Banner.class)
                .sheet("测试")
                .doWrite(banners);
    }
    @Test
    public void test02(){
        String url = "F:\\后期项目\\测试xls\\1578015869479.xls";
        // readListener : 读取数据时的监听器  每次使用DemoDataListener都需要new  不要把DemoDataListener交给Spring工厂管理
        // 文件上传 : MFile url  文件上传  File file = new File();
        EasyExcel.read(url,Banner.class,new BannerDataListener()).sheet().doRead();
    }
}
