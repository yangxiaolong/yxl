package com.geekhalo.tinyurl.domain.config;

import com.geekhalo.tinyurl.domain.codec.CustomBaseNumberCodec;
import com.geekhalo.tinyurl.domain.codec.RadixBasedNumberCodec;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NumberCodecConfiguration {

    @Value("${tinyurl.number.codec.radix:31}")
    private int radix;

    @Value("${tinyurl.number.codec.customStr:}")
    private String customStr;

    @Bean
    @ConditionalOnProperty(name = "tinyurl.number.codec.type", havingValue = "radix")
    public RadixBasedNumberCodec radixBasedNumberCodec(){
        return new RadixBasedNumberCodec(this.radix);
    }

    @Bean
    @ConditionalOnProperty(name = "tinyurl.number.codec.type", havingValue = "custom-base")
    public CustomBaseNumberCodec customBaseNumberCodec(){
        if (StringUtils.isEmpty(customStr)) {
            return new CustomBaseNumberCodec();
        }else {
            return new CustomBaseNumberCodec(customStr.toCharArray());
        }
    }
}
