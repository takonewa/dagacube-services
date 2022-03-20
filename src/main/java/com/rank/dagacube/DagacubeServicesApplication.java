package com.rank.dagacube;

import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import com.rank.dagacube.data.model.Player;
import com.rank.dagacube.data.model.Account;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import com.rank.dagacube.data.repo.AccountRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
@RequiredArgsConstructor
public class DagacubeServicesApplication implements CommandLineRunner {

    @Value("${info.app.name}")
    private String name;
    @Value("${info.app.version}")
    private String version;
    @Value("${info.app.description}")
    private String description;
    private final AccountRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(DagacubeServicesApplication.class, args);
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(name)
                        .description(description)
                        .version(version));
    }

    @Bean
    public AtomicInteger bonusHolder() {
        return new AtomicInteger();
    }

    @Override
    @Transactional
    public void run(String... args) {
        repository.save(new Account(1L, 0.0, new Player(1L, "user1", null), Set.of()));
        repository.save(new Account(2L, 0.0, new Player(2L, "user2", null), Set.of()));
        repository.flush();
    }
}
