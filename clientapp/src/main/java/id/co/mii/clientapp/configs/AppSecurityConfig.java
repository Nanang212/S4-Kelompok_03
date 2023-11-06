package id.co.mii.clientapp.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .authorizeRequests()
      .antMatchers("/css/**", "/images/**", "/js/**")
      .permitAll()
      .antMatchers( HttpMethod.GET,"/auth/**")
      .permitAll()
       .antMatchers( HttpMethod.POST,"/auth/**")
      .permitAll()
      .antMatchers("/rest/auth/**")
      .permitAll()
      .anyRequest()
      .authenticated()
      .and()
      .formLogin()
      .loginPage("/auth/login")
      .loginProcessingUrl("/auth/login")
      .successForwardUrl("/dashboard")
      .failureForwardUrl("/auth/login?error=true")
      .permitAll()
      .and()
      .logout()
      .logoutUrl("/logout")
      .permitAll();
  }
}
