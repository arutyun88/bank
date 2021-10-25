package com.neoflex.grpcclient.service;

import com.neoflex.grpcclient.model.AccountType;
import com.neoflex.grpcclient.model.Address;
import com.neoflex.grpcclient.model.BankAccount;
import com.neoflex.grpcclient.model.BankAccountInfo;
import com.neoflex.grpcserver.BankAccountInfoServiceGrpc;
import com.neoflex.grpcserver.GrpcClient;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class GrpcClientService {
    private final String hostName;
    private final int port;

    public GrpcClientService(@Value(value = "${grpc.hostname}") String hostName,
                             @Value (value = "${grpc.port}") int port) {
        this.hostName = hostName;
        this.port = port;
    }

    public GrpcClient.ResponseBankAccountInfo getResponse(String accountType){
        ManagedChannel channel = ManagedChannelBuilder.forAddress(hostName,port).usePlaintext().build();
        BankAccountInfoServiceGrpc.BankAccountInfoServiceBlockingStub stub =
                BankAccountInfoServiceGrpc.newBlockingStub(channel);

        GrpcClient.RequestAccountType requestAccountType =
                GrpcClient.RequestAccountType.newBuilder().setAccountType(accountType).build();
        return stub.getBankAccountInfo(requestAccountType);
    }

    public List<BankAccountInfo> getBankAccountInfo(String accountType) {
        GrpcClient.ResponseBankAccountInfo responseBankAccountInfo = getResponse(accountType);
        return new ArrayList<>(bankAccountInfoParser(responseBankAccountInfo.getBankAccountInfoList()));
    }

    public static List<BankAccountInfo> bankAccountInfoParser(List<GrpcClient.BankAccountInfo> response){
        List<BankAccountInfo> parseAccInfo = new ArrayList<>();

        response.forEach(responseAccInfo -> {

            BankAccount account = new BankAccount();
            account.setUuid(UUID.fromString(responseAccInfo.getAccount().getUuid()));
            account.setFirstName(responseAccInfo.getAccount().getFirstname());
            account.setLastName(responseAccInfo.getAccount().getLastname());
            account.setPatronymic(responseAccInfo.getAccount().getPatronymic());
            account.setAccountNumber(responseAccInfo.getAccount().getAccountNumber());
            account.setAccountType(AccountType.valueOf(responseAccInfo.getAccount().getAccountType().toString()));

            Address address = new Address();
            address.setStreet(responseAccInfo.getAddress().getStreet());
            address.setCity(responseAccInfo.getAddress().getCity());
            address.setState(responseAccInfo.getAddress().getState());

            parseAccInfo.add(new BankAccountInfo(account,address));
        });
        return parseAccInfo;
    }
}