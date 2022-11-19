package sn.webg.archivage.service.models;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Document representation")
public class DocumentDTO extends AbstractStorage {

    @Schema(description = "L'id technique, généré au moment de persister l'objet", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    String id;

    @Schema(description = "Chemin du fichier stocké", accessMode = Schema.AccessMode.READ_ONLY)
    String path;

    String description;

    @NotEmpty
    @NotNull
    String documentTypeId;

    @NotEmpty
    @NotNull
    String folderId;

    @Schema(description = "Reference unique du document", required = true)
    @NotNull
    @NotEmpty
    String reference;

    @Schema(description = "Nom du document", required = true)
    @NotNull
    @NotEmpty
    String name;

    String filePath;

    @Schema(description = "Type du document", required = true, accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    DocumentTypeDTO documentType;

    @Schema(description = "Dossier sur lequel le document se trouve", required = true, accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    FolderDTO folder;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    String createdBy;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    LocalDateTime createdDate;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    String lastModifiedBy;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    LocalDateTime lastModifiedDate;

    @Builder
    public DocumentDTO(@NotNull Map<String, Object> metaDatas, String id, String path, String description, String documentTypeId, String folderId, String reference, String name, DocumentTypeDTO documentType, FolderDTO folder, String createdBy, LocalDateTime createdDate, String lastModifiedBy, LocalDateTime lastModifiedDate, String  filePath) {
        super(metaDatas);
        this.id = id;
        this.path = path;
        this.description = description;
        this.documentTypeId = documentTypeId;
        this.folderId = folderId;
        this.reference = reference;
        this.name = name;
        this.documentType = documentType;
        this.folder = folder;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
        this.filePath = filePath;
    }
}
