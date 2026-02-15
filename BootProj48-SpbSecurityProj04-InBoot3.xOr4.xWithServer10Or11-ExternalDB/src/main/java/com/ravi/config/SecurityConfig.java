package com.ravi.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	
	@Autowired
	private DataSource ds;
	

    // Security rules for authentication and authorization
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .authorizeHttpRequests(auth -> auth
            		 .requestMatchers("/").permitAll()
                     .requestMatchers("/offers").authenticated()
                     .requestMatchers("/balance").hasAnyRole("CUSTOMER","MANAGER")
                     .requestMatchers("/approve").hasRole("MANAGER")
                .anyRequest().authenticated())
            .exceptionHandling(ex -> ex
            	    .accessDeniedPage("/denied")
            	)
            .formLogin(login -> login
                    .defaultSuccessUrl("/", true)   //  FORCE redirect to welcome page
                )
            .logout(Customizer.withDefaults())      // logout
            .httpBasic(Customizer.withDefaults());  // optional (for REST);
   
        return http.build();
    }
    
    // Password encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    
    @Bean
    public UserDetailsService userDetailsService() {

        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(ds);

        manager.setUsersByUsernameQuery(
            "SELECT username, password, enabled FROM users_authentication WHERE username = ?"
        );

        manager.setAuthoritiesByUsernameQuery(
            "SELECT fk_username, role FROM users_authorization WHERE fk_username = ?"
        );

        return manager;
    }


}
