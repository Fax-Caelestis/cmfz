package com.lxx.dao;

import com.lxx.entity.Counter;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface CounterDao extends Mapper<Counter> {
    void UpdateCount(@Param("id") String id, @Param("counts")Integer counts);
}
