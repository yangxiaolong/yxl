package com.lego.yxl.singlequery.starter;

import com.lego.yxl.core.singlequery.jpa.SpecificationConverterFactory;
import com.lego.yxl.core.singlequery.jpa.support.DefaultSpecificationConverterFactory;
import com.lego.yxl.core.singlequery.jpa.support.handler.*;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.sql.DataSource;
import java.util.List;

@ConditionalOnClass({JpaRepository.class, DataSource.class})
@AutoConfiguration(after = {
        JpaRepositoriesAutoConfiguration.class,
        DataSourceAutoConfiguration.class
})
@Configuration
public class JpaBasedSingleQueryConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public SpecificationConverterFactory specificationConverterFactory(List<JpaAnnotationHandler> annotationHandlers) {
        return new DefaultSpecificationConverterFactory(annotationHandlers);
    }

    @Bean
    public JpaFieldEqualToHandler jpaFieldEqualToHandler() {
        return new JpaFieldEqualToHandler();
    }

    @Bean
    public JpaFieldGreaterThanHandler jpaGreaterThanHandler() {
        return new JpaFieldGreaterThanHandler();
    }

    @Bean
    public JpaFieldGreaterThanOrEqualToHandler jpaFieldGreaterThanOrEqualToHandler() {
        return new JpaFieldGreaterThanOrEqualToHandler();
    }

    @Bean
    public JpaFieldInHandler jpaFieldInHandler() {
        return new JpaFieldInHandler();
    }

    @Bean
    public JpaFieldIsNullHandler jpaFieldIsNullHandler() {
        return new JpaFieldIsNullHandler();
    }

    @Bean
    public JpaFieldLessThanHandler jpaFieldLessThanHandler() {
        return new JpaFieldLessThanHandler();
    }

    @Bean
    public JpaFieldLessThanOrEqualToHandler jpaFieldLessThanOrEqualToHandler() {
        return new JpaFieldLessThanOrEqualToHandler();
    }

    @Bean
    public JpaFieldNotEqualToHandler jpaFieldNotEqualToHandler() {
        return new JpaFieldNotEqualToHandler();
    }

    @Bean
    public JpaFieldNotInHandler jpaFieldNotInHandler() {
        return new JpaFieldNotInHandler();
    }
}
