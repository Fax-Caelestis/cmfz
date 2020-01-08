package com.lxx.service;

import com.lxx.aspect.Mylog;
import com.lxx.dao.BannerDao;
import com.lxx.entity.Banner;
import com.lxx.entity.BannerDto;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class BannerServiceImpl implements BannerService {
    @Autowired
    BannerDao bannerDao;
    @Mylog(name = "添加轮播图")
    @Override//添加
    public void insert(Banner banner) {
        banner.setId(UUID.randomUUID().toString());
        bannerDao.insert(banner);
    }
    @Mylog(name = "删除一个轮播图")
    @Override//删除
    public void deleteById(Banner banner) {
        bannerDao.deleteByPrimaryKey(banner.getId());
    }

    @Mylog(name = "修改轮播图信息")
    @Override//修改
    public void update(Banner banner) {
        bannerDao.updateByPrimaryKeySelective(banner);
    }
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    @Override//分页
    public List<Banner> querryPage(Banner banner, int index, int page) {
        return bannerDao.selectByRowBounds(banner,new RowBounds(index,page));
    }
    //差一个
    @Override
    public Banner querryOne(Banner banner) {
        return bannerDao.selectOne(banner);
    }
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    @Override
    public BannerDto Bypage(Banner banner, int page, int count) {
        BannerDto dto=new BannerDto();
        //设置当前页
        dto.setPage(page);
        //设置总行数
        int i = bannerDao.selectCount(new Banner());
        dto.setRecords(i);
        //设置总页数
        dto.setTotal(i%count==0?i/count:i/count+1);
        //设置当前页的数据行
        //当前页数的下标
        int inx=(page-1)*count;
        dto.setRows(bannerDao.selectByRowBounds(banner,new RowBounds(inx,count)));
        return dto;
    }
    @Mylog(name = "修改轮播图状态")
    //修改装台
    @Override
    public void updateStatus(Banner banner) {
        Banner banner1 = bannerDao.selectByPrimaryKey(banner.getId());
        if("1".equals(banner1.getStatus())){
            banner1.setStatus("2");
        }else {
            banner1.setStatus("1");
        }
        bannerDao.updateStatus(banner1);
    }
}
