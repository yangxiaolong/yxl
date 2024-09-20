package com.lego.yxl.support;

import com.google.common.base.Preconditions;
import com.lego.yxl.CommandApplicationServiceDefinition;
import com.lego.yxl.CommandServiceDefinition;
import lombok.Builder;
import lombok.Value;
import org.springframework.core.annotation.AnnotatedElementUtils;

@Value
@Builder
public class CommandServiceMetadata {
    private Class domainClass;

    private Class repositoryClass;

    private Class serviceClass;

    public static CommandServiceMetadata fromCommandApplicationService(Class commandApplicationService){
        CommandApplicationServiceDefinition mergedAnnotation =
                AnnotatedElementUtils.findMergedAnnotation(commandApplicationService, CommandApplicationServiceDefinition.class);

        Preconditions.checkArgument(mergedAnnotation != null);

        return CommandServiceMetadata.builder()
                .domainClass(mergedAnnotation.domainClass())
                .repositoryClass(mergedAnnotation.repositoryClass())
                .serviceClass(commandApplicationService)
                .build();
    }


    public static CommandServiceMetadata fromCommandService(Class commandService){
        CommandServiceDefinition mergedAnnotation =
                AnnotatedElementUtils.findMergedAnnotation(commandService, CommandServiceDefinition.class);

        Preconditions.checkArgument(mergedAnnotation != null);

        return CommandServiceMetadata.builder()
                .domainClass(mergedAnnotation.domainClass())
                .repositoryClass(mergedAnnotation.repositoryClass())
                .serviceClass(commandService)
                .build();
    }
}
