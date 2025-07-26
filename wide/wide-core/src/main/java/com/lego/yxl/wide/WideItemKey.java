package com.lego.yxl.wide;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WideItemKey<TYPE extends Enum<TYPE> & WideItemType<TYPE>, KEY> {
    private TYPE type;
    private KEY key;
}
