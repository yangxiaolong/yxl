package com.yxl.vo;

import com.yxl.service.user.User;
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
