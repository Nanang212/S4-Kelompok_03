package id.co.mii.clientapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity

public class AppSecurityConfig extends WebSecurityConfigurerAdapter{
    @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .authorizeRequests()
      .antMatchers("/css/**", "/images/**", "/js/**", "/email/**")
      .permitAll()
      
      .antMatchers("/auth/**")
      .permitAll()
      .antMatchers("/api/auth/registration")
      .permitAll()
      .antMatchers("/register/**")
      .permitAll()
      .antMatchers(HttpMethod.POST,"/signup")
      .permitAll()
      .antMatchers("/login")
      .permitAll()
      .antMatchers(HttpMethod.POST,"/login")
      .permitAll()
      .anyRequest()
      .authenticated()
      .and()
      .formLogin()
      .loginPage("/login")
      .loginProcessingUrl("/login")
      .successForwardUrl("/dashboard")
      .failureForwardUrl("/login?error=true")
      .permitAll()
      .and()
      .logout()
      .logoutUrl("/logout")
      .permitAll();
  }
}

    

