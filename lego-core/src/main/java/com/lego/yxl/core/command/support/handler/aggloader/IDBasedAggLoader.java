package com.lego.yxl.core.command.support.handler.aggloader;

import com.lego.yxl.core.command.CommandRepository;

import java.util.Optional;
import java.util.function.Function;

public class IDBasedAggLoader
        extends AbstractSmartLoader {
    private final CommandRepository commandRepository;
    private final Function idFetcher;

    private IDBasedAggLoader(Class cmdClass, Class aggClass,
                             CommandRepository commandRepository,
                             Function idFetcher) {
        super(cmdClass, aggClass);
        this.commandRepository = commandRepository;
        this.idFetcher = idFetcher;
    }

    @Override
    public Optional load(Object o) {
        Object id = this.idFetcher.apply(o);
        return commandRepository.findById(id);
    }

    public static IDBasedAggLoader apply(Class cmdClass, Class aggClass, CommandRepository repository, Function idFetcher){
        return new IDBasedAggLoader(cmdClass, aggClass, repository, idFetcher);
    }
}
