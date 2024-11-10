package com.lego.yxl.validator.starter;

import com.lego.yxl.validator.core.ValidateService;
import com.lego.yxl.validator.core.VerifiableBasedValidator;
import com.lego.yxl.validator.core.VerifiableMethodValidationInterceptor;
import com.lego.yxl.validator.core.common.ValidateErrorsHandler;
import com.lego.yxl.validator.core.common.Verifiable;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.executable.ExecutableValidator;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@ConditionalOnClass({Verifiable.class, ExecutableValidator.class})
@AutoConfigureAfter(ValidationAutoConfiguration.class)
public class ValidatorAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public VerifiableBasedValidator validateableBasedValidator() {
        return new VerifiableBasedValidator();
    }

    @Bean
    public VerifiableMethodValidationInterceptor verifiableMethodValidationInterceptor(ValidateService validateService) {
        return new VerifiableMethodValidationInterceptor(validateService);
    }

    @Bean
    @ConditionalOnMissingBean
    public ValidateErrorsHandler validateErrorReporter() {
        return errors -> {
            Set<? extends ConstraintViolation<?>> collect = errors.getErrors().stream()
                    .map(error ->
                            ConstraintViolationImpl.forBeanValidation("", null, null,
                                    error.getMsg(), null, null, null,
                                    null, null, null, error.getCode()
                            )
                    )
                    .collect(Collectors.toSet());
            throw new ConstraintViolationException(collect);
        };
    }

    @Bean
    public PointcutAdvisor verifiableMethodValidationAdvisor(@Autowired VerifiableMethodValidationInterceptor interceptor) {
        DefaultPointcutAdvisor pointcutAdvisor = new DefaultPointcutAdvisor(
                new AnnotationMatchingPointcut(Validated.class, null, true), interceptor);
        pointcutAdvisor.setOrder(Ordered.LOWEST_PRECEDENCE);
        return pointcutAdvisor;
    }

    @Autowired(required = false)
    public void configMethodValidationOrder(List<MethodValidationPostProcessor> methodValidationPostProcessors) {
        methodValidationPostProcessors.forEach(methodValidationPostProcessor ->
                methodValidationPostProcessor.setBeforeExistingAdvisors(true));
    }

    @Bean
    @ConditionalOnMissingBean
    public ValidateService validateService() {
        return new ValidateService();
    }

}
