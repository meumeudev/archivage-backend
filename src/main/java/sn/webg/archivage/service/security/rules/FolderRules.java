package sn.webg.archivage.service.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import sn.webg.archivage.service.models.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FolderRules {

    static final String FOLDER_API_PREFIX = "/v1/folders";
    static final String FOLDER_ID = "/{folderId}";

    @Bean
    public SecurityRule createFolder() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.POST)
                .apiPattern(FOLDER_API_PREFIX)
                .build()
                .condition()
                    .hasPermission(SecurityPermissions.ADD_FOLDER)
                    .hasPermission(SecurityPermissions.ROLE_ADMIN)
                .end();
    }

    @Bean
    public SecurityRule readFolder() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(FOLDER_API_PREFIX + FOLDER_ID)
                .build()
                .condition()
                    .hasPermission(SecurityPermissions.READ_FOLDER)
                    .hasPermission(SecurityPermissions.ROLE_ADMIN)
                .end();
    }

    @Bean
    public SecurityRule readFolders() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(FOLDER_API_PREFIX)
                .build()
                .condition()
                    .hasPermission(SecurityPermissions.ROLE_ADMIN)
                    .hasPermission(SecurityPermissions.ADD_FOLDER)
                    .hasPermission(SecurityPermissions.READ_FOLDER)
                    .hasPermission(SecurityPermissions.EDIT_FOLDER)
                    .hasPermission(SecurityPermissions.DELETE_FOLDER)
                    .hasPermission(SecurityPermissions.ADD_DOCUMENT)
                    .hasPermission(SecurityPermissions.EDIT_DOCUMENT)
                .end();
    }

    @Bean
    public SecurityRule updateFolder() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.PUT)
                .apiPattern(FOLDER_API_PREFIX + FOLDER_ID)
                .build()
                .condition()
                    .hasPermission(SecurityPermissions.ROLE_ADMIN)
                    .hasPermission(SecurityPermissions.EDIT_FOLDER)
                .end();
    }

    @Bean
    public SecurityRule deleteFolder() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.DELETE)
                .apiPattern(FOLDER_API_PREFIX + FOLDER_ID)
                .build()
                .condition()
                    .hasPermission(SecurityPermissions.ROLE_ADMIN)
                    .hasPermission(SecurityPermissions.DELETE_FOLDER)
                .end();
    }

    @Bean
    public SecurityRule lastReference() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(FOLDER_API_PREFIX + "/reference/_last")
                .build()
                .condition()
                .hasPermission(SecurityPermissions.EDIT_FOLDER)
                .hasPermission(SecurityPermissions.ADD_FOLDER)
                .hasPermission(SecurityPermissions.READ_FOLDER)
                .hasPermission(SecurityPermissions.DELETE_FOLDER)
                .hasPermission(SecurityPermissions.ROLE_ADMIN)
                .end();
    }

}
