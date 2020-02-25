package com.hualala.supplychain.mysqltotidb.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@DS("tidb")
@Component
public interface TidbMapper {
    void insert(@Param("sql") String sql);
}
