package com.tiffanytimbric.xchange;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
        securedEnabled = true,
        prePostEnabled = true,
        jsr250Enabled = true
)
public class SecurityConfig {

    @Bean
    @Nullable
    public SecurityFilterChain filterChain(
            @Nullable final HttpSecurity httpSecurity
    ) throws Exception {
        if (httpSecurity == null) {
            return null;
        }

        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                        authorizationManagerRequestMatcherRegistry
                                .requestMatchers("/**").hasAnyRole("ADMIN")
                                .requestMatchers("/**").hasAnyRole("USER", "ADMIN")
                                .requestMatchers("/login/**").permitAll()
                                .requestMatchers("/logout/**").permitAll()
                                .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                );

        return httpSecurity.build();
    }

    @Bean
    @NonNull
    public UserDetailsService userDetailsService(
            @Nullable final BCryptPasswordEncoder bCryptPasswordEncoder
    ) {
        final InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User
                .withUsername("user")
                .password(
                        bCryptPasswordEncoder == null
                                ? "userPass"
                                : bCryptPasswordEncoder.encode("userPass")
                )
                .roles("USER")
                .build()
        );

        manager.createUser(User
                .withUsername("admin")
                .password(
                        bCryptPasswordEncoder == null
                                ? "adminPass"
                                : bCryptPasswordEncoder.encode("adminPass")
                )
                .roles("USER", "ADMIN")
                .build()
        );

        return manager;
    }

}
