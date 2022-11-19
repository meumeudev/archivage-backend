package sn.webg.archivage.service.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ValuePermission {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    String id;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    String name;

    String type;
}
