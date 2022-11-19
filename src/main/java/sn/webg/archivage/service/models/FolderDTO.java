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
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Folder representation")
public class FolderDTO extends AbstractStorage {

    @Schema(description = "L'id technique, généré au moment de persister l'objet", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    String id;

    @NotNull
    @NotEmpty
    @Schema(description = "Nom associé au dossier")
    String name;

    String description;

    @NotNull
    @NotEmpty
    String folderTypeId;

    String cardboardId;

    String organizationId;

    @NotNull
    @NotEmpty
    @Schema(description = "Reference unique du dossier")
    String reference;

    @Schema(description = "Numero sequentiel")
    String sequentialNumber;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    FolderTypeDTO folderType;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    LocalDateTime date;

    Integer year;

    String comment;

    @Schema(description = "Carton de rangement du dossier", implementation = CardboardDTO.class, accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    CardboardDTO cardboard;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    OrganizationDTO organization;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    String createdBy;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    LocalDateTime createdDate;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    String lastModifiedBy;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    LocalDateTime lastModifiedDate;

    @Builder
    public FolderDTO(@NotNull Map<String, Object> metaDatas, String id, String name, String description, String folderTypeId, String reference, String sequentialNumber, FolderTypeDTO folderType, CardboardDTO cardboard, OrganizationDTO organization, String createdBy, LocalDateTime createdDate, String lastModifiedBy, LocalDateTime lastModifiedDate, String organizationId) {
        super(metaDatas);
        this.id = id;
        this.name = name;
        this.description = description;
        this.folderTypeId = folderTypeId;
        this.reference = reference;
        this.sequentialNumber = sequentialNumber;
        this.folderType = folderType;
        this.cardboard = cardboard;
        this.organization = organization;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
        this.organizationId = organizationId;
    }
}
