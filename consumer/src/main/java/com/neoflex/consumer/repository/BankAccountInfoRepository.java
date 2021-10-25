package com.neoflex.consumer.repository;

import com.neoflex.consumer.model.BankAccountInfo;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.util.UUID;

@EnableCassandraRepositories
public interface BankAccountInfoRepository extends CassandraRepository<BankAccountInfo, UUID> {
}
