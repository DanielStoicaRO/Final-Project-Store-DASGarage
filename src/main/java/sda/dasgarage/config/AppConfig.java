package sda.dasgarage.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Configuration
@ComponentScan(basePackages = "sda.dasgarage")
@EnableJpaRepositories(basePackages = "sda.dasgarage.repositories")
@EntityScan(basePackages = "sda.dasgarage.entities")
public class AppConfig {
}