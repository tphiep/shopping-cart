package com.example;

import com.example.config.CustomerConfiguration;
//import io.eventuate.jdbckafka.TramJdbcKafkaConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
        org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration.class}
)
@Import({CustomerConfiguration.class})

public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
