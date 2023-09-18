package pl.ms.projectoverview.app.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.ms.projectoverview.app.services.UserService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    private final UserService mUserService;

    private final TokenService mTokenService;

    private final PasswordEncoder mPasswordEncoder;

    public SecurityConfiguration(UserService mUserService, TokenService mTokenService, PasswordEncoder mPasswordEncoder) {
        this.mUserService = mUserService;
        this.mTokenService = mTokenService;
        this.mPasswordEncoder = mPasswordEncoder;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        if (http == null) return null;

        http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(Customizer.withDefaults())
            .addFilterBefore(new JwtFilter(mTokenService, mUserService), UsernamePasswordAuthenticationFilter.class)
            .userDetailsService(mUserService)
            .authorizeHttpRequests(
                    (customizer) -> customizer
                            .requestMatchers("/auth", "/api/v1/user/create", "/recoverPwd", "/error")
                            .permitAll()
                            .anyRequest().authenticated()
            );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    @Scope("singleton")
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(mPasswordEncoder);
        provider.setUserDetailsService(mUserService);
        return provider;
    }

    @Bean
    @Scope(value = "singleton")
    public RoleHierarchy getHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy(
                "ROLE_ADMIN > ROLE_USER\nROLE_USER > ROLE_GUEST"
        );

        return roleHierarchy;
    }
}
