package sn.webg.archivage.service.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import sn.webg.archivage.service.models.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DocumentRules {

    static final String DOCUMENT_API_PREFIX = "/v1/documents";
    static final String DOCUMENT_ID = "/{documentId}";

    @Bean
    public SecurityRule createDocument() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(DOCUMENT_API_PREFIX)
                .build()
                .condition()
                    .hasPermission(SecurityPermissions.ADD_DOCUMENT)
                    .hasPermission(SecurityPermissions.ROLE_ADMIN)
                .end();
    }

    @Bean
    public SecurityRule readDocument() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DOCUMENT_API_PREFIX + DOCUMENT_ID)
                .build()
                .condition()
                    .hasPermission(SecurityPermissions.READ_DOCUMENT)
                    .hasPermission(SecurityPermissions.ROLE_ADMIN)
                .end();
    }

    @Bean
    public SecurityRule readDocuments() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DOCUMENT_API_PREFIX)
                .build()
                .condition()
                    .hasPermission(SecurityPermissions.ADD_DOCUMENT)
                    .hasPermission(SecurityPermissions.READ_DOCUMENT)
                    .hasPermission(SecurityPermissions.EDIT_DOCUMENT)
                    .hasPermission(SecurityPermissions.DELETE_DOCUMENT)
                    .hasPermission(SecurityPermissions.ROLE_ADMIN)
                .end();
    }

    @Bean
    public SecurityRule updateDocument() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(DOCUMENT_API_PREFIX + DOCUMENT_ID)
                .build()
                .condition()
                    .hasPermission(SecurityPermissions.EDIT_DOCUMENT)
                    .hasPermission(SecurityPermissions.ROLE_ADMIN)
                .end();
    }

    @Bean
    public SecurityRule addFile() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(DOCUMENT_API_PREFIX + "/{id}/_upload")
                .build()
                .condition()
                    .hasPermission(SecurityPermissions.EDIT_DOCUMENT)
                    .hasPermission(SecurityPermissions.ADD_DOCUMENT)
                    .hasPermission(SecurityPermissions.DELETE_DOCUMENT)
                    .hasPermission(SecurityPermissions.ROLE_ADMIN)
                .end();
    }

    @Bean
    public SecurityRule readFile() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DOCUMENT_API_PREFIX + "/{id}/_download")
                .build()
                .condition()
                    .hasPermission(SecurityPermissions.EDIT_DOCUMENT)
                    .hasPermission(SecurityPermissions.ADD_DOCUMENT)
                    .hasPermission(SecurityPermissions.READ_DOCUMENT)
                    .hasPermission(SecurityPermissions.DELETE_DOCUMENT)
                    .hasPermission(SecurityPermissions.ROLE_ADMIN)
                .end();
    }

    @Bean
    public SecurityRule deleteDocument() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(DOCUMENT_API_PREFIX + DOCUMENT_ID)
                .build()
                .condition()
                    .hasPermission(SecurityPermissions.DELETE_DOCUMENT)
                    .hasPermission(SecurityPermissions.ROLE_ADMIN)
                .end();
    }

}
