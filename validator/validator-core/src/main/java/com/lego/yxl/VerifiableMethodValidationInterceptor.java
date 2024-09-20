package com.lego.yxl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.util.List;

public class VerifiableMethodValidationInterceptor implements MethodInterceptor {
    private final ValidateService validateService;

    public VerifiableMethodValidationInterceptor(ValidateService validateService) {
        Preconditions.checkArgument(validateService != null);
        this.validateService = validateService;

    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object[] arguments = invocation.getArguments();
        if (arguments.length > 0) {
            List<Object> params = Lists.newArrayList();
            ErrorsCollector errorsCollector = new ErrorsCollector();
            for (Object argument : arguments) {
                params.add(argument);
            }
            this.validateService.validateParam(params);
        }
        return invocation.proceed();
    }


}
