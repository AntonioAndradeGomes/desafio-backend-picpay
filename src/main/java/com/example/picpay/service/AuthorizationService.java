package com.example.picpay.service;
import com.example.picpay.client.AuthorizationClient;
import com.example.picpay.controller.dto.TransferDto;
import com.example.picpay.entity.Transfer;
import com.example.picpay.exception.PicPayException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {
    private final AuthorizationClient  authorizationClient;

    public AuthorizationService(AuthorizationClient authorizationClient) {
        this.authorizationClient = authorizationClient;
    }

    public  boolean isAuthorized(TransferDto transfer) {
        var resp = authorizationClient.isAuthorized();

        if(resp.getStatusCode().isError()){
            throw new PicPayException();
        }

        return resp.getBody() != null && resp.getBody().authorized();
    }
}
