package com.neoflex.request.repository;

import com.neoflex.request.model.BankAccountInfo;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.util.UUID;

@EnableCassandraRepositories
public interface CassandraRequestRepository extends CassandraRepository<BankAccountInfo, UUID> {
}
