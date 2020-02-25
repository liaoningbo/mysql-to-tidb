package com.hualala.supplychain.mysqltotidb.service;

import com.alibaba.fastjson.JSONObject;
import com.hualala.supplychain.mysqltotidb.mapper.TidbMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

            List<String> keyList = new ArrayList<>();
            List<List<Object>> valueList = new ArrayList<>();

            setKeyAndValue(subList, ids, keyList, valueList);
            Long t1 = System.currentTimeMillis();
            tidbMapper.insert(keyList, valueList, tableName);
            Long t2 = System.currentTimeMillis();
            log.info("{}表新增{}条数据，耗时{}ms, {}表还剩{}条数据", tableName, subList.size(), t2 - t1, tableName, totalSize - (i*pageSize));
        }
    }

    private void setKeyAndValue(List<JSONObject> jsonObjectList, String ids,  List<String> keyList, List<List<Object>> valueList){
        //setKey
        String[] idArr = ids.split(",");
        if(idArr.length > 1){
            keyList.add("id");
        }
        keyList.addAll(jsonObjectList.get(0).keySet().stream().map(a -> a.toString()).collect(Collectors.toList()));
        //setValue
        for(JSONObject jsonObject : jsonObjectList){
            List<Object> subList = new ArrayList<>();
            int firstNum = 0;
            if(idArr.length > 1){
                StringBuilder idValue = new StringBuilder();
                for(String id : idArr){
                    idValue.append(jsonObject.get(id)+"-");
                }
                idValue.deleteCharAt(idValue.length() - 1);
                subList.add(idValue.toString());
                firstNum = 1;
            }
            for(int i=firstNum; i<keyList.size(); i++){
                String key = keyList.get(i);
                if(jsonObject.containsKey(key)){
                    subList.add(jsonObject.get(key));
                }else{
                    subList.add("");
                }
            }
            valueList.add(subList);
        }
    }
}
