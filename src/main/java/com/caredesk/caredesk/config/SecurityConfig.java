package com.caredesk.caredesk.config;

// ... other imports
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import com.caredesk.caredesk.service.UnifiedUserDetailsService;

import java.util.Collection;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Inject the new unified service
    @Autowired
    @Lazy
    private UnifiedUserDetailsService unifiedUserDetailsService;

    // Your PasswordEncoder bean should already be here (BCryptPasswordEncoder)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // SIMPLIFY the AuthenticationProvider
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(unifiedUserDetailsService); // Use the new service
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    // Update your SecurityFilterChain to redirect agents after login
    @Bean
    public AuthenticationSuccessHandler customSuccessHandler() {
        return (request, response, authentication) -> {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                response.sendRedirect("/admin/dashboard");
            } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_CUSTOMER"))) {
                response.sendRedirect("/customer/dashboard");
            } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_AGENT"))) {
                // ADD THIS REDIRECT FOR AGENTS
                response.sendRedirect("/agent/tickets");
            } else {
                response.sendRedirect("/");
            }
        };
    }

    // Your SecurityFilterChain bean should remain largely the same, but ensure it uses the customSuccessHandler
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // ... your existing requestMatchers
                        .requestMatchers("/agent/**").hasRole("AGENT") // Secure agent routes
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                        .successHandler(customSuccessHandler()) // Ensure this is used
                );
        return http.build();
    }
}