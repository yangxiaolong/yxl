package com.lego.yxl.idempotent.core.core.support;

import com.google.common.collect.Maps;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Map;

public class SimpleIdempotentKeyParser implements IdempotentKeyParser {
    private final ExpressionParser expressionParser = new SpelExpressionParser();
    private final Map<String, Expression> expressionCache = Maps.newConcurrentMap();

    @Override
    public String parse(String[] names, Object[] param, String el) {
        EvaluationContext evaluationContext = new StandardEvaluationContext();

        for (int i = 0; i < names.length; i++) {
            evaluationContext.setVariable(names[i], param[i]);
        }

        Expression expression = expressionCache.computeIfAbsent(el, this.expressionParser::parseExpression);
        Object value = expression.getValue(evaluationContext);
        return String.valueOf(value);
    }
}
