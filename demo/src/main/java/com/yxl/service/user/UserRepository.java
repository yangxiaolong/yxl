package com.yxl.service.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static com.yxl.util.TimeUtils.sleepAsMS;

/**
 */
@Repository
@Slf4j
public class UserRepository {
    public List<User> getByIds(List<Long> ids){
        sleepAsMS(10);
        return ids.stream()
                .distinct()
                .map(this::createUser)
                .collect(toList());
    }

    public User getById(Long id){
        log.info("Get User By Id {}", id);
        sleepAsMS(3);
        return createUser(id);
    }

    private User createUser(Long id){
        return User.builder()
                .id(id)
                .name("用户-" + id)
                .build();
    }
}
