package com.hualala.supplychain.mysqltotidb.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "tables")
@EnableConfigurationProperties(MapConfig.class)
public class MapConfig {
    private Map<String, String> billMap = new LinkedHashMap<>();
    private Map<String, String> basicMap = new LinkedHashMap<>();
    private Map<String, String> voucherMap = new LinkedHashMap<>();
    private Map<String, String> shopMap = new LinkedHashMap<>();

    public Map<String, String> getBillMap() {
        return billMap;
    }

    public void setBillMap(Map<String, String> billMap) {
        this.billMap = billMap;
    }

    public Map<String, String> getBasicMap() {
        return basicMap;
    }

    public void setBasicMap(Map<String, String> basicMap) {
        this.basicMap = basicMap;
    }

    public Map<String, String> getVoucherMap() {
        return voucherMap;
    }

    public void setVoucherMap(Map<String, String> voucherMap) {
        this.voucherMap = voucherMap;
    }

    public Map<String, String> getShopMap() {
        return shopMap;
    }

    public void setShopMap(Map<String, String> shopMap) {
        this.shopMap = shopMap;
    }
}
