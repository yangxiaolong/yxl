package com.lego.yxl.joininmemory.annotations;

import com.lego.yxl.core.joininmemory.annotation.JoinInMemory;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@JoinInMemory(keyFromSourceData = "",
        keyFromJoinData = "#{id}",
        loader = "#{@addressRepository.getByIds(#root)}",
        joinDataConverter = "#{T(com.lego.yxl.joininmemory.vo.AddressVO).apply(#root)}"
)
public @interface JoinAddressVOOnId {
    @AliasFor(
            annotation = JoinInMemory.class
    )
    String keyFromSourceData();
}
