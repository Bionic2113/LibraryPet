package com.caterpillar.shamil.SpringSecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * <h3>SpringSecurity</h3>
 *
 * @author Shamil Shyhiev
 * <a href="https://t.me/bionic2113">telegram<a>
 * @date 13.04.2023
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    final UserDetailsServiceImpl userDetails;
    final PassEncoder passEnc;
    @Autowired
    public SecurityConfig(UserDetailsServiceImpl userDetails, PassEncoder passEnc) {
        this.userDetails = userDetails;
        this.passEnc = passEnc;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable().httpBasic().disable()
                .authorizeHttpRequests((requests) -> requests
//                        .requestMatchers( "/swagger-ui/**","/home","/register", "/api/**","/api-swagger/**").permitAll()
                        .requestMatchers("/admin/**").hasAuthority("admin")
                        .requestMatchers("/user", "/reader/**").hasAnyAuthority("user","admin")
                        .anyRequest().permitAll()
                        .and()
                        .authenticationProvider(daoAuthenticationProvider())
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll().successHandler(myAuthenticationSuccessHandler())
                )
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll())
        ;
        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return new MySuccessHandler();
    }
    @Bean
    protected DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passEnc.passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetails);
        return daoAuthenticationProvider;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/resources/**", "/static/**","/webjars/**");
    }
}
