package com.ardisconsulting.graph.config

import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

// -------------------------------------------------------------------------------------------------

@CompileStatic
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true, prePostEnabled=true)
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	void configureGlobal(AuthenticationManagerBuilder auth) {
		auth.inMemoryAuthentication()
			.withUser('admin').password(passwordEncoder().encode('password')).roles('ADMIN').authorities('AUTHORITY_1')
			.and()
			.withUser('user').password(passwordEncoder().encode('password')).roles('USER').authorities('AUTHORITY_2')
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers('/ping', '/test/unprotected');
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.anyRequest().authenticated()

		http.httpBasic()
	}
}
