package com.neoflex.grpcserver.repository;

import com.neoflex.grpcserver.model.BankAccountInfoDB;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.util.UUID;

@EnableCassandraRepositories
public interface BankAccountInfoRepository extends CassandraRepository <BankAccountInfoDB, UUID> {
}
