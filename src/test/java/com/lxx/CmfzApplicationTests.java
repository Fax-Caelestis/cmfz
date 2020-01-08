package com.lxx;

import com.lxx.dao.AdminDao;
import com.lxx.entity.Admin;
import com.lxx.service.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest(classes = CmfzApplication.class)
@RunWith(SpringRunner.class)
public class CmfzApplicationTests {
    @Autowired
    AdminDao adminDao;
    @Autowired
    AdminService adminService;
    @Test
    public void contextLoads() {
        List<Admin> admins = adminDao.selectAll();
        System.out.println(admins);
    }

    @Test
    public void name() {
        Admin admin = new Admin();
        admin.setUsername("admin");
        admin.setPassword("admin");
        Admin select = adminDao.selectOne(admin);
        System.out.println(select);
    }

    @Test
        public void sss() {
        Admin admin = new Admin();
        admin.setUsername("lxx");
        admin.setPassword("123");
        Admin admin1 = adminService.selectOne(admin);
        System.out.println(admin1);
    }
}
