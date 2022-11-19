package sn.webg.archivage.service.services.utils;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import sn.webg.archivage.service.exceptions.ResourceNotFoundException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.text.MessageFormat;

@Slf4j
@UtilityClass
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InputStreamUtils {

    static final String FILE_NOT_FOUND_MESSAGE = "File not found with the filepath: {0}";

    public InputStream generateInputStream(String filePath) {

        /* Initializing accessListFile */
        File file = new File(filePath);

        /* Checking if file exists */
        if ((!file.exists())) {
            throw new ResourceNotFoundException(MessageFormat.format(FILE_NOT_FOUND_MESSAGE, filePath));
        }

        /* Initializing inputStream */
        InputStream inputStream = null;

        try {
            /* Getting fileStream*/
            inputStream = Files.newInputStream(file.toPath());
        } catch (IOException e) {
            log.error("File input stream error ..... ", e);
        }

        log.info("generateInputStream end ok - filePath: {}", filePath);

        return inputStream;
    }
}
