package com.example.teacher_system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;

@Configuration
public class SecurityConfiguration {
    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    //当然，由于记住我信息是存放在内存中的，我们需要保证服务器一直处于运行状态，
    //如果关闭服务器的话，记住我信息会全部丢失，因此，如果我们希望记住我能够一直持久化保存，
    //我们就需要进一步进行配置。我们需要创建一个基于JDBC的TokenRepository实现：
    //当我们登录之后，数据库中会自动记录相关的信息
    @Bean
    public PersistentTokenRepository tokenRepository(DataSource dataSource){
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
        //repository.setCreateTableOnStartup(true);  //在启动时自动在数据库中创建存储记住我信息的表，仅第一次需要，后续不需要
        repository.setDataSource(dataSource);
        return repository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   PersistentTokenRepository tokenRepository) throws Exception {
        return http
                .authorizeHttpRequests(auth->{
                    auth.requestMatchers("/static/**").permitAll();
                    auth.requestMatchers("/register","/doRegister","/getCode").permitAll();
                    auth.requestMatchers("/forgot","/reset","/getForgetCode","/doForget","doReset").permitAll();
                    auth.anyRequest().authenticated();
                })
                .formLogin(conf->{
                    conf.loginPage("/login");
                    conf.loginProcessingUrl("/doLogin");
                    conf.defaultSuccessUrl("/");
                    conf.permitAll();
                })
                .logout(conf->{
                    conf.logoutUrl("/doLogout");
                    conf.logoutSuccessUrl("/login");
                    conf.permitAll();
                })
                .rememberMe(conf->{
                    conf.alwaysRemember(false);
                    conf.rememberMeParameter("remember-me");
                    conf.rememberMeCookieName("huzhihang");  //记住我设置的Cookie名字，也可以自定义，不过没必要

                    conf.tokenRepository(tokenRepository);   //设置刚刚的记住我持久化存储库
                    conf.tokenValiditySeconds(3600 * 7);    //设置记住我有效时间为7天
                })
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }
}
