package com.yxl.vo;

import com.yxl.service.address.Address;
import lombok.Value;

/**
 */
@Value
public class AddressVO {
    private Long id;
    private String detail;

    public static AddressVO apply(Address address){
        if (address == null){
            return null;
        }
        return new AddressVO(address.getId(), address.getDetail());
    }
}
