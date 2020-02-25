package com.hualala.supplychain.mysqltotidb.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@DS("tidb")
@Component
public interface TidbMapper {
    void insert(@Param("keyList") List<String> keyList, @Param("valueList") List<List<Object>> valueList, @Param("tableName") String tableName);
}
