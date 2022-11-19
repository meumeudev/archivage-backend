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
import java.util.Set;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Schema(description = "FolderType representation")
public class FolderTypeDTO {

    @Schema(description = "L'id technique, généré au moment de persister l'objet", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    String id;

    @Schema(description = "unique code")
    @NotNull
    @NotEmpty
    String code;

    @Schema(description = "Liste des meta donnees associe")
    Set<MetaDataForTypeDTO> metaDataForTypeList;

    @NotNull
    @NotEmpty
    @Schema(description = "Nom associé au type de dossier")
    String label;

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
}
