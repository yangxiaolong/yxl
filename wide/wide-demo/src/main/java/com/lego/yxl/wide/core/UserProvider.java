package com.lego.yxl.wide.core;

import com.lego.yxl.wide.WideItemDataProvider;
import com.lego.yxl.wide.jpa.User;
import com.lego.yxl.wide.jpa.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserProvider implements WideItemDataProvider<WideOrderType, Long, User> {
    @Autowired
    private UserDao userDao;

    @Override
    public List<User> apply(List<Long> key) {
        return userDao.findAllById(key);
    }

    @Override
    public WideOrderType getSupportType() {
        return WideOrderType.USER;
    }
}
