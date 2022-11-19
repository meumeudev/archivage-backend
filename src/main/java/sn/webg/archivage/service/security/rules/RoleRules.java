package sn.webg.archivage.service.security.rules;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import sn.webg.archivage.service.models.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleRules {

    static final String ROLE_API_PREFIX = "/v1/roles";
    static final String ROLE_ID = "/{roleId}";

    @Bean
    public SecurityRule createRole() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(ROLE_API_PREFIX)
                .build()
                .condition()
                    .hasPermission(SecurityPermissions.ROLE_ADMIN)
                    .hasPermission(SecurityPermissions.ADD_ROLE)
                .end();
    }

    @Bean
    public SecurityRule readRole() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ROLE_API_PREFIX + ROLE_ID)
                .build()
                .condition()
                    .hasPermission(SecurityPermissions.ROLE_ADMIN)
                    .hasPermission(SecurityPermissions.READ_ROLE)
                .end();
    }

    @Bean
    public SecurityRule readRoles() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ROLE_API_PREFIX)
                .build()
                .condition()
                    .hasPermission(SecurityPermissions.ROLE_ADMIN)
                    .hasPermission(SecurityPermissions.ADD_ROLE)
                    .hasPermission(SecurityPermissions.READ_ROLE)
                    .hasPermission(SecurityPermissions.EDIT_ROLE)
                    .hasPermission(SecurityPermissions.DELETE_ROLE)
                    .hasPermission(SecurityPermissions.ADD_USER)
                    .hasPermission(SecurityPermissions.EDIT_USER)
                .end();
    }

    @Bean
    public SecurityRule updateRole() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(ROLE_API_PREFIX)
                .build()
                .condition()
                    .hasPermission(SecurityPermissions.ROLE_ADMIN)
                    .hasPermission(SecurityPermissions.EDIT_ROLE)
                .end();
    }

    @Bean
    public SecurityRule deleteRole() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(ROLE_API_PREFIX + ROLE_ID)
                .build()
                .condition()
                    .hasPermission(SecurityPermissions.ROLE_ADMIN)
                    .hasPermission(SecurityPermissions.DELETE_ROLE)
                .end();
    }

    @Bean
    public SecurityRule getPermissions() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern("/v1/permissions")
                .build()
                .condition()
                    .hasPermission(SecurityPermissions.ROLE_ADMIN)
                    .hasPermission(SecurityPermissions.ADD_ROLE)
                    .hasPermission(SecurityPermissions.READ_ROLE)
                    .hasPermission(SecurityPermissions.EDIT_ROLE)
                .end();
    }

    @Bean
    public SecurityRule getModules() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern("/v1/modules")
                .build()
                .condition()
                    .hasPermission(SecurityPermissions.ROLE_ADMIN)
                    .hasPermission(SecurityPermissions.ADD_ROLE)
                    .hasPermission(SecurityPermissions.READ_ROLE)
                    .hasPermission(SecurityPermissions.EDIT_ROLE)
                .end();
    }

}
