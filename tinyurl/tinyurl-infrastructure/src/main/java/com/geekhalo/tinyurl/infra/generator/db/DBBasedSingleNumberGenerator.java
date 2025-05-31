package com.geekhalo.tinyurl.infra.generator.db;

import com.geekhalo.tinyurl.domain.generator.NumberGenerator;
import com.geekhalo.tinyurl.infra.generator.db.gen.NumberGen;
import com.geekhalo.tinyurl.infra.generator.db.gen.NumberGenRepository;
import com.geekhalo.tinyurl.infra.generator.db.gen.NumberType;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

public class DBBasedSingleNumberGenerator
        implements NumberGenerator {

    @Autowired
    @Getter(AccessLevel.PRIVATE)
    private NumberGenRepository numberGenRepository;

    @Override
    public Long nextNumber() {
        do {
            try {
                // 尝试获取nextNumber
                Long number = doNextNumber(NumberType.TINY_URL);
                // 保存成功，说明未发生冲突
                if (number != null){
                    return number;
                }
            }catch (ObjectOptimisticLockingFailureException e){
                // 更新失败，进行重试
//                LOGGER.error("opt lock failure to generate number, retry ...");
            }
        }while (true);
    }

    private Long doNextNumber(NumberType type){
        NumberGen numberGen = this.getNumberGenRepository().getByType(type);
        if (numberGen == null){
            numberGen = new NumberGen(type);
        }
        Long id = numberGen.nextNumber();
        this.getNumberGenRepository().save(numberGen);
        return id;
    }
}
