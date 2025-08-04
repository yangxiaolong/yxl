package com.lego.yxl.core.singlequery;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * 分页参数
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pageable {
    /**
     * 页号
     */
    private Integer pageNo;
    /**
     * 每页大小
     */
    private Integer pageSize;

    public Integer limit(){
        return pageSize;
    }

    /**
     * 偏移起始量，从 0 开始
     * @return
     */
    public Integer offset(){
        if (pageNo == null || pageSize == null){
            return null;
        }
        return pageNo * pageSize;
    }

}
