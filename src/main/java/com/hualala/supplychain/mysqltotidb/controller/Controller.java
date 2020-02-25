package com.hualala.supplychain.mysqltotidb.controller;

import com.hualala.supplychain.mysqltotidb.config.MapConfig;
import com.hualala.supplychain.mysqltotidb.service.BasicService;
import com.hualala.supplychain.mysqltotidb.service.BillService;
import com.hualala.supplychain.mysqltotidb.service.ShopService;
import com.hualala.supplychain.mysqltotidb.service.VoucherService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Log4j2
public class Controller {
    @Autowired
    private MapConfig mapConfig;

    @Autowired
    private BillService billService;
    @Autowired
    private BasicService basicService;
    @Autowired
    private VoucherService voucherService;
    @Autowired
    private ShopService shopService;

    @RequestMapping("/test")
    public void test(){
        Map<String, String> basicMap = mapConfig.getBasicMap();
        Map<String, String> billMap = mapConfig.getBillMap();
        Map<String, String> voucherMap = mapConfig.getVoucherMap();
        Map<String, String> shopMap = mapConfig.getShopMap();

        //处理basic
        basicService.disposeBasic(basicMap);

        //处理bill
        billService.disposeBill(billMap);

        //处理voucher
        voucherService.disposeVoucher(voucherMap);

        //处理voucher
        shopService.disposeShop(shopMap);

        log.info("======================> 处理结束了！！！");
    }
}
