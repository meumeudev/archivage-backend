package sn.webg.archivage.service.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import sn.webg.archivage.service.models.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MetaDataRules {

    static final String METADATA_API_PREFIX = "/v1/metaDatas";
    static final String METADATA_ID = "/{metaDataId}";

    @Bean
    public SecurityRule createMetaData() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(METADATA_API_PREFIX)
                .build()
                .condition()
                    .hasPermission(SecurityPermissions.ROLE_ADMIN)
                    .hasPermission(SecurityPermissions.ADD_METADATA)
                .end();
    }

    @Bean
    public SecurityRule readMetaData() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(METADATA_API_PREFIX + METADATA_ID)
                .build()
                .condition()
                    .hasPermission(SecurityPermissions.ROLE_ADMIN)
                    .hasPermission(SecurityPermissions.READ_METADATA)
                    .hasPermission(SecurityPermissions.READ_DOCUMENT)
                    .hasPermission(SecurityPermissions.ADD_DOCUMENT)
                    .hasPermission(SecurityPermissions.EDIT_DOCUMENT)
                    .hasPermission(SecurityPermissions.READ_FOLDER)
                    .hasPermission(SecurityPermissions.ADD_FOLDER)
                    .hasPermission(SecurityPermissions.EDIT_FOLDER)
                    .hasPermission(SecurityPermissions.ADD_FOLDER_TYPE)
                    .hasPermission(SecurityPermissions.EDIT_FOLDER_TYPE)
                    .hasPermission(SecurityPermissions.ADD_DOCUMENT_TYPE)
                    .hasPermission(SecurityPermissions.EDIT_DOCUMENT_TYPE)
                .end();
    }

    @Bean
    public SecurityRule readMetaDatas() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(METADATA_API_PREFIX)
                .build()
                .condition()
                    .hasPermission(SecurityPermissions.ROLE_ADMIN)
                    .hasPermission(SecurityPermissions.READ_METADATA)
                    .hasPermission(SecurityPermissions.READ_DOCUMENT)
                    .hasPermission(SecurityPermissions.ADD_DOCUMENT)
                    .hasPermission(SecurityPermissions.EDIT_DOCUMENT)
                    .hasPermission(SecurityPermissions.READ_FOLDER)
                    .hasPermission(SecurityPermissions.ADD_FOLDER)
                    .hasPermission(SecurityPermissions.EDIT_FOLDER)
                    .hasPermission(SecurityPermissions.ADD_FOLDER_TYPE)
                    .hasPermission(SecurityPermissions.EDIT_FOLDER_TYPE)
                    .hasPermission(SecurityPermissions.ADD_DOCUMENT_TYPE)
                    .hasPermission(SecurityPermissions.EDIT_DOCUMENT_TYPE)
                .end();
    }

    @Bean
    public SecurityRule updateMetaData() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(METADATA_API_PREFIX + METADATA_ID)
                .build()
                .condition()
                    .hasPermission(SecurityPermissions.ROLE_ADMIN)
                    .hasPermission(SecurityPermissions.EDIT_METADATA)
                .end();
    }

    @Bean
    public SecurityRule deleteMetaData() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(METADATA_API_PREFIX + METADATA_ID)
                .build()
                .condition()
                    .hasPermission(SecurityPermissions.ROLE_ADMIN)
                    .hasPermission(SecurityPermissions.DELETE_METADATA)
                .end();
    }

}
