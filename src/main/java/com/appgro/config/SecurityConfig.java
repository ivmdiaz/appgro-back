package com.appgro.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${cors.enabled:false}")
    private boolean corsEnabled;

    public static final String JWT_KEY = "Appgro";


    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        applyCors(httpSecurity)
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedResponse())
                .and()
                .logout()
                .and()
                .addFilterAfter(new AuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers(HttpMethod.POST, "/").permitAll()
                .antMatchers("/api/public/**").permitAll()
                .antMatchers("/api/**").authenticated()
                .anyRequest().permitAll();
    }

    private HttpSecurity applyCors(HttpSecurity httpSecurity) throws Exception {
        if (corsEnabled) {
            return httpSecurity.cors().and();
        } else {
            return httpSecurity;
        }
    }

    private AuthenticationEntryPoint unauthorizedResponse() {
        return (req, rsp, e) -> {
            rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        };
    }


    @Bean
    public CustomPassEnconder passwordEncoder(){
        return new CustomPassEnconder();
    }

}
