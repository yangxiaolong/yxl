package com.lego.yxl.joininmemory.vo;

import com.lego.yxl.joininmemory.repository.User;
import lombok.Value;

/**
 */
@Value
public class UserVO {
    private Long id;
    private String name;

    public static UserVO apply(User user) {
        if (user == null) {
            return null;
        }

        return new UserVO(user.getId(), user.getName());
    }
}
