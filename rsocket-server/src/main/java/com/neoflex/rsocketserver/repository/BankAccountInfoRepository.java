package com.neoflex.rsocketserver.repository;

import com.neoflex.rsocketserver.model.BankAccountInfoDB;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.util.UUID;

@EnableCassandraRepositories
public interface BankAccountInfoRepository extends ReactiveCassandraRepository<BankAccountInfoDB, UUID> {
}
