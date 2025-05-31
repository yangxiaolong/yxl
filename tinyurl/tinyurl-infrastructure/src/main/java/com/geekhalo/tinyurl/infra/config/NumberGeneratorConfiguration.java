package com.geekhalo.tinyurl.infra.config;

import com.geekhalo.tinyurl.domain.generator.NumberGenerator;
import com.geekhalo.tinyurl.infra.generator.QueueBasedAsyncNumberGenerator;
import com.geekhalo.tinyurl.infra.generator.db.DBBasedBatchNumberGenerator;
import com.geekhalo.tinyurl.infra.generator.db.DBBasedSingleNumberGenerator;
import com.geekhalo.tinyurl.infra.generator.redis.RedisBasedBatchNumberGenerator;
import com.geekhalo.tinyurl.infra.generator.redis.RedisBasedSingleNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class NumberGeneratorConfiguration {

    @Value("${tinyurl.number.generator.async:false}")
    private boolean asyncEnable;

    @Value("${tinyurl.number.generator.buffer:500}")
    private Integer buffer;

    @ConditionalOnProperty(name = "tinyurl.number.generator.type", havingValue = "redis-single")
    @ConditionalOnClass(RedisTemplate.class)
    @Bean(name = "realNumberGenerator")
    public NumberGenerator redisBasedSingleNumberGenerator(){
        return new RedisBasedSingleNumberGenerator();
    }

    @ConditionalOnProperty(name = "tinyurl.number.generator.type", havingValue = "redis-batch")
    @ConditionalOnClass(RedisTemplate.class)
    @Bean(name = "realNumberGenerator")
    public NumberGenerator redisBasedBatchNumberGenerator(){
        return new RedisBasedBatchNumberGenerator();
    }

    @ConditionalOnProperty(name = "tinyurl.number.generator.type", havingValue = "db-single")
    @Bean(name = "realNumberGenerator")
    public NumberGenerator dbBasedSingleNumberGenerator(){
        return new DBBasedSingleNumberGenerator();
    }

    @ConditionalOnProperty(name = "tinyurl.number.generator.type", havingValue = "db-batch")
    @Bean(name = "realNumberGenerator")
    public NumberGenerator dbBasedBatchNumberGenerator(){
        return new DBBasedBatchNumberGenerator();
    }

    @Bean
    @Primary
    public NumberGenerator wrapperFor(@Autowired @Qualifier("realNumberGenerator") NumberGenerator numberGenerator){
        if (asyncEnable){
            return new QueueBasedAsyncNumberGenerator(numberGenerator, this.buffer);
        }else {
            return numberGenerator;
        }
    }
}
