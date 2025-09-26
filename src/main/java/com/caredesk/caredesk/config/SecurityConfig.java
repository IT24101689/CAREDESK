    package com.caredesk.caredesk.config;

    import com.caredesk.caredesk.service.AdminDetailsService;  // Assuming this exists based on project structure
    import com.caredesk.caredesk.service.CustomerService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.context.annotation.Lazy;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.security.authentication.AuthenticationProvider;
    import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
    import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.core.GrantedAuthority;
    import org.springframework.security.crypto.password.NoOpPasswordEncoder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.security.web.SecurityFilterChain;
    import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;
    import java.io.IOException;
    import java.util.Collection;

    @Configuration
    @EnableWebSecurity
    public class SecurityConfig {

        @Autowired
        @Lazy
        private AdminDetailsService adminDetailsService;  // Existing UserDetailsService for admins

        @Autowired
        @Lazy
        private CustomerService customerService;  // New UserDetailsService for customers

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                    .csrf(csrf -> csrf.disable())  // Keep disabled for now; enable later if needed
                    .authorizeHttpRequests(auth -> auth
                            // Public paths (existing + static)
                            .requestMatchers("/faq", "/faq/**", "/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                            // Admin paths require ROLE_ADMIN
                            .requestMatchers("/admin/**").hasRole("ADMIN")
                            // Customer paths require ROLE_CUSTOMER
                            .requestMatchers("/customer/**").hasRole("CUSTOMER")
                            // Everything else permitted (adjust as needed)
                            .anyRequest().permitAll()
                    )
                    .formLogin(form -> form
                            .loginPage("/login")  // Shared login page (update from /admin/login)
                            .permitAll()
                            .successHandler(customSuccessHandler())  // Custom redirect based on role
                    )
                    .logout(logout -> logout
                            .logoutUrl("/logout")  // Shared logout
                            .logoutSuccessUrl("/login")
                            .invalidateHttpSession(true)
                            .deleteCookies("JSESSIONID")
                            .permitAll()
                    );

            return http.build();
        }

        @Bean
        public AuthenticationSuccessHandler customSuccessHandler() {
            return new AuthenticationSuccessHandler() {
                @Override
                public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                    Authentication authentication) throws IOException {
                    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
                    if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                        response.sendRedirect("/admin/dashboard");
                    } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_CUSTOMER"))) {
                        response.sendRedirect("/customer/dashboard");
                    } else {
                        response.sendRedirect("/");
                    }
                }
            };
        }

        @Bean
        public AuthenticationProvider authenticationProvider() {
            DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
            authProvider.setUserDetailsService(username -> {
                try {
                    // Try loading as admin first
                    return adminDetailsService.loadUserByUsername(username);
                } catch (Exception e) {
                    // If not admin, try customer
                    return customerService.loadUserByUsername(username);
                }
            });
            authProvider.setPasswordEncoder(passwordEncoder());
            return authProvider;
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return NoOpPasswordEncoder.getInstance();  // Plain-text for dev; NOT secure for prod!
        }
    }