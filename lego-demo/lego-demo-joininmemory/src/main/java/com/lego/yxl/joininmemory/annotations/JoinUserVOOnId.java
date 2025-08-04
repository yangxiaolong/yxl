package com.lego.yxl.joininmemory.annotations;

import com.lego.yxl.core.joininmemory.annotation.JoinInMemory;
import com.lego.yxl.joininmemory.vo.UserVO;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@JoinInMemory(keyFromSourceData = "",
        keyFromJoinData = "#{id}",
        loader = "#{@userRepository.getByIds(#root)}",
        joinDataConverter = "#{T(com.lego.yxl.joininmemory.vo.UserVO).apply(#root)}"
)
public @interface JoinUserVOOnId {
    @AliasFor(
            annotation = JoinInMemory.class
    )
    String keyFromSourceData();
}
