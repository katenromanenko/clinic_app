package by.katenromanenko.clinicapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "by.katenromanenko.clinicapp")
public class ClinicAppApplication {

    public static void main(String[] args) {

        SpringApplication.run(ClinicAppApplication.class, args);
    }

}
