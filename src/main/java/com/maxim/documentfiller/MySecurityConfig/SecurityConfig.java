package com.maxim.documentfiller.MySecurityConfig;

import com.maxim.documentfiller.PostgresDB.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfiguration {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

//        http.authorizeHttpRequests((authorizeHttpRequests) ->
//                authorizeHttpRequests
//                        .requestMatchers("/attributes/**", "/templates-file/*")
//                        .hasAnyRole(UserDAO.Role.TEMPLATE_MANAGER.toString())
//                        .requestMatchers("/document-filling/*").permitAll()
//        );
        http.authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests.anyRequest().permitAll()
        ).csrf((customizer)->customizer.disable());

        return http.build();
    }



    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(new UserDetailsServiceImpl());
    }
}
