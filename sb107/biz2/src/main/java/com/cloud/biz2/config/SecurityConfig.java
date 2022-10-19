package com.cloud.biz2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authorization.HttpStatusServerAccessDeniedHandler;

@Configuration
@EnableWebFluxSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http){

        http.authorizeExchange(exchange ->
                exchange.pathMatchers("/api/profile").hasAnyAuthority("SCOPE_profile, SCOPE_email")
                        .pathMatchers("/api/client_a").hasAnyAuthority("SCOPE_CLIENT_A")
                        .pathMatchers("/api/client_b").hasAnyAuthority("SCOPE_CLIENT_B")
        )
                .oauth2ResourceServer(
                        oauth2 -> oauth2
                                .jwt(Customizer.withDefaults())
                                .and()
                                .exceptionHandling()
                                .accessDeniedHandler(new HttpStatusServerAccessDeniedHandler(HttpStatus.OK))
                                .and()
                                .csrf()
                                .disable());
        return http.build();
    }
}