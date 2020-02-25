package com.hualala.supplychain.mysqltotidb.mapper;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@DS("mysql-stock")
public interface VoucherMapper {
    List<JSONObject> selectAll(@Param("tableName") String tableName, @Param("ids") String ids, @Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);
    Long getCount(@Param("tableName") String tableName);
}
