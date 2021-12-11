package sda.dasgarage.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/");

        registry.addResourceHandler("/imagines/**")
                .addResourceLocations("classpath:/static/imagines/");

        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/");

//        registry.addResourceHandler("/**")
//                .addResourceLocations("classpath:/templates/");
    }
}
