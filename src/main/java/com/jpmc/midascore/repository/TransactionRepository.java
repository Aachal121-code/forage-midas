package com.jpmc.midascore.repository;

import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<com.jpmc.midascore.entity.TransactionRecord, Long> {
}
