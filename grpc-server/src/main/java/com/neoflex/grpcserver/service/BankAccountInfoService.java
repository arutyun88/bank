package com.neoflex.grpcserver.service;

import com.neoflex.grpcserver.BankAccountInfoOuterClass;
import com.neoflex.grpcserver.BankAccountInfoServiceGrpc;
import com.neoflex.grpcserver.model.AccountType;
import com.neoflex.grpcserver.model.BankAccountInfoDB;
import com.neoflex.grpcserver.repository.BankAccountInfoRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@GrpcService
public class BankAccountInfoService extends BankAccountInfoServiceGrpc.BankAccountInfoServiceImplBase {

    private final BankAccountInfoRepository BANK_ACCOUNT_INFO_REPOSITORY;

    @Autowired
    public BankAccountInfoService(BankAccountInfoRepository bankAccountInfoRepository) {
        this.BANK_ACCOUNT_INFO_REPOSITORY = bankAccountInfoRepository;
    }

    @Override
    public void getBankAccountInfo(BankAccountInfoOuterClass.RequestAccountType request,
                                   StreamObserver<BankAccountInfoOuterClass.ResponseBankAccountInfo> responseObserver) {

        BankAccountInfoOuterClass.ResponseBankAccountInfo.Builder builder =
                BankAccountInfoOuterClass.ResponseBankAccountInfo.newBuilder();

        List<BankAccountInfoDB> infoDBList = BANK_ACCOUNT_INFO_REPOSITORY.findAll();
        infoDBList.stream()
                .filter(bankAccountInfoDB -> bankAccountInfoDB
                        .getBankAccount()
                        .getAccountType()
                        .equals(AccountType.valueOf(request.getAccountType())))
                .forEach(bankAccountInfoDB -> builder.addBankAccountInfo(convert(bankAccountInfoDB)));

        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    public BankAccountInfoOuterClass.BankAccountInfo convert(BankAccountInfoDB accountInfoDB) {

        BankAccountInfoOuterClass.Account account = BankAccountInfoOuterClass.Account.newBuilder()
                .setUuid(accountInfoDB.getBankAccount().getUuid().toString())
                .setFirstname(accountInfoDB.getBankAccount().getFirstName())
                .setLastname(accountInfoDB.getBankAccount().getLastName())
                .setPatronymic(accountInfoDB.getBankAccount().getPatronymic())
                .setAccountNumber(accountInfoDB.getBankAccount().getAccountNumber())
                .setAccountType(BankAccountInfoOuterClass.Account.AccountType.valueOf(
                        accountInfoDB.getBankAccount().getAccountType().toString()
                ))
                .build();

        BankAccountInfoOuterClass.Address address = BankAccountInfoOuterClass.Address.newBuilder()
                .setStreet(accountInfoDB.getAddress().getStreet())
                .setCity(accountInfoDB.getAddress().getCity())
                .setState(accountInfoDB.getAddress().getState())
                .build();

        return BankAccountInfoOuterClass.BankAccountInfo.newBuilder()
                .setAccount(account)
                .setAddress(address)
                .build();
    }
}
