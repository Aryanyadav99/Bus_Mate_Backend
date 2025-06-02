package net.busbackend.security;

import net.busbackend.services.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    @Autowired
    private JWTEntryPoint jwtEntryPoint;
    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Autowired
    private JwtAuthFilter jwtAuthFilter;
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints
                        .antMatchers(HttpMethod.GET).permitAll()  // Allow all GET requests
                        .antMatchers("/api/auth/**","/health").permitAll()  // Allow all auth endpoints
                        .antMatchers(HttpMethod.POST, "/api/reservation/add").authenticated()

                        // Authenticated endpoints
                        .antMatchers(HttpMethod.POST, "/api/bus/add", "/api/schedule/add", "/api/route/add")
                        .authenticated()

                        // All other requests
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exception ->
                        exception.authenticationEntryPoint(jwtEntryPoint)
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration configuration) throws  Exception{
         return configuration.getAuthenticationManager();
    }
}
