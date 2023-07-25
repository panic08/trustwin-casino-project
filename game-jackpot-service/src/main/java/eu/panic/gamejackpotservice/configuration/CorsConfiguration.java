package eu.panic.gamejackpotservice.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfiguration implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/game/jackpot/play")
                .allowedOrigins("http://localhost:80")
                .allowedMethods("POST")
                .allowedHeaders("*");
        registry.addMapping("/api/game/crash/getLast")
                .allowedOrigins("http://localhost:80")
                .allowedMethods("GET")
                .allowedHeaders("*");
        registry.addMapping("/api/game/crash/getAllBets")
                .allowedOrigins("http://localhost:80")
                .allowedMethods("GET")
                .allowedHeaders("*");
    }
}
