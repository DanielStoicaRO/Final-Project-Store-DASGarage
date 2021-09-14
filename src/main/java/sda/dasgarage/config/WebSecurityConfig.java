package sda.dasgarage.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/frontpage", "/login").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.httpBasic();
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/frontpage").deleteCookies("JSESSIONID").clearAuthentication(true).invalidateHttpSession(true);
    }

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth, DataSource dataSource) throws Exception {

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        auth.jdbcAuthentication().passwordEncoder(passwordEncoder).dataSource(dataSource);
        System.out.println(passwordEncoder.encode("user"));



//                .inMemoryAuthentication()
//                .withUser("user").password(passwordEncoder.encode("user")).roles("USER")
//                .and()
//                .withUser("admin").password(passwordEncoder.encode("admin")).roles("ADMIN")
//                .and().passwordEncoder(passwordEncoder);
    }


}
