package com.neoflex.grpcclient.controller;

import com.neoflex.grpcclient.model.BankAccountInfo;
import com.neoflex.grpcclient.service.GrpcClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class BankAccountInfoController {

    private final GrpcClientService GRPC_CLIENT_SERVICE;

    @Autowired
    public BankAccountInfoController(GrpcClientService grpcClientService) {
        this.GRPC_CLIENT_SERVICE = grpcClientService;
    }

    @GetMapping(path = "{accountType}")
    public List<BankAccountInfo> getBankAccountInfoList(@PathVariable("accountType") String accountType) {
        return GRPC_CLIENT_SERVICE.getBankAccountInfo(accountType);
    }
}