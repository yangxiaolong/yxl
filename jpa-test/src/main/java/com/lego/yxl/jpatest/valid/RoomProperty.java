package com.lego.yxl.jpatest.valid;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;

/**
 * @auther yangxiaolong
 * @create 2024/12/29
 */
public class RoomProperty {

    public String name;

    public boolean finished;

    @NotNull
    public String getName() {
        return name;
    }

    @AssertTrue
    public boolean isFinished() {
        return finished;
    }

}
