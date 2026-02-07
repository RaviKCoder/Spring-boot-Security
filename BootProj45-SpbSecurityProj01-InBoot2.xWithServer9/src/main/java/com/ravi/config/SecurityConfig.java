package com.ravi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		
//		auth.inMemoryAuthentication().withUser("raja").password("{noop}rani").roles("CUSTOMER");
//		auth.inMemoryAuthentication().withUser("ramesh").password("{noop}hyd").roles("MANAGER");
//		auth.inMemoryAuthentication().withUser("rajesh").password("{noop}vizag").roles("VISITOR");
		
		auth.inMemoryAuthentication().withUser("raja").password("$2a$10$mGdEG0rgYPlvqYm.n./4JuQV4gEQLWc2IvylHyvtvy7fwNyYdY8nC").roles("CUSTOMER").disabled(true);
		auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).withUser("ramesh").password("$2a$10$GUuQc8hkB/znQX7cgTWeC.up3zjypgqXs6uNeDI0fQmmwyKVl2LqS").roles("MANAGER");
		auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).withUser("rajesh").password("$2a$10$UDVviM17McCzgd.OJB2dHeYukkpflBSbRoL2epKrBuvIBazMXEBwa").roles("VISITOR");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.authorizeHttpRequests()
		.antMatchers("/").permitAll()
		.antMatchers("/offers").authenticated()
		.antMatchers("/balance").hasAnyRole("CUSTOMER","MANAGER")
		.antMatchers("/approve").hasRole("MANAGER")
		.anyRequest().authenticated()
		.and()//.httpBasic()
		.formLogin()
		.and().logout()
		.and().exceptionHandling().accessDeniedPage("/denied")
		.and().sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(true);
	}

}
