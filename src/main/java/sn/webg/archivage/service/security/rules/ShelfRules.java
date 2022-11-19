package sn.webg.archivage.service.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import sn.webg.archivage.service.models.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShelfRules {

    static final String SHELF_API_PREFIX = "/v1/shelfs";
    static final String SHELF_ID = "/{shelfId}";

    @Bean
    public SecurityRule createShelf() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(SHELF_API_PREFIX)
                .build()
                .condition()
                    .hasPermission(SecurityPermissions.ROLE_ADMIN)
                    .hasPermission(SecurityPermissions.ADD_SHELF)
                .end();
    }

    @Bean
    public SecurityRule readShelf() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(SHELF_API_PREFIX + SHELF_ID)
                .build()
                .condition()
                    .hasPermission(SecurityPermissions.ROLE_ADMIN)
                    .hasPermission(SecurityPermissions.READ_SHELF)
                .end();
    }

    @Bean
    public SecurityRule readShelfs() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(SHELF_API_PREFIX)
                .build()
                .condition()
                    .hasPermission(SecurityPermissions.ROLE_ADMIN)
                    .hasPermission(SecurityPermissions.ADD_SHELF)
                    .hasPermission(SecurityPermissions.READ_SHELF)
                    .hasPermission(SecurityPermissions.EDIT_SHELF)
                    .hasPermission(SecurityPermissions.DELETE_SHELF)
                .end();
    }

    @Bean
    public SecurityRule updateShelf() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(SHELF_API_PREFIX + SHELF_ID)
                .build()
                .condition()
                    .hasPermission(SecurityPermissions.ROLE_ADMIN)
                    .hasPermission(SecurityPermissions.EDIT_SHELF)
                .end();
    }

    @Bean
    public SecurityRule deleteShelf() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(SHELF_API_PREFIX + SHELF_ID)
                .build()
                .condition()
                    .hasPermission(SecurityPermissions.ROLE_ADMIN)
                    .hasPermission(SecurityPermissions.DELETE_SHELF)
                .end();
    }

}
