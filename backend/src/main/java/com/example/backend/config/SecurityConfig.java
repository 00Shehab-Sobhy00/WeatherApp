package com.example.backend.config;


import com.example.backend.model.entity.ERole;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {


    private final JWTAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(config ->
                        config
                                // white List
                                .requestMatchers("register", "login","info/**")
                                .permitAll()
                                // anyRequest should be auth fist now
                                .requestMatchers("user/profile/**").hasAuthority(ERole.USER.name())
                                .requestMatchers(  "admin/profile/**").hasAuthority(ERole.ADMIN.name())
                                .requestMatchers("admins/**").hasAuthority(ERole.SUPER_ADMIN.name())
                                .requestMatchers("notes/**").hasAnyAuthority(ERole.SUPER_ADMIN.name(), ERole.ADMIN.name())
                                .requestMatchers(HttpMethod.GET,"/super/**").hasAuthority(ERole.SUPER_ADMIN.name())

                                .anyRequest()
                                .authenticated()
                )

                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // create a new session for each request
        http.authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//       .sessionManagement()
//       .sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // create a new session for each request
//       .and()
//       .authenticationProvider(authenticationProvider)
//       .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }


//    UnsatisfiedDependencyException  error
    //4. CORS with Spring Security
//    @Bean
//            http.cors();
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("*"));
//        configuration.setAllowedMethods(Arrays.asList("*"));
//        configuration.setAllowedHeaders(Arrays.asList("*"));
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return (CorsConfigurationSource) source; // Cast the source to CorsConfigurationSource
//    }
}



 // this is the old way of using filter chane works as well --
//  .csrf(AbstractHttpConfigurer::disable)
//          // white List
//          .authorizeHttpRequests()
//          .requestMatchers("weather/register", "weather/login").permitAll()
//          // anyRequest should be auth fist now
//          .requestMatchers("weather/admins").hasAuthority(ERole.SUPER_ADMIN.name())
//          .requestMatchers("weather/notes").hasAnyAuthority(ERole.SUPER_ADMIN.name(),ERole.ADMIN.name())
//          .anyRequest()
//          .authenticated()
//          .and()
//
//          .sessionManagement()
//          .sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // create a new session for each request
//          .and()
//          .authenticationProvider(authenticationProvider)
//          .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);