package sn.webg.archivage.service.services.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.webg.archivage.service.properties.DocumentProperties;
import sn.webg.archivage.service.services.DataStorageService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DataStorageServiceImpl implements DataStorageService {

    final DocumentProperties documentProperties;

    @Override
    public String storeFile(String type, String reference, String expention, InputStream inputStream) {

        /* Computing absoluteFilePath */
        final var absoluteFilePath = Paths.get(documentProperties.getFileStorageRootPath(), type,
                reference + FilenameUtils.EXTENSION_SEPARATOR + expention);

        /* Creating parent if not exist */
        absoluteFilePath.getParent().toFile().mkdirs();

        try {
            /* Storing file */
            Files.copy(inputStream, absoluteFilePath, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            log.error("An occurred during file storage: {} {}", absoluteFilePath, e);
        }

        return absoluteFilePath.toString();
    }
}
