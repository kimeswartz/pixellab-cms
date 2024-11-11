package com.kim.pixellab_cms.config.security;

// Class to:
// To override the HTTP basic authentication.
// Perform the authentication by finding the user in our database.
// Generate a JWT token when the authentication succeeds.

import com.kim.pixellab_cms.repository.user.UserRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class ApplicationSecurityConfiguration {
    private final UserRepository userRepository;

    // Constructor to inject the UserRepository dependency.
    // This allows us to use the repository for finding users during authentication.
    public ApplicationSecurityConfiguration(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Bean to provide a custom UserDetailsService implementation.
    // Searches for a user in the db by their email (username).
    @Bean
    UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    // Been for password encoder
    // This encoder to securely hash and verify user passwords
    @Bean
    BCryptPasswordEncoder passwordEncoder (){
        return new BCryptPasswordEncoder();
    }

    // Been to provide AuthenticationManager
    // This manager is responsible for processing authentication requests.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    // This provider is responsible for authenticating users with the custom UserDetailsService and password encoder.
    // Also overrides the password generated in the console. This means we have successfully overridden the authentication method.
    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }
}
