package com.lego.yxl.jpatest.valid;

import jakarta.validation.valueextraction.ExtractedValue;
import jakarta.validation.valueextraction.ValueExtractor;

/**
 * @auther yangxiaolong
 * @create 2024/12/29
 */
public class ResultValueExtractor implements ValueExtractor<Result<@ExtractedValue ?>> {

    @Override
    public void extractValues(Result<?> originalValue, ValueReceiver receiver) {
        receiver.value(null, originalValue.getData());
    }

}
