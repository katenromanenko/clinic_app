package by.katenromanenko.clinicapp.config;

import by.katenromanenko.clinicapp.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth
                        // 1) публичные
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html",
                                "/auth/sign-in",
                                "/auth/refresh"
                        ).permitAll()

                        // 2) ACTUATOR только ADMIN
                        .requestMatchers("/actuator/**").hasRole("ADMIN")

                        // 3) USERS только ADMIN
                        // (чтобы пациент не создавал/удалял пользователей)
                        .requestMatchers("/api/users/**").hasRole("ADMIN")

                        // 4) SPECIALIZATIONS
                        // чтение всем авторизованным, изменения только ADMIN
                        .requestMatchers(HttpMethod.GET, "/api/specializations/**").authenticated()
                        // изменения только ADMIN
                        .requestMatchers(HttpMethod.POST, "/api/specializations/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/specializations/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/specializations/**").hasRole("ADMIN")

                        // 5) TIMESLOTS
                        // врач и админ могут управлять слотами
                        // пациент - только смотреть
                        .requestMatchers(HttpMethod.GET, "/api/timeslots/**").authenticated()
                        // менять/создавать/удалять может доктор или админ
                        .requestMatchers(HttpMethod.POST, "/api/timeslots/**").hasAnyRole("DOCTOR","ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/timeslots/**").hasAnyRole("DOCTOR","ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/timeslots/**").hasAnyRole("DOCTOR","ADMIN")

                        // 6) APPOINTMENTS
                        // пациент и админ могут создавать/менять записи
                        .requestMatchers(HttpMethod.GET, "/api/appointments/**").authenticated()
                        // создавать/менять — PATIENT или ADMIN
                        .requestMatchers(HttpMethod.POST, "/api/appointments/**").hasAnyRole("PATIENT","ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/appointments/**").hasAnyRole("PATIENT","ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/appointments/**").hasAnyRole("PATIENT","ADMIN")

                        // всё остальное — просто авторизация
                        .anyRequest().authenticated()
                )

                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

                .formLogin(form -> form.disable());

        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
