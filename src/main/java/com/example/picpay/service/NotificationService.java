package com.example.picpay.service;

import com.example.picpay.client.NotificationClient;
import com.example.picpay.entity.Transfer;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class NotificationService {
    private final NotificationClient notificationClient;

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    public NotificationService(NotificationClient notificationClient) {
        this.notificationClient = notificationClient;
    }
    public  void  sendNotification(Transfer transfer){
        try {
            logger.info("Sending notification...");

            var resp = notificationClient.sendNotification(transfer);

            if (resp.getStatusCode().isError()) {
                logger.error("Error while sending notification, status code is not OK");
            }

        } catch (Exception e) {
            logger.error("Error while sending notification", e);
        }
    }
}
