package com.vk.config;

import com.vk.security.AuthProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthProviderImpl authenticationProvider;

    @Autowired
    public SecurityConfig(AuthProviderImpl authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider);
    }

    @Bean
    public SecurityFilterChain configure(AuthenticationManagerBuilder authentication, HttpSecurity http) throws Exception {
        authentication.authenticationProvider(authenticationProvider);
        return http.csrf(csrf->csrf.disable())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/forAdmin").hasRole("ADMIN");
                    auth.requestMatchers("/forAuth").hasAnyRole("USER", "ADMIN");
                    auth.requestMatchers("/forAll").permitAll();
                })
                .formLogin().defaultSuccessUrl("/forAuth",true)
                .failureUrl("/login?error")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/forAll")
                .and()
                .headers(headers->headers.frameOptions().sameOrigin())
                .httpBasic(Customizer.withDefaults())
                .build();
    }

}
