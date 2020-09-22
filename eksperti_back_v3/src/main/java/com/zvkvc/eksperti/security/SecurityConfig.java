package com.zvkvc.eksperti.security;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Qualifier("userDetailsServiceImpl") // use custom UserDetailsService
    @Autowired
    private UserDetailsService userDetailsServiceImpl;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean(BeanIds.AUTHENTICATION_MANAGER) // Use Spring's built in AuthManager
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsServiceImpl)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/")

                .permitAll()
                .antMatchers("/api/auth/**")

                .permitAll()
                .antMatchers(HttpMethod.GET, "/api/subforums/")

                .permitAll()
                .antMatchers(HttpMethod.GET, "/api/subforums/**")

                .permitAll()
                .antMatchers(HttpMethod.GET, "/api/comments/")

                .permitAll()
                .antMatchers(HttpMethod.GET, "/api/comments/by-post/")

                .permitAll()
                .antMatchers(HttpMethod.GET, "/api/comments/by-post/**")

                .permitAll()
                .antMatchers(HttpMethod.GET, "/api/comments/**")

                .permitAll()
                .antMatchers(HttpMethod.GET, "/api/posts/")

                .permitAll()
                .antMatchers(HttpMethod.GET, "/api/posts/**")

                .permitAll()
                .antMatchers("/v2/api-docs", // swagger mappings, all permitted
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**")
                .permitAll()

                .anyRequest()
                .authenticated();
        httpSecurity.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues()); // solves Angular CORS problems
        // add -before- filters here
        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
