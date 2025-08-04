package com.lego.yxl.wide.core;

import com.lego.yxl.core.wide.WideItemDataProvider;
import com.lego.yxl.wide.jpa.Address;
import com.lego.yxl.wide.jpa.AddressDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AddressProvider implements WideItemDataProvider<WideOrderType, Long, Address> {
    @Autowired
    private AddressDao addressDao;

    @Override
    public List<Address> apply(List<Long> key) {
        return addressDao.findAllById(key);
    }

    @Override
    public WideOrderType getSupportType() {
        return WideOrderType.ADDRESS;
    }
}
