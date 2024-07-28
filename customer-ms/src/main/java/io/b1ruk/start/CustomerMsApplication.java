package io.b1ruk.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.sidecar.EnableSidecar;


@EnableSidecar
@SpringBootApplication
public class CustomerMsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CustomerMsApplication.class, args);
    }
}
