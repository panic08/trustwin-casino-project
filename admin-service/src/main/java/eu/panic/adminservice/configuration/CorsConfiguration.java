package eu.panic.adminservice.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class CorsConfiguration implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/admin/auth/block")
                .allowedOrigins("http://localhost:80")
                .allowedMethods("POST")
                .allowedHeaders("*");

        registry.addMapping("/api/admin/auth/unblock")
                .allowedOrigins("http://localhost:80")
                .allowedMethods("POST")
                .allowedHeaders("*");

        registry.addMapping("/api/admin/withdrawal/getAllByWithdrawalStatus")
                .allowedOrigins("http://localhost:80")
                .allowedMethods("GET")
                .allowedHeaders("*");

        registry.addMapping("/api/admin/withdrawal/updateWithdrawalStatus")
                .allowedOrigins("http://localhost:80")
                .allowedMethods("PUT")
                .allowedHeaders("*");
    }
}
