package ru.smith.engine_lv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = {"ru.smith.engine_lv.api", "ru.smith.engine_lv.service"})
public class EngineLvApplication {

    public static void main(String[] args) {
        SpringApplication.run(EngineLvApplication.class, args);
    }

}
