package com.lego.yxl.command.core.support.method;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import com.lego.yxl.*;
import com.lego.yxl.command.core.*;
import com.lego.yxl.command.core.support.handler.AggCommandHandlerFactories;
import com.lego.yxl.command.core.support.handler.CommandParser;
import com.lego.yxl.command.core.support.handler.aggloader.IDBasedAggLoader;
import com.lego.yxl.command.core.support.handler.aggloader.KeyBasedAggLoader;
import com.lego.yxl.command.core.support.handler.aggloader.SmartAggLoaders;
import com.lego.yxl.command.core.support.handler.aggsyncer.SmartAggSyncers;
import com.lego.yxl.command.core.support.handler.aggsyncer.SmartCommandRepositoryBasedAggSyncer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.function.Function;

@Getter(AccessLevel.PROTECTED)
@Setter(AccessLevel.PUBLIC)
abstract class BaseCommandServiceMethodInvokerFactory {
    private final Set<Class> registeredCommandTypes = Sets.newHashSet();

    private final Class<? extends AggRoot> aggClass;

    @Autowired
    private CommandParser commandParser;
    @Autowired
    private SmartAggSyncers smartAggSyncers;
    @Autowired
    private AggCommandHandlerFactories commandHandlerFactory;

    @Setter(AccessLevel.PUBLIC)
    private CommandRepository commandRepository;

    @Autowired
    private SmartAggLoaders smartAggLoaders;

    protected BaseCommandServiceMethodInvokerFactory(Class<? extends AggRoot> aggClass) {
        Preconditions.checkArgument(aggClass != null, "Agg Class Can not be null");
        this.aggClass = aggClass;
    }

    public void init(){
        commandParser.parseAgg(this.aggClass);
        this.smartAggSyncers.addAggSyncer(new SmartCommandRepositoryBasedAggSyncer(this.commandRepository, this.aggClass));
    }

    protected void parseContext(Class context){
        this.commandParser.parseContext(context);
    }

    protected void autoRegisterAggLoaders(Class commandType) {
        if (registeredCommandTypes.contains(commandType)){
            return;
        }
        registeredCommandTypes.add(commandType);

        if (CommandForSync.class.isAssignableFrom(commandType) && getCommandRepository() instanceof CommandWithKeyRepository){
            this.smartAggLoaders.addSmartAggLoader(KeyBasedAggLoader.apply(commandType, getAggClass(),
                    (CommandWithKeyRepository) getCommandRepository(), new CommandForSyncFetcher() ));
        }

        if (CommandForUpdateById.class.isAssignableFrom(commandType)){
            this.smartAggLoaders.addSmartAggLoader(IDBasedAggLoader.apply(commandType, getAggClass(),
                    getCommandRepository(), new CommandForUpdateByIdFetcher() ));
        }

        if (CommandForUpdateByKey.class.isAssignableFrom(commandType) && getCommandRepository() instanceof CommandWithKeyRepository){
            this.smartAggLoaders.addSmartAggLoader(KeyBasedAggLoader.apply(commandType, getAggClass(),
                    (CommandWithKeyRepository) getCommandRepository(), new CommandForUpdateByKeyFetcher()));
        }
    }

    protected Class getContextClass(Method method){
        CommandMethodDefinition commandMethodDefinition = AnnotatedElementUtils.findMergedAnnotation(method, CommandMethodDefinition.class);
        if (commandMethodDefinition != null){
            return commandMethodDefinition.contextClass();
        }
        return null;
    }


    @Value
    protected class BizMethodContext{
        private final Class contextClass;
        private final Method method;
    }

    private static class CommandForUpdateByIdFetcher implements Function{

        @Override
        public Object apply(Object cmd) {
            return ((CommandForUpdateById)cmd).getId();
        }

        @Override
        public String toString(){
            return "CommandForUpdateById.getId()";
        }
    }

    private static class CommandForUpdateByKeyFetcher implements Function{

        @Override
        public Object apply(Object cmd) {
            return ((CommandForUpdateByKey)cmd).getKey();
        }

        @Override
        public String toString(){
            return "CommandForUpdateByKey.getKey()";
        }
    }


    private static class CommandForSyncFetcher implements Function {

        @Override
        public Object apply(Object cmd) {
            return ((CommandForSync) cmd).getKey();
        }

        @Override
        public String toString(){
            return "CommandForSync.getKey()";
        }
    }
}
