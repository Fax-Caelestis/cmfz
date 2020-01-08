package com.lxx.service;

import com.lxx.dao.AdminDao;
import com.lxx.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminDao adminDao;
    @Override
    public Admin selectOne(Admin admin) {
        return adminDao.selectOne(admin);
    }
}
