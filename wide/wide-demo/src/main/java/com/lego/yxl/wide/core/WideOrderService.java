package com.lego.yxl.wide.core;

import com.lego.yxl.wide.WideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WideOrderService {
    @Autowired
    private WideService<Long, WideOrderType> wideService;

    public void index(Long orderId){
        this.wideService.index(orderId);
    }

    public void updateOrder(Long orderId){
        this.wideService.updateItem(WideOrderType.ORDER, orderId);
    }

    public void updateUser(Long userId){
        this.wideService.updateItem(WideOrderType.USER, userId);
    }

    public void updateAddress(Long addressId){
        this.wideService.updateItem(WideOrderType.ADDRESS, addressId);
    }

    public void updateProduct(Long productId){
        this.wideService.updateItem(WideOrderType.PRODUCT, productId);
    }
}
