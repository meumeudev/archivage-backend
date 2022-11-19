package sn.webg.archivage.service.services.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sn.webg.archivage.service.annotations.DocumentFilter;
import sn.webg.archivage.service.annotations.DocumentTypeFilter;
import sn.webg.archivage.service.annotations.FieldDisplayedFilter;
import sn.webg.archivage.service.annotations.FolderFilter;
import sn.webg.archivage.service.annotations.IsAdmin;
import sn.webg.archivage.service.annotations.MetaDataFilter;
import sn.webg.archivage.service.annotations.MetaDataSearchFilter;
import sn.webg.archivage.service.entities.DocumentEntity;
import sn.webg.archivage.service.exceptions.ResourceNotFoundException;
import sn.webg.archivage.service.mappers.DocumentMapper;
import sn.webg.archivage.service.models.DocumentDTO;
import sn.webg.archivage.service.models.DownloadFile;
import sn.webg.archivage.service.properties.DocumentProperties;
import sn.webg.archivage.service.repositories.DocumentRepository;
import sn.webg.archivage.service.repositories.MetaDataRepository;
import sn.webg.archivage.service.repositories.UserRepository;
import sn.webg.archivage.service.services.AbstractMetaDataService;
import sn.webg.archivage.service.services.DataStorageService;
import sn.webg.archivage.service.services.DocumentService;
import sn.webg.archivage.service.services.DocumentTypeService;
import sn.webg.archivage.service.services.utils.DownloadFileUtils;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class DocumentServiceImpl extends AbstractMetaDataService implements DocumentService {

    final DocumentRepository documentRepository;

    final DocumentTypeService documentTypeService;

    final DocumentMapper documentMapper;

    static final String FOLDER_NOT_FOUND_MESSAGE = "[Document] Not found Document {0}";

    static final String INVALID_EXTENSION_MESSAGE = "File: {0} does not match expected extension: {1}";

    static final String DOCUMENT_ROOT_DIRECTORY = "documents";

    final DocumentProperties documentProperties;

    final DataStorageService dataStorageService;

    private final UserRepository userRepository;

    private final MetaDataRepository metaDataRepository;

    @Override
    public DocumentDTO createDocument(DocumentDTO document) {

        /* Getting documentType */
        var documentType = documentTypeService.readDocumentType(document.getDocumentTypeId());

        /* Validate all metaData */
        validateMetaData(document.getMetaDatas(), documentType.getMetaDataForTypeList());

        /* Getting related entity */
        var documentEntity = documentMapper.asEntity(document);

        var createdDocument = documentMapper.asDto(documentRepository.save(documentEntity));

        log.info("createDocument end ok - documentId: {}", createdDocument.getId());
        log.trace("createDocument end ok - document: {}", createdDocument);

        return createdDocument;
    }

    @Override
    public DocumentDTO updateDocument(DocumentDTO document) {

        if (!documentRepository.existsById(document.getId())) {
            throw  new ResourceNotFoundException(MessageFormat.format(FOLDER_NOT_FOUND_MESSAGE, document.getId()));
        }

        /* Getting documentType */
        var documentType = documentTypeService.readDocumentType(document.getDocumentTypeId());

        /* Validate all metaData */
        validateMetaData(document.getMetaDatas(), documentType.getMetaDataForTypeList());

        /* Getting related entity */
        var documentEntity = documentMapper.asEntity(document);

        var updatedDocument = documentMapper.asDto(documentRepository.save(documentEntity));

        log.info("updateDocument end ok - documentId: {}", updatedDocument.getId());
        log.trace("updateDocument end ok - document: {}", updatedDocument);

        return updatedDocument;
    }

    @Override
    public DocumentDTO readDocument(String documentId) {

        var document = documentRepository.findById(documentId)
                .map(documentMapper::asDto)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(FOLDER_NOT_FOUND_MESSAGE, documentId)));

        log.info("readDocument end ok - documentId: {}", documentId);
        log.trace("readDocument end ok - document: {}", document);

        return document;
    }

    @Override
    @FieldDisplayedFilter(type = DocumentDTO.class)
    public DocumentDTO readByIdFilters(String documentId, @MetaDataFilter Set<String> accessMetaData, @DocumentTypeFilter Set<String> idDocumentTypes, @IsAdmin boolean isAdmin, @DocumentFilter Set<String> idDocuments, @FolderFilter Set<String> idFolders) {

        var document = documentRepository.readByIdFilters(documentId, accessMetaData, idDocumentTypes, isAdmin, idDocuments, idFolders)
                .map(documentMapper::asDto)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(FOLDER_NOT_FOUND_MESSAGE, documentId)));

        log.info("readDocument end ok - documentId: {}", documentId);
        log.trace("readDocument end ok - document: {}", document);

        return document;
    }

    @Override
    public void deleteDocument(String documentId) {

        if (!documentRepository.existsById(documentId)) {
            throw  new ResourceNotFoundException(MessageFormat.format(FOLDER_NOT_FOUND_MESSAGE, documentId));
        }

        documentRepository.deleteById(documentId);
    }

    @Override
    public DocumentDTO addFile(String id, MultipartFile file) {

        /* Checking file extension */
        if (documentProperties.getAcceptFileExtensions().contains(FilenameUtils.getExtension(file.getOriginalFilename()))) {

            try(var fileInputStream = file.getInputStream()) {

                DocumentEntity documentEntity = documentRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(FOLDER_NOT_FOUND_MESSAGE, id)));


                /* Storing  file document */
                documentEntity.setFilePath(dataStorageService.storeFile(DOCUMENT_ROOT_DIRECTORY, documentEntity.getReference(), FilenameUtils.getExtension(file.getOriginalFilename()), fileInputStream));

                DocumentDTO documentUpdated = documentMapper.asDto(documentRepository.save(documentEntity));

                log.info("addFile end ok - documentId: {}", documentEntity.getId());
                log.trace("addFile end ok - document: {}", documentUpdated);

                return documentUpdated;

            } catch (IOException e) {
                log.error(MessageFormat.format("An error occurred with file: {0}", file.getOriginalFilename()), e);
                throw new ResourceNotFoundException(MessageFormat.format(FOLDER_NOT_FOUND_MESSAGE, id));
            }

        } else {
            throw new InvalidParameterException(MessageFormat.format(INVALID_EXTENSION_MESSAGE, file.getOriginalFilename(), documentProperties.getAcceptFileExtensions()));
        }
    }

    @Override
    public DownloadFile readFile(String id) {

        DocumentDTO document = readDocument(id);

        /* Getting downloadFile */
        DownloadFile downloadFile = DownloadFileUtils.generateDownloadFile(document.getFilePath());

        log.info("readFile end ok - documentId: {}", id);
        log.trace("readFile end ok - downloadFile: {}", downloadFile);

        return downloadFile;
    }

    @Override
    @FieldDisplayedFilter(pageable = true, type = DocumentDTO.class)
    public Page<DocumentDTO> readByFilters(@MetaDataFilter Set<String> accessMetaData, String name, String reference, @DocumentTypeFilter Set<String> idDocumentTypes, @IsAdmin boolean isAdmin, @DocumentFilter Set<String> idDocuments, @FolderFilter Set<String> idFolders, Pageable pageable, @MetaDataSearchFilter Map<String, Object> metaDataList) {

        /* Setting valid value type in object*/
        metaDataList = setValueAndConvertValidTYpe(isAdmin, userRepository, metaDataRepository, metaDataList);

        var documents = documentRepository.readByFilters(metaDataList, accessMetaData, name, reference, idDocumentTypes, isAdmin, idDocuments,idFolders, pageable).map(documentMapper::asDto);

        log.trace("list document get ok {}", documents);

        return documents;
        
    }
}
