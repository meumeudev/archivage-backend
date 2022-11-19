package sn.webg.archivage.service.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import sn.webg.archivage.service.models.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRules {

    static final String USER_API_PREFIX = "/v1/agents";
    static final String USER_ID = "/{userId}";
    static final String USER_USERNAME = "/username/{userName}";
    static final String USER_PASSWORD = "/password/{userName}";

    @Bean
    public SecurityRule createUser() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(USER_API_PREFIX)
                .build()
                .condition()
                    .hasPermission(SecurityPermissions.ROLE_ADMIN)
                    .hasPermission(SecurityPermissions.ADD_USER)
                .end();
    }

    @Bean
    public SecurityRule readUser() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(USER_API_PREFIX + USER_ID)
                .build()
                .condition()
                    .hasPermission(SecurityPermissions.ROLE_ADMIN)
                    .hasPermission(SecurityPermissions.READ_USER)
                .end();
    }
    @Bean
    public SecurityRule updatePassword() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(USER_API_PREFIX + USER_PASSWORD)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.ROLE_ADMIN)
                .hasPermission(SecurityPermissions.READ_USER)
                .end();
    }

    @Bean
    public SecurityRule readUserByUsername() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(USER_API_PREFIX + USER_USERNAME)
                .authenticated(true)
                .build();
    }

    @Bean
    public SecurityRule readUsers() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(USER_API_PREFIX)
                .build()
                .condition()
                    .hasPermission(SecurityPermissions.ROLE_ADMIN)
                    .hasPermission(SecurityPermissions.ADD_USER)
                    .hasPermission(SecurityPermissions.EDIT_USER)
                    .hasPermission(SecurityPermissions.DELETE_USER)
                    .hasPermission(SecurityPermissions.READ_USER)
                .end();
    }

    @Bean
    public SecurityRule updateUser() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(USER_API_PREFIX + USER_ID)
                .build()
                .condition()
                    .hasPermission(SecurityPermissions.ROLE_ADMIN)
                    .hasPermission(SecurityPermissions.EDIT_USER)
                .end();
    }

    @Bean
    public SecurityRule deleteUser() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(USER_API_PREFIX + USER_ID)
                .build()
                .condition()
                    .hasPermission(SecurityPermissions.ROLE_ADMIN)
                    .hasPermission(SecurityPermissions.DELETE_USER)
                .end();
    }

}
