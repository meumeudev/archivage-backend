package sn.webg.archivage.service.configurations;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import sn.webg.archivage.service.security.WebSecurityManager;
import sn.webg.archivage.service.security.jwt.JWTConfigurer;
import sn.webg.archivage.service.security.jwt.TokenProvider;

@EnableWebSecurity
@RequiredArgsConstructor
@Profile(value = "!test")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    final TokenProvider tokenProvider;

    final WebSecurityManager webSecurityManager;

    final CorsFilter corsFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .httpBasic().disable()
                .csrf().disable()
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/api/authenticate").permitAll()
                .antMatchers("/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/swagger-ui/**",
                        "/v3/api-docs",
                        "/v3/api-docs/**",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/mobility-engine/swagger-ui.html",
                        "/manage/**",
                        "/api/*/v3/api-docs",
                        "/api/v3/api-docs/**",
                        "/api/*/v3/api-docs/**",
                        "/webjars/springfox-swagger-ui/**",
                        "/api/*/v2/api-docs", "/v1/auth/**").permitAll()
                .and()
                .httpBasic()
                .and()
                .apply(securityConfigurerAdapter());

        // Managing apis security access rules
        webSecurityManager.manageSecurityRules(http);

        //With this rules, all the endpoints with no explicit authorization are denied => white list mode
        http.authorizeRequests().anyRequest().denyAll();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private JWTConfigurer securityConfigurerAdapter() {
        return new JWTConfigurer(tokenProvider);
    }
}
