package com.lego.yxl.joininmemory.vo;

import com.lego.yxl.joininmemory.core.annotation.JoinInMemory;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@JoinInMemory(keyFromSourceData = "",
        keyFromJoinData = "#{id}",
        loader = "#{@userRepository.getByIds(#root)}",
        joinDataConverter = "#{T(com.yxl.demo.UserVO).apply(#root)}"
)
public @interface JoinUserVOOnId {
    @AliasFor(
            annotation = JoinInMemory.class
    )
    String keyFromSourceData();
}
