package sn.webg.archivage.service.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.Setter;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public enum SecurityPermissions {

    ADD_ROLE("Ajouter profile", Module.SECURITY),
    ROLE_ADMIN("", Module.SECURITY),
    EDIT_ROLE("Modifier profile", Module.SECURITY),
    DELETE_ROLE("Supprimer profile", Module.SECURITY),
    READ_ROLE("Consulter profile", Module.SECURITY),
    DELETE_USER("Supprimer utilisateur", Module.SECURITY),
    ADD_USER("Ajouter utilisateur", Module.SECURITY),
    EDIT_USER("Modifier utilisateur", Module.SECURITY),
    READ_USER("Consulter utilisateur", Module.SECURITY),
    ADD_ORGANIZATION("Ajouter structure", Module.REFERENTIEL),
    EDIT_ORGANIZATION("Modifier structure", Module.REFERENTIEL),
    DELETE_ORGANIZATION("Supprimer structure", Module.REFERENTIEL),
    READ_ORGANIZATION("Consulter structure", Module.REFERENTIEL),
    ADD_ORGANIZATION_TYPE("Ajouter type structure", Module.REFERENTIEL),
    EDIT_ORGANIZATION_TYPE("Modifier type structure", Module.REFERENTIEL),
    DELETE_ORGANIZATION_TYPE("Supprimer type structure", Module.REFERENTIEL),
    READ_ORGANIZATION_TYPE("Consulter type structure", Module.REFERENTIEL),
    ADD_CARDBOARD("Ajouter carton", Module.REFERENTIEL),
    EDIT_CARDBOARD("Modifier carton", Module.REFERENTIEL),
    DELETE_CARDBOARD("Supprimer carton", Module.REFERENTIEL),
    READ_CARDBOARD("Consulter carton", Module.REFERENTIEL),
    ADD_SHELF("Ajouter rayon", Module.REFERENTIEL),
    EDIT_SHELF("Modifier rayon", Module.REFERENTIEL),
    DELETE_SHELF("Supprimer rayon", Module.REFERENTIEL),
    READ_SHELF("Consulter rayon", Module.REFERENTIEL),
    ADD_FOLDER_TYPE("Ajouter type dossier", Module.REFERENTIEL),
    EDIT_FOLDER_TYPE("Modifier type dossier", Module.REFERENTIEL),
    DELETE_FOLDER_TYPE("Supprimer type dossier", Module.REFERENTIEL),
    READ_FOLDER_TYPE("Consulter type dossier", Module.REFERENTIEL),
    ADD_FOLDER("Ajouter dossier", Module.DOSSIER),
    EDIT_FOLDER("Modifier dossier", Module.DOSSIER),
    DELETE_FOLDER("Supprimer dossier", Module.DOSSIER),
    READ_FOLDER("Consulter dossier", Module.DOSSIER),
    ADD_DOCUMENT_TYPE("Ajouter type document", Module.REFERENTIEL),
    EDIT_DOCUMENT_TYPE("Modifier type document", Module.REFERENTIEL),
    DELETE_DOCUMENT_TYPE("Supprimer type document", Module.REFERENTIEL),
    READ_DOCUMENT_TYPE("Consulter type document", Module.REFERENTIEL),
    ADD_DOCUMENT("Ajouter document", Module.DOSSIER),
    EDIT_DOCUMENT("Modifier document", Module.DOSSIER),
    DELETE_DOCUMENT("Supprimer document", Module.DOSSIER),
    READ_DOCUMENT("Consulter document", Module.DOSSIER),
    ADD_METADATA("Ajouter metaData", Module.REFERENTIEL),
    EDIT_METADATA("Modifier metaData", Module.REFERENTIEL),
    DELETE_METADATA("Supprimer metaData", Module.REFERENTIEL),
    READ_METADATA("Consulter metaData", Module.REFERENTIEL);

    @Getter
    @Setter
    String description;

    @Getter
    @Setter
    Module module;

    SecurityPermissions(String description, Module module) {
        this.description = description;
        this.module = module;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static SecurityPermissions fromValue(Object permission) {
        if (permission instanceof Map) {
            Map<String, Object> mapPermission = (Map<String, Object>) permission;
            if (mapPermission.containsKey("name")) {
                return SecurityPermissions.valueOf(mapPermission.get("name").toString());
            }
        }
        if (permission instanceof String) {
            return SecurityPermissions.valueOf(permission.toString());
        }
        throw new IllegalArgumentException(MessageFormat.format("{0} not found with the value: {1} in [{2}]", SecurityPermissions.class, permission, values()));
    }

    @JsonValue
    Map<String, Object> getSecurityPermissions() {
        return Map.of(
                "name", name(),
                "module", module,
                "description", description
        );
    }

    public static Set<SecurityPermissions> readSecurityPermissionsByModule(Module module) {
        if (Objects.isNull(module)) {
            return Arrays.stream(values())
                    .filter(securityPermissions -> !securityPermissions.equals(SecurityPermissions.ROLE_ADMIN))
                    .collect(Collectors.toSet());
        }
        return Arrays.stream(values())
                .filter(securityPermissions -> !securityPermissions.equals(SecurityPermissions.ROLE_ADMIN))
                .filter(securityPermissions -> securityPermissions.module.equals(module))
                .collect(Collectors.toSet());
    }
}
