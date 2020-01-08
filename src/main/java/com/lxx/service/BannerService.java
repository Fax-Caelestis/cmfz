package com.lxx.service;

import com.lxx.entity.Banner;
import com.lxx.entity.BannerDto;

import java.util.List;

public interface BannerService {
    //添加
    public void insert(Banner banner);
    //删除
    public void deleteById(Banner banner);
    //修改
    public void update(Banner banner);
    //分页
    public List<Banner> querryPage(Banner banner,int index,int page);
    //差一个
    public Banner querryOne(Banner banner);
    //分页查询
    public BannerDto Bypage(Banner banner,int page, int count);
    //修改状态
    public void updateStatus(Banner banner);
}
