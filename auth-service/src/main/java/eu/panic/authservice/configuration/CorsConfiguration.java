package eu.panic.authservice.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class CorsConfiguration implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/auth/signIn")
                .allowedOrigins("http://localhost:80")
                .allowedMethods("POST")
                .allowedHeaders("*");

        registry.addMapping("/api/auth/signUp")
                .allowedOrigins("http://localhost:80")
                .allowedOrigins("POST")
                .allowedHeaders("*");

        registry.addMapping("/api/auth/changePersonalData")
                .allowedOrigins("http://localhost:80")
                .allowedOrigins("PUT")
                .allowedHeaders("*");

        registry.addMapping("/api/auth/getInfoByJwt")
                .allowedOrigins("*")
                .allowedMethods("POST")
                .allowedHeaders("*");
    }
}
