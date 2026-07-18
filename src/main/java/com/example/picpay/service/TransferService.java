package com.example.picpay.service;

import com.example.picpay.controller.dto.TransferDto;
import com.example.picpay.entity.Transfer;
import com.example.picpay.entity.Wallet;
import com.example.picpay.exception.InsufficientBalanceException;
import com.example.picpay.exception.TransferNotAllowedForWalletTypeException;
import com.example.picpay.exception.TransferNotAuthorizedException;
import com.example.picpay.exception.WalletNotFoundException;
import com.example.picpay.repository.TranferRepository;
import com.example.picpay.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class TransferService {
    private final TranferRepository tranferRepository;
    private final AuthorizationService authorizationService;
    private final NotificationService notificationService;
    private final WalletRepository walletRepository;

    public TransferService(
            TranferRepository tranferRepository, AuthorizationService authorizationService, NotificationService notificationService, WalletRepository walletRepository
    ) {
        this.tranferRepository = tranferRepository;
        this.authorizationService = authorizationService;
        this.notificationService = notificationService;
        this.walletRepository = walletRepository;
    }

    public Transfer transfer(TransferDto transferDto) {
        var sender = walletRepository.findById(transferDto.payer()).orElseThrow(() -> new WalletNotFoundException(transferDto.payer()));
        var receiver = walletRepository.findById(transferDto.payee()).orElseThrow(() -> new WalletNotFoundException(transferDto.payee()));
        validateTransfer(transferDto, sender);
        sender.debit(transferDto.value());
        receiver.credit(transferDto.value());
        var transfer = new Transfer(sender, receiver, transferDto.value());

        walletRepository.save(sender);
        walletRepository.save(receiver);
        var transferResult = tranferRepository.save(transfer);

        CompletableFuture.runAsync(() -> notificationService.sendNotification(transferResult));
        return  transferResult;
    }

    private void validateTransfer(TransferDto transferDto, Wallet sender){
        if(!sender.isTransferAllowedForWalletType()){
            throw new TransferNotAllowedForWalletTypeException();
        }
        if(!sender.isBalancerEqualOrGreaterThan(transferDto.value())){
            throw new InsufficientBalanceException();
        }

        if(!authorizationService.isAuthorized(transferDto)){
            throw new TransferNotAuthorizedException();
        }


    }

}
