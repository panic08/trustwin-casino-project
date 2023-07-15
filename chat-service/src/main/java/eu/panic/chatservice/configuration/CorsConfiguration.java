package eu.panic.chatservice.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfiguration implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/chat/sendMessage")
                .allowedOrigins("http://localhost:80")
                .allowedMethods("POST")
                .allowedHeaders("*");
        registry.addMapping("/api/chat/getAll")
                .allowedOrigins("http://localhost:80")
                .allowedMethods("GET")
                .allowedHeaders("*");
    }
}
