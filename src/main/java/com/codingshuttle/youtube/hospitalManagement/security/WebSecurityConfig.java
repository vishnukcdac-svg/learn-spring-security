package com.codingshuttle.youtube.hospitalManagement.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    private  final PasswordEncoder passwordEncoder;

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        // this is used for stateful security with session and login form
//    httpSecurity.
//        authorizeHttpRequests(auth -> auth
//                .requestMatchers("/public/**","/auth/**").permitAll()
//                .requestMatchers("/admin/**").hasRole("ADMIN")
//                .requestMatchers("/doctors/**").hasAnyRole("DOCTOR", "ADMIN")
//
//        )
//                .formLogin(Customizer.withDefaults());
//    return httpSecurity.build();


    //
       // now we  doing stateless security with jwt and no session defined and no login form
        httpSecurity.
                csrf(csrfConfig -> csrfConfig.disable()) // csrf is disabled for stateless security
                        .sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))// session is disabled for stateless security
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/public/**","/auth/**","/api/v1/auth/**").permitAll()
//                        .requestMatchers("/admin/**").hasRole("ADMIN")
//                        .requestMatchers("/doctors/**").hasAnyRole("DOCTOR", "ADMIN")
                                .anyRequest().authenticated()

                )
               // .formLogin(Customizer.withDefaults())
        // now adding jwt filter
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        ;
        return httpSecurity.build();
    }
    // commenting for now using user entity
    /*@Bean
    public UserDetailsService userDetailsService() {
    UserDetails user1 = User.withUsername("admin")
            .password(passwordEncoder.encode("pass" ))
            .roles("ADMIN")
            .build();
    UserDetails user2 =User.withUsername("patient")
            .password(passwordEncoder.encode("pass"))
            .roles("PATIENT")
            .build();

    return new InMemoryUserDetailsManager(user1, user2);
    }*/
}
