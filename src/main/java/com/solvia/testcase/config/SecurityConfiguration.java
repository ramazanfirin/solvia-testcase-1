package com.solvia.testcase.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

     @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
        		.authorizeHttpRequests(request -> request.requestMatchers
        		(new AntPathRequestMatcher("/books/**"))
        		.permitAll()
                ).csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(request -> request.requestMatchers
            	(new AntPathRequestMatcher("/authors/**"))
                .hasRole("ADMIN")
                .anyRequest()
                .authenticated())
            .httpBasic(Customizer.withDefaults())
            .build();
        
        
     
    }
     
     @Bean
     public UserDetailsService users() {
     	// The builder will ensure the passwords are encoded before saving in memory
     	UserBuilder users = User.withDefaultPasswordEncoder();
     	UserDetails user = users
     		.username("user")
     		.password("password")
     		.roles("USER")
     		.build();
     	UserDetails admin = users
     		.username("admin")
     		.password("password")
     		.roles("USER", "ADMIN")
     		.build();
     	return new InMemoryUserDetailsManager(user, admin);
     }
}