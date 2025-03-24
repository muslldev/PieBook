package com.mpbtwozeroone.piebook.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET,"/categories/**").hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
                        .requestMatchers(HttpMethod.POST,"/categories/**").hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/categories/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/categories/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.GET,"/recipes/**").hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
                        .requestMatchers(HttpMethod.POST,"/recipes/**").hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/recipes/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/recipes/**").hasAuthority("ROLE_ADMIN")
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter))
                );

        return http.build();
    }
}
