package com.bt_akademi.transaction_management.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
    web tabanlÄ± gÃ¼venlik saÄŸlamak iÃ§in
    @EnableWebSecurity "annotation"Ä±
    ve WebSecurityConfigurerAdapter sÄ±nÄ±fÄ±
    kullanÄ±lÄ±r.
 */

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    @Value("${service.security.secure-key-username}")
    private String secureKeyUsername;

    @Value("${service.security.secure-key-password}")
    private String secureKeyPassword;

    //uygulama açılması ile ilgili bizim belirlediğimiz
    // kullanıcı adı ve şifreyi devreye almak için
    // bu metot "override" edilir
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        //https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/in-memory.html
        auth.inMemoryAuthentication()
            .passwordEncoder(encoder)
            .withUser(secureKeyUsername)
            .password(encoder.encode(secureKeyPassword))
            .roles("USER");

    }

    // Session kullanÄ±lmayacak. JSON Web Token (JWT) kullanÄ±lacak.
    // https://www.prismacsi.com/cross-site-request-forgery-csrf-nedir


    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception
    {
        super.configure(httpSecurity);

        httpSecurity.csrf().disable();
    }

}
