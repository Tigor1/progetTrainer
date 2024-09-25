package ru.lid.progertrainer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class ProgerTrainerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProgerTrainerApplication.class, args);
        log.info("start ProgerTrainer app");
    }
}
