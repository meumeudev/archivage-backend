package sn.webg.archivage.service.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import sn.webg.archivage.service.models.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DocumentTypeRules {

    static final String DOCUMENT_TYPE_API_PREFIX = "/v1/documentTypes";
    static final String DOCUMENT_TYPE_ID = "/{documentTypeId}";

    @Bean
    public SecurityRule createDocumentType() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(DOCUMENT_TYPE_API_PREFIX)
                .build()
                .condition()
                    .hasPermission(SecurityPermissions.ADD_DOCUMENT_TYPE)
                    .hasPermission(SecurityPermissions.ROLE_ADMIN)
                .end();
    }

    @Bean
    public SecurityRule readDocumentType() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DOCUMENT_TYPE_API_PREFIX + DOCUMENT_TYPE_ID)
                .build()
                .condition()
                    .hasPermission(SecurityPermissions.READ_DOCUMENT_TYPE)
                    .hasPermission(SecurityPermissions.ROLE_ADMIN)
                .end();
    }

    @Bean
    public SecurityRule readDocumentTypes() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DOCUMENT_TYPE_API_PREFIX)
                .build()
                .condition()
                    .hasPermission(SecurityPermissions.ADD_DOCUMENT_TYPE)
                    .hasPermission(SecurityPermissions.READ_DOCUMENT_TYPE)
                    .hasPermission(SecurityPermissions.EDIT_DOCUMENT_TYPE)
                    .hasPermission(SecurityPermissions.DELETE_DOCUMENT_TYPE)
                    .hasPermission(SecurityPermissions.ADD_DOCUMENT)
                    .hasPermission(SecurityPermissions.READ_DOCUMENT)
                    .hasPermission(SecurityPermissions.EDIT_DOCUMENT)
                    .hasPermission(SecurityPermissions.ROLE_ADMIN)
                .end();
    }

    @Bean
    public SecurityRule updateDocumentType() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(DOCUMENT_TYPE_API_PREFIX + DOCUMENT_TYPE_ID)
                .build()
                .condition()
                    .hasPermission(SecurityPermissions.EDIT_DOCUMENT_TYPE)
                    .hasPermission(SecurityPermissions.ROLE_ADMIN)
                .end();
    }

    @Bean
    public SecurityRule deleteDocumentType() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(DOCUMENT_TYPE_API_PREFIX + DOCUMENT_TYPE_ID)
                .build()
                .condition()
                    .hasPermission(SecurityPermissions.DELETE_DOCUMENT_TYPE)
                    .hasPermission(SecurityPermissions.ROLE_ADMIN)
                .end();
    }

}
