package com.example.ischedule.config;

import com.example.ischedule.Service.CustomUserDetailsService;
import com.example.ischedule.handler.CustomAuthenticationFailureHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfiguration {
    private final CustomUserDetailsService userDetailsService;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    public WebSecurityConfig(CustomUserDetailsService userDetailsService, CustomAuthenticationFailureHandler customAuthenticationFailureHandler) {
        this.userDetailsService = userDetailsService;
        this.customAuthenticationFailureHandler = customAuthenticationFailureHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
     * By ignoring these requests, Spring Security will not apply any security measures to them,
     * allowing them to be accessed without authentication or authorization.
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/js/**", "/images/**");
    }

    //configure AuthenticationProvider, which provides the UserDetails of a authenticated User
    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        // Set the UserDetailsService implementation to retrieve user details
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    private AuthenticationFailureHandler authenticationFailureHandler() {
        return this.customAuthenticationFailureHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                //https://www.ionos.at/digitalguide/server/sicherheit/was-ist-cross-site-request-forgery/
                .csrf()
                .disable()
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/welcome", "/signup").permitAll()
                        .requestMatchers("/home").hasAnyRole("STUDENT", "ADMIN", "ASSISTANT")
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .failureUrl("/login?error")
                        .failureHandler(authenticationFailureHandler())
                        .defaultSuccessUrl("/home", true)
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?loggedOut")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );

        return http.build();
    }
}
