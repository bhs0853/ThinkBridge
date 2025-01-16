package com.bhs.thinkbridge.config;

import com.bhs.thinkbridge.repositories.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * This Application Configuration specifies the spring boot about the particular authentication
 * type our application needs. <br>
 *
 * <pre>
 *     PasswordEncoder
 *                          -> Authentication Provider -> Authentication Manager
 *     UserDetailsService
 * </pre>
 *
 * <br>
 *
 *  This Authentication Manager takes in the Authentication object and tries to authenticate it.
 *
 * <br>
 *
 * Check out spring security doc to get a clear understanding of whole authentication and authorization architecture.
 *
 * @see <a href="https://docs.spring.io/spring-security/reference/index.html"> Spring Security Doc </a>
 *
 * @see <a href="https://medium.com/spring-framework/spring-security-authentication-process-explained-in-detailed-5bc0a424a746">
 *     Authentication Process Medium Article</a>
 */

@Configuration
public class ApplicationConfiguration {

    private final UserRepository userRepository;

    public ApplicationConfiguration(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserDetailsService userDetailsService(){
        return email -> userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }

    @Bean
    AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
