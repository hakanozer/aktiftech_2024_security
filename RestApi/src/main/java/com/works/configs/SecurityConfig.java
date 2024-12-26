package com.works.configs;

import com.works.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    final CustomerService customerService;
    final PasswordEncoder passwordEncoder;
    final FilterConfig filterConfig;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return
        http
        .httpBasic( Customizer.withDefaults() )
        .authorizeHttpRequests( req ->
        req
                .requestMatchers("/product/**").hasRole("product")
                .requestMatchers("/note/**").hasRole("note")
                .requestMatchers("/customer/**").permitAll()
                .requestMatchers(
                        "/v3/api-docs/**",    // Allow access to OpenAPI docs
                        "/swagger-ui/**",     // Allow access to Swagger UI
                        "/swagger-ui.html" ,
                        "/api/v1/users"
                ).permitAll()
        )
        .addFilterAfter(filterConfig, UsernamePasswordAuthenticationFilter.class)
        .csrf( csrf -> csrf.disable() )
        .formLogin( formLogin -> formLogin.disable() )
        .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(customerService);
        return daoAuthenticationProvider;
    }

}

/*
ali01 -> product
veli01 -> note
zehra02 -> product, note
 */
