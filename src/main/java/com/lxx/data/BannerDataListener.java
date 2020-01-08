package com.lxx.data;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.lxx.dao.BannerDao;
import com.lxx.entity.Banner;
import com.lxx.util.ApplicationContextUtil;

import java.util.ArrayList;
import java.util.List;

public class BannerDataListener extends AnalysisEventListener<Banner> {
    List<Banner> list = new ArrayList<Banner>();

    @Override
    public void invoke(Banner banner, AnalysisContext analysisContext) {
        System.out.println(banner);
        list.add(banner);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        ApplicationContextUtil applicationContextUtil = new ApplicationContextUtil();
        BannerDao bannerDao = (BannerDao) applicationContextUtil.getBean(BannerDao.class);
       bannerDao.insertList(list);
    }
}
