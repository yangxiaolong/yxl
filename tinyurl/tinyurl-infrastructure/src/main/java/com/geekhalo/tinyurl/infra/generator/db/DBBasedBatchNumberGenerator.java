package com.geekhalo.tinyurl.infra.generator.db;


import com.geekhalo.tinyurl.domain.generator.NumberGenerator;
import com.geekhalo.tinyurl.infra.generator.AbstractBatchNumberGenerator;
import com.geekhalo.tinyurl.infra.generator.db.gen.NumberGen;
import com.geekhalo.tinyurl.infra.generator.db.gen.NumberGenRepository;
import com.geekhalo.tinyurl.infra.generator.db.gen.NumberType;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.util.List;

public class DBBasedBatchNumberGenerator
        extends AbstractBatchNumberGenerator
        implements NumberGenerator {
    @Autowired
    @Getter(AccessLevel.PRIVATE)
    private NumberGenRepository numberGenRepository;

    @Value("${tinyurl.number.generator.batchSize:500}")
    private int batchSize;



    @Override
    protected List<Long> batchLoad() {
        do {
            try {
                List<Long> numbers = nextNumber(batchSize);
                return numbers;
            }catch (ObjectOptimisticLockingFailureException e){
            }
        }while (true);
    }

    private List<Long> nextNumber(int size){
        NumberGen numberGen = this.getNumberGenRepository().getByType(NumberType.TINY_URL);
        if (numberGen == null){
            numberGen = new NumberGen(NumberType.TINY_URL);
        }

        List<Long> ids = numberGen.nextNumber(size);

        this.getNumberGenRepository().save(numberGen);
        return ids;
    }
}