package com.geekhalo.feed.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class SpringWebConfiguration implements WebMvcConfigurer {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer hibernateCustomizer() {
        return builder -> {
            // //empty beans不需要报错，没有就是没有了
            builder.featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
            // //遇到不可识别字段的时候不要报错，因为前端传进来的字段不可信，可以不要影响正常业务逻辑
            builder.featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

            ////遇到不可以识别的枚举的时候，为了保证服务的强壮性，建议也不要关心未知的，甚至给个默认的，特别是微服务大家的枚举值随时在变，但是老的服务是不需要跟着一起变的
            builder.featuresToEnable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL);
            builder.featuresToEnable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE);

//            builder.modules(new Hibernate5Module());
        };
    }
}
