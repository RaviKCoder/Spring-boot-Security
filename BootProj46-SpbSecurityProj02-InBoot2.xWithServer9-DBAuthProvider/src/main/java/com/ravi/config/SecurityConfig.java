package com.ravi.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private DataSource ds;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
	//enable jdbc authentication provider
		auth.jdbcAuthentication().dataSource(ds).passwordEncoder(new BCryptPasswordEncoder())
		.usersByUsernameQuery("SELECT UNAME,PASSWORD,STATUS FROM USER_AUTHENTICATION WHERE UNAME=?")//For authentication
		.authoritiesByUsernameQuery("SELECT FK_UNAME,ROLE FROM USER_AUTHORIZATION WHERE FK_UNAME=?");//For authorization

	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.authorizeHttpRequests()
		.antMatchers("/").permitAll()
		.antMatchers("/offers").authenticated()
		.antMatchers("/balance").hasAnyAuthority("CUSTOMER","MANAGER")
		.antMatchers("/approve").hasAuthority("MANAGER")
		.anyRequest().authenticated()
		.and()//.httpBasic()
		.formLogin()
		.and().logout()
		.and().exceptionHandling().accessDeniedPage("/denied")
		.and().sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(true);
	}

}
