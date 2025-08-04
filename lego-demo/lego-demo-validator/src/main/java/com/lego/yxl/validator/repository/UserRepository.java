package com.lego.yxl.validator.repository;

import com.lego.yxl.core.util.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 */
@Repository
@Slf4j
public class UserRepository {
    public List<User> getByIds(List<Long> ids){
        TimeUtils.sleepAsMS(10);
        return ids.stream()
                .distinct()
                .map(this::createUser)
                .collect(toList());
    }

    public User getById(Long id){
        log.info("Get User By Id {}", id);
        TimeUtils.sleepAsMS(3);
        return createUser(id);
    }

    private User createUser(Long id){
        return User.builder()
                .id(id)
                .name("用户-" + id)
                .build();
    }
}
