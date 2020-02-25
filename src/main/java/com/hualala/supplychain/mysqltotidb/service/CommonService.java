package com.hualala.supplychain.mysqltotidb.service;

import com.alibaba.fastjson.JSONObject;
import com.hualala.supplychain.mysqltotidb.mapper.TidbMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class CommonService {

    @Autowired
    private TidbMapper tidbMapper;
    @Value("${pageSize}")
    private Integer pageSize;

    public void insert(List<JSONObject> jsonObjectList, String tableName, String ids) {
        Integer totalSize = jsonObjectList.size();
        int pageCount = jsonObjectList.size() / pageSize + (totalSize % pageSize > 0 ? 1 : 0);

        for(int i=1; i <= pageCount; i++){
            List<JSONObject> subList;
            if (i == pageCount && totalSize % pageSize > 0) {
                subList = jsonObjectList.subList((i - 1) * pageSize, totalSize);
            } else if (i < pageCount) {
                subList = jsonObjectList.subList((i - 1) * pageSize, i * pageSize);
            }else{
                return;
            }
            String sql = getSql(subList, tableName, ids);
            Long t1 = System.currentTimeMillis();
            tidbMapper.insert(sql);
            Long t2 = System.currentTimeMillis();
            log.info("{}表新增{}条数据，耗时{}ms, {}表还剩{}条数据", tableName, subList.size(), t2 - t1, tableName, totalSize - (i*pageSize));
        }
    }

    private String getSql(List<JSONObject> jsonObjectList, String tableName, String ids){
        List<String> keyList = jsonObjectList.get(0).keySet().stream().map(a -> a.toString()).collect(Collectors.toList());
        StringBuilder sql = new StringBuilder("insert into ");
        sql.append(tableName);
        String[] idArr = ids.split(",");
        if(idArr.length == 1){
            sql.append("(");
        }else{
            sql.append("(id,");
        }
        for(String key : keyList){
            sql.append(key+",");
        }
        sql.deleteCharAt(sql.length() - 1);
        sql.append(") values ");
        for(JSONObject jsonObject : jsonObjectList){
            if(idArr.length > 1){
                sql.append("('");
                for(String id : ids.split(",")){
                    sql.append(jsonObject.get(id)+"-");
                }
                sql.deleteCharAt(sql.length() - 1);
                sql.append("',");
            }else{
                sql.append("(");
            }

            for(String key : keyList){
                sql.append("'" + jsonObject.get(key)+"',");
            }
            sql.deleteCharAt(sql.length() - 1);
            sql.append("),");
        }
        sql.deleteCharAt(sql.length() - 1);
        sql.append(";");
        return sql.toString();
    }
}
