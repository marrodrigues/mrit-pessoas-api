package br.com.mrit.pessoas.infrastructure.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${application.auth.username}")
    private String USERNAME;
    @Value("${application.auth.password}")
    private String PASSWORD;
    @Value("${application.auth.role}")
    private String ROLE;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            auth.inMemoryAuthentication()
                .withUser(USERNAME)
                .password(String.format("{noop}%s", PASSWORD))
                .authorities(ROLE);
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http = http.cors().and().csrf().disable();
        http.authorizeRequests()
                .antMatchers("/api/**").authenticated()
                .antMatchers(HttpMethod.POST,"/api/**").authenticated()
                .antMatchers(HttpMethod.PUT,"/api/**").authenticated()
                .antMatchers(HttpMethod.DELETE,"/api/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .httpBasic()
                .realmName("pessoas-api");
    }
}
