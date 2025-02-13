package ru.lid.progertrainer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.lid.progertrainer.security.data.entity.User;
import ru.lid.progertrainer.security.data.repository.UserRepository;
import ru.lid.progertrainer.security.service.JwtService;

@SpringBootApplication
@Slf4j
public class ProgerTrainerApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(ProgerTrainerApplication.class, args);

        BCryptPasswordEncoder passwordEncoder = run.getBeanFactory().getBean(BCryptPasswordEncoder.class);
        JwtService jwtService = run.getBeanFactory().getBean(JwtService.class);
        UserRepository userRepository = run.getBeanFactory().getBean(UserRepository.class);

        User email_1 = userRepository.findByEmail("email_1").get();
        User email_2 = userRepository.findByEmail("email_2").get();
        log.info("password1 - {}, accessToken - {}, refreshToken - {}", passwordEncoder.encode("123"), jwtService.generateToken(email_1), jwtService.generateRefreshToken(email_1));
        log.info("password2 - {}, accessToken - {}, refreshToken - {}", passwordEncoder.encode("123"), jwtService.generateToken(email_2), jwtService.generateRefreshToken(email_2));
        log.info("start ProgerTrainer app");
    }
}
