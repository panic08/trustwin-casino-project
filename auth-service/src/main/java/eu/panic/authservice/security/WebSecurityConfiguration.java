package eu.panic.authservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class WebSecurityConfiguration extends org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration {
    @Bean
    public SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(HttpMethod.POST, "/api/auth/signUp").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/signIn").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/getInfoByJwt").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/auth/changePersonalData").permitAll()
                        .anyRequest().authenticated()
                )
                .build();
    }
}
