package com.lego.yxl.repository.address;

import com.lego.yxl.util.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 *
 */
@Repository
@Slf4j
public class AddressRepository {
    public Address getDefaultAddressByUserId(Long userId) {
        log.info("Load Default Address For User {}", userId);
        Address address = createAddress(RandomUtils.nextLong());
        address.setUserId(userId);
        return address;
    }

    public List<Address> getByIds(List<Long> ids) {
        TimeUtils.sleepAsMS(10);
        return ids.stream()
                .distinct()
                .map(this::createAddress)
                .collect(toList());
    }

    public Address getById(Long id) {
        TimeUtils.sleepAsMS(3);
        return createAddress(id);
    }

    private Address createAddress(Long id) {
        return Address.builder()
                .id(id)
                .userId(RandomUtils.nextLong())
                .detail("详细地址-" + id)
                .build();
    }
}
