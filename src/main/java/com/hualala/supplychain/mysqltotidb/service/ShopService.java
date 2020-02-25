package com.hualala.supplychain.mysqltotidb.service;

import com.alibaba.fastjson.JSONObject;
import com.hualala.supplychain.mysqltotidb.mapper.ShopMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
public class ShopService {
    @Autowired
    private CommonService commonService;
    @Autowired
    private ShopMapper shopMapper;
    @Value("${pageSize}")
    private Integer pageSize;

    public void disposeShop(Map<String, String> shopMap) {
        for(String tableName : shopMap.keySet()){
            log.info("{}表开始！！！", tableName);
            String ids = shopMap.get(tableName);
            Long t1 = System.currentTimeMillis();
            //查询数据总量
            Long count = shopMapper.getCount(tableName);
            log.info("{}表总数据量{}", tableName, count);
            Long pageCount = count / pageSize + 1;
            log.info("{}表总共分{}页，一页{}条数据", tableName, pageCount, pageSize);
            for(Integer pageNo=1; pageNo <= pageCount; pageNo++){
                //查询数据
                List<JSONObject> jsonObjectList = shopMapper.selectAll(tableName, ids, pageNo, pageSize);
                Long t2 = System.currentTimeMillis();
                log.info("查询{}表第{}页数据，耗时{}ms，数据量：{}，还剩{}页", tableName, pageNo, t2 - t1, jsonObjectList.size(), pageCount-pageNo);
                //设置主键ID，并新增
                List<String> keyList = new ArrayList<>();
                List<List<Object>> valueList = new ArrayList<>();
                commonService.setKeyAndValue(jsonObjectList, ids, keyList, valueList);
                commonService.insert(jsonObjectList, tableName, keyList, valueList);
            }
            log.info("{}表结束！！！", tableName);
        }
    }
}
