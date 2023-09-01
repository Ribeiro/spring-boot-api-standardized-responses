package br.tech.gtech.spring.standardizedresponses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.tech.gtech.spring.standardizedresponses.autoconfig.EnableResponse;


@EnableResponse
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
