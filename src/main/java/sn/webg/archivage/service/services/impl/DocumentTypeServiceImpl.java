package sn.webg.archivage.service.services.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sn.webg.archivage.service.exceptions.ResourceNotFoundException;
import sn.webg.archivage.service.mappers.DocumentTypeMapper;
import sn.webg.archivage.service.models.DocumentTypeDTO;
import sn.webg.archivage.service.models.MetaDataType;
import sn.webg.archivage.service.repositories.DocumentTypeRepository;
import sn.webg.archivage.service.services.DocumentTypeService;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class DocumentTypeServiceImpl implements DocumentTypeService {
    
    final DocumentTypeRepository documentTypeRepository;
    
    final DocumentTypeMapper documentTypeMapper;

    static final String FOLDER_TYPE_NOT_FOUND_MESSAGE = "[DocumentType] Not found DocumentType {0}";
    
    @Override
    public DocumentTypeDTO createDocumentType(DocumentTypeDTO documentType) {

        var documentTypeEntity = documentTypeMapper.asEntity(documentType);

        /* Creating documentType */
        var createdDocumentType = documentTypeMapper.asDto(documentTypeRepository.save(documentTypeEntity));

        log.info("createDocumentType end ok - documentTypeId: {}", documentType.getId());
        log.trace("createDocumentType end ok - documentType: {}", documentType);

        return createdDocumentType;
    }

    @Override
    public DocumentTypeDTO updateDocumentType(DocumentTypeDTO documentType) {

        if (!documentTypeRepository.existsById(documentType.getId())) {
            throw  new ResourceNotFoundException(MessageFormat.format(FOLDER_TYPE_NOT_FOUND_MESSAGE, documentType.getId()));
        }

        var updatedDocumentType = documentTypeRepository.save(documentTypeMapper.asEntity(documentType));

        log.info("updateDocumentType end ok - documentTypeId: {}", documentType.getId());
        log.trace("updateDocumentType end ok - documentType: {}", documentType);

        return documentTypeMapper.asDto(updatedDocumentType);
        
    }

    @Override
    public DocumentTypeDTO readDocumentType(String id) {

        var documentType = documentTypeRepository.findById(id)
                .map(documentTypeMapper::asDto)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(FOLDER_TYPE_NOT_FOUND_MESSAGE, id)));

        log.info("readDocumentType end ok - documentTypeId: {}", id);
        log.trace("readDocumentType end ok - documentType: {}", documentType);

        return documentType;
    }

    @Override
    public void deleteDocumentType(String id) {

        if (!documentTypeRepository.existsById(id)) {
            throw  new ResourceNotFoundException(MessageFormat.format(FOLDER_TYPE_NOT_FOUND_MESSAGE, id));
        }

        documentTypeRepository.deleteById(id);
        
    }

    @Override
    public Page<DocumentTypeDTO> readByFilters(String code, String label, List<MetaDataType> metaDataTypeList, List<String> metaDataLabelList, Pageable pageable) {

        var documentTypes = documentTypeRepository.readByFilters(
                code,
                label,
                metaDataTypeList,
                metaDataLabelList,
                pageable
        ).map(documentTypeMapper::asDto);

        log.trace("list documentType get ok {}", documentTypes);

        return documentTypes;
    }
}
