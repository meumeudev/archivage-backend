package sn.webg.archivage.service.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import sn.webg.archivage.service.models.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrganizationRules {

    static final String ORGANIZATION_API_PREFIX = "/v1/organizations";
    static final String ORGANIZATION_ID = "/{organisationId}";

    @Bean
    public SecurityRule createOrganisation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(ORGANIZATION_API_PREFIX)
                .build()
                .condition()
                    .hasPermission(SecurityPermissions.ADD_ORGANIZATION)
                .end();
    }

    @Bean
    public SecurityRule readOrganisation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ORGANIZATION_API_PREFIX + ORGANIZATION_ID)
                .build()
                .condition()
                    .hasPermission(SecurityPermissions.ROLE_ADMIN)
                    .hasPermission(SecurityPermissions.READ_ORGANIZATION)
                .end();
    }

    @Bean
    public SecurityRule readOrganisations() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ORGANIZATION_API_PREFIX)
                .build()
                .condition()
                    .hasPermission(SecurityPermissions.ROLE_ADMIN)
                    .hasPermission(SecurityPermissions.ADD_ORGANIZATION)
                    .hasPermission(SecurityPermissions.EDIT_ORGANIZATION)
                    .hasPermission(SecurityPermissions.DELETE_ORGANIZATION)
                    .hasPermission(SecurityPermissions.READ_ORGANIZATION)
                    .hasPermission(SecurityPermissions.ADD_USER)
                    .hasPermission(SecurityPermissions.EDIT_USER)
                    .hasPermission(SecurityPermissions.ADD_FOLDER)
                    .hasPermission(SecurityPermissions.EDIT_FOLDER)
                .end();
    }

    @Bean
    public SecurityRule updateOrganisation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(ORGANIZATION_API_PREFIX + ORGANIZATION_ID)
                .build()
                .condition()
                    .hasPermission(SecurityPermissions.ROLE_ADMIN)
                    .hasPermission(SecurityPermissions.EDIT_ORGANIZATION)
                .end();
    }

    @Bean
    public SecurityRule deleteOrganisation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(ORGANIZATION_API_PREFIX + ORGANIZATION_ID)
                .build()
                .condition()
                    .hasPermission(SecurityPermissions.ROLE_ADMIN)
                    .hasPermission(SecurityPermissions.DELETE_ORGANIZATION)
                .end();
    }

}
