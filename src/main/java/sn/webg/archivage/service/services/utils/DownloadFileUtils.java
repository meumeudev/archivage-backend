package sn.webg.archivage.service.services.utils;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import sn.webg.archivage.service.models.DownloadFile;

@Slf4j
@UtilityClass
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DownloadFileUtils {

    public DownloadFile generateDownloadFile(String filePath) {

        /* Generating downloadFile */
        DownloadFile downloadFile = DownloadFile
                .builder()
                .fileName(FilenameUtils.getName(filePath))
                .inputStream(InputStreamUtils.generateInputStream(filePath)).build();

        log.info("generateDownloadFile end ok - filePath: {}", downloadFile.getFileName());
        log.trace("generateDownloadFile end ok - downloadFile: {}", downloadFile);

        return downloadFile;
    }
}
