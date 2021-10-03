package sda.dasgarage;

import org.springframework.boot.SpringApplication;
import sda.dasgarage.config.AppConfig;

/**
 * Welcome Online Store DAS Garage
 */
public class AppStart {
    public static void main(String[] args) {
        SpringApplication.run(AppConfig.class);
    }
}
