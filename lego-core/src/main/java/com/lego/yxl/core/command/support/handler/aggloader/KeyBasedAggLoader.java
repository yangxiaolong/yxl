package com.lego.yxl.core.command.support.handler.aggloader;

import com.lego.yxl.core.command.CommandWithKeyRepository;

import java.util.Optional;
import java.util.function.Function;

public class KeyBasedAggLoader
        extends AbstractSmartLoader
        implements SmartAggLoader{
    private final CommandWithKeyRepository commandRepository;
    private final Function keyFetcher;

    private KeyBasedAggLoader(Class cmdClass, Class aggClass, CommandWithKeyRepository commandRepository, Function keyFetcher) {
        super(cmdClass, aggClass);
        this.commandRepository = commandRepository;
        this.keyFetcher = keyFetcher;
    }

    @Override
    public Optional load(Object o) {
        Object key = this.keyFetcher.apply(o);
        return commandRepository.findByKey(key);
    }


    public static KeyBasedAggLoader apply(Class cmdClass, Class aggClass, CommandWithKeyRepository repository, Function keyFetcher){
        return new KeyBasedAggLoader(cmdClass, aggClass, repository, keyFetcher);
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.aggClass).append("\t")
                .append(this.commandRepository.getClass())
                .append(".")
                .append("findByKey(")
                .append(this.keyFetcher)
                .append(")");
        return stringBuilder.toString();
    }
}
