package com.tiffanytimbric.xchange;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@EnableWebSecurity
@EnableMethodSecurity(
        prePostEnabled = true, securedEnabled = true, jsr250Enabled = true
)
@Configuration
public class XchangeConfiguration {

    private PasswordEncoder passwordEncoder = new PasswordEncoder() {

        @Override
        @NonNull
        public String encode(
                @Nullable final CharSequence rawPassword
        ) {
            return String.valueOf(rawPassword);
        }

        @Override
        public boolean matches(
                @Nullable final CharSequence rawPassword,
                @Nullable final String encodedPassword
        ) {
            return StringUtils.equals(
                    rawPassword, encodedPassword
            );
        }

    };

    @Bean
    @NonNull
    public SecurityFilterChain securityFilterChain(
            @NonNull final HttpSecurity httpSecurity
    ) throws Exception {
        return httpSecurity.authorizeHttpRequests(authRegistry -> {
                    authRegistry.requestMatchers("/**").permitAll();
                    authRegistry.anyRequest().authenticated();
                })
//                .formLogin(Customizer.withDefaults())
                .getOrBuild();
    }

    @Bean
    @NonNull
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(List.of(
                User.builder()
                        .username("Aradia")
                        .password("password")
                        .roles(
                                "ADMIN", "USER"
                        )
                        .build(),
                User.builder()
                        .username("Tiffany")
                        .password("password")
                        .roles("USER")
                        .build()
        ));
    }

    @Bean
    @NonNull
    public PasswordEncoder passwordEncoder() {
        return passwordEncoder;
    }

}
