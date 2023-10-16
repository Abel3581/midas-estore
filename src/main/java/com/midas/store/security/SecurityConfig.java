package com.midas.store.security;

import com.midas.store.security.filter.JwtTokenFilter;
import com.midas.store.security.provider.UsernameAndPasswordAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UsernameAndPasswordAuthenticationProvider usernameAndPasswordAuthenticationProvider;
    private final JwtTokenFilter jwtTokenFilter;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Autowired
    void registerProvider(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(usernameAndPasswordAuthenticationProvider);
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.httpBasic(httpBasic -> httpBasic.disable());
        http.formLogin(formLogin -> formLogin.disable());
        http.csrf(csrf -> csrf.disable());

        http.authorizeRequests()
                .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
                .requestMatchers(HttpMethod.GET,"/users").hasAnyAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/products").hasAnyAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "/products").hasAnyAuthority("CUSTOMER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/products/{id}").hasAnyAuthority("ADMIN","CUSTOMER")
                .requestMatchers(HttpMethod.PUT, "/products/{id}").hasAnyAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE,"products/{id}").hasAnyAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/orders/{cartId}").permitAll()
                .requestMatchers(HttpMethod.GET,"/orders").hasAnyAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET,"/orders/{cartId}").hasAnyAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT,"/carts/{cartId}/{productId}").hasAnyAuthority("CUSTOMER")
                .requestMatchers(HttpMethod.GET,"/carts/{id}").hasAnyAuthority("CUSTOMER")

                .anyRequest().authenticated();

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling(httpSecurityExceptionHandlingConfigurer -> {
            httpSecurityExceptionHandlingConfigurer
                    .accessDeniedHandler(customAccessDeniedHandler);
        });

        return http.build();

    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000/*", "http://localhost:4200/*"));
        configuration.setAllowedMethods(Collections.singletonList("*"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
