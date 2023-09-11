package com.client.invest.client.investment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ClientInvestmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientInvestmentApplication.class, args);
    }

}
