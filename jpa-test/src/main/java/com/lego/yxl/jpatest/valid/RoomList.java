package com.lego.yxl.jpatest.valid;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @auther yangxiaolong
 * @create 2024/12/29
 */
@Data
@AllArgsConstructor()
public class RoomList {

    private List<@Valid @NotNull Room> rooms;

}
