package sn.webg.archivage.service.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.io.InputStream;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DownloadFile {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    String fileName;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    InputStream inputStream;
}
