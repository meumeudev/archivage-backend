package sn.webg.archivage.service.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import sn.webg.archivage.service.models.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CardboardRules {

    static final String CARDBOARD_API_PREFIX = "/v1/cardboards";
    static final String CARDBOARD_ID = "/{cardboardId}";

    @Bean
    public SecurityRule createCardboard() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(CARDBOARD_API_PREFIX)
                .build()
                .condition()
                    .hasPermission(SecurityPermissions.ADD_CARDBOARD)
                    .hasPermission(SecurityPermissions.ROLE_ADMIN)
                .end();
    }

    @Bean
    public SecurityRule readCardboard() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(CARDBOARD_API_PREFIX + CARDBOARD_ID)
                .build()
                .condition()
                    .hasPermission(SecurityPermissions.READ_CARDBOARD)
                    .hasPermission(SecurityPermissions.ROLE_ADMIN)
                .end();
    }

    @Bean
    public SecurityRule readCardboards() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(CARDBOARD_API_PREFIX)
                .build()
                .condition()
                    .hasPermission(SecurityPermissions.ADD_CARDBOARD)
                    .hasPermission(SecurityPermissions.READ_CARDBOARD)
                    .hasPermission(SecurityPermissions.EDIT_CARDBOARD)
                    .hasPermission(SecurityPermissions.DELETE_CARDBOARD)
                    .hasPermission(SecurityPermissions.ROLE_ADMIN)
                .end();
    }

    @Bean
    public SecurityRule updateCardboard() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(CARDBOARD_API_PREFIX + CARDBOARD_ID)
                .build()
                .condition()
                    .hasPermission(SecurityPermissions.EDIT_CARDBOARD)
                    .hasPermission(SecurityPermissions.ROLE_ADMIN)
                .end();
    }

    @Bean
    public SecurityRule deleteCardboard() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(CARDBOARD_API_PREFIX + CARDBOARD_ID)
                .build()
                .condition()
                    .hasPermission(SecurityPermissions.DELETE_CARDBOARD)
                    .hasPermission(SecurityPermissions.ROLE_ADMIN)
                .end();
    }

}
