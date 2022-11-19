package sn.webg.archivage.service.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import sn.webg.archivage.service.models.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FolderTypeRules {

    static final String FOLDER_TYPE_API_PREFIX = "/v1/folderTypes";
    static final String FOLDER_TYPE_ID = "/{folderTypeId}";

    @Bean
    public SecurityRule createFolderType() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(FOLDER_TYPE_API_PREFIX)
                .build()
                .condition()
                    .hasPermission(SecurityPermissions.ROLE_ADMIN)
                    .hasPermission(SecurityPermissions.ADD_FOLDER_TYPE)
                .end();
    }

    @Bean
    public SecurityRule readFolderType() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(FOLDER_TYPE_API_PREFIX + FOLDER_TYPE_ID)
                .build()
                .condition()
                    .hasPermission(SecurityPermissions.ROLE_ADMIN)
                    .hasPermission(SecurityPermissions.READ_FOLDER_TYPE)
                .end();
    }

    @Bean
    public SecurityRule readFolderTypes() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(FOLDER_TYPE_API_PREFIX)
                .build()
                .condition()
                    .hasPermission(SecurityPermissions.ROLE_ADMIN)
                    .hasPermission(SecurityPermissions.ADD_FOLDER_TYPE)
                    .hasPermission(SecurityPermissions.READ_FOLDER_TYPE)
                    .hasPermission(SecurityPermissions.EDIT_FOLDER_TYPE)
                    .hasPermission(SecurityPermissions.DELETE_FOLDER_TYPE)
                    .hasPermission(SecurityPermissions.ADD_FOLDER)
                    .hasPermission(SecurityPermissions.READ_FOLDER)
                    .hasPermission(SecurityPermissions.EDIT_FOLDER)
                .end();
    }

    @Bean
    public SecurityRule updateFolderType() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(FOLDER_TYPE_API_PREFIX + FOLDER_TYPE_ID)
                .build()
                .condition()
                    .hasPermission(SecurityPermissions.ROLE_ADMIN)
                    .hasPermission(SecurityPermissions.EDIT_FOLDER_TYPE)
                .end();
    }

    @Bean
    public SecurityRule deleteFolderType() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(FOLDER_TYPE_API_PREFIX + FOLDER_TYPE_ID)
                .build()
                .condition()
                    .hasPermission(SecurityPermissions.ROLE_ADMIN)
                    .hasPermission(SecurityPermissions.DELETE_FOLDER_TYPE)
                .end();
    }

}
