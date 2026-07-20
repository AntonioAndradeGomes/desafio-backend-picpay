package com.example.picpay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class PicpayApplication {
	//https://youtu.be/dttXo48oXt4?list=PLxCh3SsamNs7y1Y-QaVdWx0MUh0wvo7TV
	public static void main(String[] args) {
		SpringApplication.run(PicpayApplication.class, args);
	}

}
