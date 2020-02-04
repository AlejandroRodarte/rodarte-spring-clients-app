package com.rodarte.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    // configure how our exposed endpoints should be protected and who can access it
    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/clients", "/api/clients/page/**", "/api/clients/image/**")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/api/clients/{id}")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/api/clients/upload")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/api/clients")
                .hasRole("ADMIN")
                .antMatchers("/api/clients/**")
                .hasRole("ADMIN")
                .anyRequest()
                .authenticated();

    }

}
