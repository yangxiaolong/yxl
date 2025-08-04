package com.lego.yxl.core.idempotent.core.support;

import com.lego.yxl.core.idempotent.annotation.Idempotent;
import com.lego.yxl.core.idempotent.annotation.IdempotentHandleType;
import com.lego.yxl.core.idempotent.core.IdempotentMeta;
import com.lego.yxl.core.idempotent.core.IdempotentMetaParser;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.Method;

public class IdempotentAnnParser implements IdempotentMetaParser {
    private final ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

    @Override
    public IdempotentMeta parse(Method method) {
        Idempotent idempotent = AnnotatedElementUtils.findMergedAnnotation(method, Idempotent.class);
        if (idempotent == null) {
            return null;
        }
        String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
        return new AnnBasedIdempotentMeta(idempotent, parameterNames, method.getReturnType());
    }

    static class AnnBasedIdempotentMeta implements IdempotentMeta {
        private final Idempotent idempotent;
        private final String[] paramNames;
        private final Class returnType;

        AnnBasedIdempotentMeta(Idempotent idempotent, String[] paramNames, Class returnType) {
            this.idempotent = idempotent;
            this.paramNames = paramNames;
            this.returnType = returnType;
        }

        @Override
        public String executorFactory() {
            return this.idempotent.executorFactory();
        }

        @Override
        public int group() {
            return this.idempotent.group();
        }

        @Override
        public String[] paramNames() {
            return this.paramNames;
        }

        @Override
        public String keyEl() {
            return this.idempotent.keyEl();
        }

        @Override
        public IdempotentHandleType handleType() {
            return this.idempotent.handleType();
        }

        @Override
        public Class returnType() {
            return this.returnType;
        }
    }
}
