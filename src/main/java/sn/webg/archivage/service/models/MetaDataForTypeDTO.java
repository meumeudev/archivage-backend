package sn.webg.archivage.service.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MetaDataForTypeDTO {

    @Schema(description = "Voir si le champ est obligatoire")
    boolean required = false;

    @Schema(description = "Identifiant Technique metaData")
    String metaDataId;

    @NotEmpty
    @NotNull
    @Schema(description = "Les valeurs possibles")
    List<String> possibleValues;

    @Schema(description = "Voir si le champ doit etre affiché")
    boolean visibleInDisplay;

    @Schema(description = "Voir si le champ doit etre visible a la recherche")
    boolean availableInSearch;

    MetaDataDTO metaData;
}
