package sda.dasgarage.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("sda.dasgarage")
@EntityScan("sda.dasgarage.entities")
@EnableJpaRepositories("sda.dasgarage.repositories")
public class AppConfig {
}
