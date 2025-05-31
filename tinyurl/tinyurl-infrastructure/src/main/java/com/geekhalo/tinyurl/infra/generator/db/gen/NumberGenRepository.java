package com.geekhalo.tinyurl.infra.generator.db.gen;


import org.springframework.data.jpa.repository.JpaRepository;

public interface NumberGenRepository extends JpaRepository<NumberGen, Long> {

    NumberGen getByType(NumberType type);
}
