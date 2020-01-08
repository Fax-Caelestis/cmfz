package com.lxx;

import com.lxx.dao.UserDao;
import com.lxx.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

@SpringBootTest(classes = CmfzApplication.class)
@RunWith(SpringRunner.class)
public class testUser {
    @Autowired
    UserDao userDao;

    @Test
    public void name() {
        User user = userDao.querryByPhone("789987");
        System.out.println(user);
        /*List<UserMapDTO> userMapDTOS = userDao.querryBylocation(1);

        for (UserMapDTO userMapDTO : userMapDTOS) {
            System.out.println(userMapDTO);
        }*/
    }

    @Test
    public void fdsf() {
        Jedis jedis = new Jedis("192.168.202.15");
        String s = jedis.get("18835957973");
        System.out.println(s);
    }
}
