package com.lego.yxl;

import com.lego.yxl.core.mybatis.ExampleConverterFactory;
import com.lego.yxl.core.mybatis.support.DefaultExampleConverterFactory;
import com.lego.yxl.core.mybatis.support.handler.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Created by taoli on 2022/8/26.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
@Configuration
@ConditionalOnClass(name = "org.apache.ibatis.session.SqlSessionFactory")
public class MyBatisBasedSingleQueryConfiguration {

    @Bean
    public ExampleConverterFactory converterFactory(List<FieldAnnotationHandler> handlers){
        return new DefaultExampleConverterFactory(handlers);
    }

    @Bean
    public FieldEqualToHandler fieldEqualToHandler(){
        return new FieldEqualToHandler();
    }

    @Bean
    public FieldGreaterThanHandler fieldGreaterThanHandler(){
        return new FieldGreaterThanHandler();
    }

    @Bean
    public FieldGreaterThanOrEqualToHandler fieldGreaterThanOrEqualToHandler(){
        return new FieldGreaterThanOrEqualToHandler();
    }

    @Bean
    public FieldInHandler fieldInHandler(){
        return new FieldInHandler();
    }

    @Bean
    public FieldIsNullHandler fieldIsNullHandler(){
        return new FieldIsNullHandler();
    }

    @Bean
    public FieldLessThanHandler fieldLessThanHandler(){
        return new FieldLessThanHandler();
    }

    @Bean
    public FieldLessThanOrEqualToHandler fieldLessThanOrEqualToHandler(){
        return new FieldLessThanOrEqualToHandler();
    }

    @Bean
    public FieldNotEqualToHandler fieldNotEqualToHandler(){
        return new FieldNotEqualToHandler();
    }

    @Bean
    public FieldNotInHandler fieldNotInHandler(){
        return new FieldNotInHandler();
    }
}
