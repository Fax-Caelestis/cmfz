package com.lxx.cache;

import com.lxx.util.MyWebWare;
import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.locks.ReadWriteLock;

public class MybatisClass implements Cache {
    // 必须有一个Id属性
    private final String id;

    // 必须有个一个id属性的有参构造
    public MybatisClass(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    // 添加缓存
    @Override
    public void putObject(Object key, Object value) {
        RedisTemplate redisTemplate = (RedisTemplate) MyWebWare.getBeanByName("redisTemplate");
        // 通过RedisTemplate对象操作Redis
        // 注意: 存储时使用hash类型存储数据 方便数据更改时删除当前namespace的所有数据
        redisTemplate.opsForHash().put(this.id,key.toString(),value);
    }
    // 取数据
    @Override
    public Object getObject(Object key) {
        // 通过工具类获取RedisTemplate对象
        RedisTemplate redisTemplate = (RedisTemplate) MyWebWare.getBeanByName("redisTemplate");
        Object o = redisTemplate.opsForHash().get(this.id, key.toString());
        return o;
    }

    @Override
    public Object removeObject(Object o) {
        return null;
    }

    @Override
    public void clear() {
        // 通过工具类获取RedisTemplate对象
        RedisTemplate redisTemplate = (RedisTemplate) MyWebWare.getBeanByName("redisTemplate");
        redisTemplate.delete(this.id);
    }
    //删除缓存
    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return null;
    }
}
