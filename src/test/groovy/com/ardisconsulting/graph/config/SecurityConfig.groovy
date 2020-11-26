package com.ardisconsulting.graph.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager

// -------------------------------------------------------------------------------------------------

@Configuration
class SecurityConfig {

//	@Bean
//	public UserDetailsService userDetailsService() {
//		def adminAuthority = new SimpleGrantedAuthority('ROLE_ADMIN')
//		def userAuthority = new SimpleGrantedAuthority('ROLE_USER')
//
//		def user = (UserDetails)new User('user', 'password', [ userAuthority ])
//		def badUser = (UserDetails)new User('baduser', 'password', [ userAuthority ])
//
//		return new InMemoryUserDetailsManager([ user, badUser ])
//	}
}
