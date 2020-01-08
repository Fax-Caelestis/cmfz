package com.lxx.service;

import com.lxx.dao.GuruDao;
import com.lxx.entity.Guru;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
@Service
public class GuruServiceImpl implements GuruService {
    @Autowired
    GuruDao guruDao;
    @Override
    public List<Guru> querryAll() {
        return guruDao.selectAll();
    }

    @Override
    public void insert(Guru guru) {

        guruDao.insertSelective(guru);
    }

    @Override
    public void upload(Guru guru) {
        guruDao.updateByPrimaryKeySelective(guru);
    }

    @Override
    public void delete(Guru guru) {
        guruDao.delete(guru);
    }

    @Override
    public Guru selectOne(Guru guru) {
        return null;
    }
}
