package com.neoflex.request.controller;

import com.neoflex.request.model.BankAccountInfo;
import com.neoflex.request.repository.CassandraRequestRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping
public class CassandraRequestController {

    @Autowired
    @Setter
    private CassandraRequestRepository requestRepository;

    @GetMapping(value = "/{uuid_id}")
    public Optional<BankAccountInfo> findById(@PathVariable("uuid_id") UUID uuid) {
        return requestRepository.findById(uuid);
    }
}
