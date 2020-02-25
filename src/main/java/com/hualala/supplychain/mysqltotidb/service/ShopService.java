package com.hualala.supplychain.mysqltotidb.service;

import com.alibaba.fastjson.JSONObject;
import com.hualala.supplychain.mysqltotidb.mapper.ShopMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Log4j2
public class ShopService {
    @Autowired
    private CommonService commonService;
    @Autowired
    private ShopMapper shopMapper;

    public void disposeShop(Map<String, String> shopMap) {
        for(String tableName : shopMap.keySet()){
            String ids = shopMap.get(tableName);
            //查询数据
            Long t1 = System.currentTimeMillis();
            List<JSONObject> jsonObjectList = shopMapper.selectAll(tableName, ids);
            Long t2 = System.currentTimeMillis();
            log.info("查询{}表，耗时{}ms，数据量：{}", tableName, t2 - t1, jsonObjectList.size());
            //设置主键ID，并新增
            commonService.insert(jsonObjectList, tableName, ids);
        }
    }
}
