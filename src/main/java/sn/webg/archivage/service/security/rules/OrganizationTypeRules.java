package sn.webg.archivage.service.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import sn.webg.archivage.service.models.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrganizationTypeRules {

    static final String ORGANIZATION_TYPE_API_PREFIX = "/v1/organizationTypes";
    static final String ORGANIZATION_TYPE_ID = "/{organisationTypeId}";

    @Bean
    public SecurityRule createOrganizationType() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(ORGANIZATION_TYPE_API_PREFIX)
                .build()
                .condition()
                    .hasPermission(SecurityPermissions.ROLE_ADMIN)
                    .hasPermission(SecurityPermissions.ADD_ORGANIZATION_TYPE)
                .end();
    }

    @Bean
    public SecurityRule readOrganizationType() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ORGANIZATION_TYPE_API_PREFIX + ORGANIZATION_TYPE_ID)
                .build()
                .condition()
                    .hasPermission(SecurityPermissions.ROLE_ADMIN)
                    .hasPermission(SecurityPermissions.READ_ORGANIZATION_TYPE)
                .end();
    }

    @Bean
    public SecurityRule readOrganizationTypes() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(ORGANIZATION_TYPE_API_PREFIX)
                .build()
                .condition()
                    .hasPermission(SecurityPermissions.ROLE_ADMIN)
                    .hasPermission(SecurityPermissions.ADD_ORGANIZATION_TYPE)
                    .hasPermission(SecurityPermissions.EDIT_ORGANIZATION_TYPE)
                    .hasPermission(SecurityPermissions.DELETE_ORGANIZATION_TYPE)
                    .hasPermission(SecurityPermissions.READ_ORGANIZATION_TYPE)
                    .hasPermission(SecurityPermissions.ADD_ORGANIZATION)
                    .hasPermission(SecurityPermissions.EDIT_ORGANIZATION)
                .end();
    }

    @Bean
    public SecurityRule updateOrganizationType() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(ORGANIZATION_TYPE_API_PREFIX + ORGANIZATION_TYPE_ID)
                .build()
                .condition()
                    .hasPermission(SecurityPermissions.ROLE_ADMIN)
                    .hasPermission(SecurityPermissions.EDIT_ORGANIZATION_TYPE)
                .end();
    }

    @Bean
    public SecurityRule deleteOrganizationType() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(ORGANIZATION_TYPE_API_PREFIX + ORGANIZATION_TYPE_ID)
                .build()
                .condition()
                    .hasPermission(SecurityPermissions.ROLE_ADMIN)
                    .hasPermission(SecurityPermissions.DELETE_ORGANIZATION_TYPE)
                .end();
    }

}
