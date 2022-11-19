package sn.webg.archivage.service.services.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sn.webg.archivage.service.exceptions.ResourceNotFoundException;
import sn.webg.archivage.service.mappers.FolderTypeMapper;
import sn.webg.archivage.service.models.FolderTypeDTO;
import sn.webg.archivage.service.models.MetaDataType;
import sn.webg.archivage.service.repositories.FolderTypeRepository;
import sn.webg.archivage.service.services.FolderTypeService;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class FolderTypeServiceImpl implements FolderTypeService {
    
    final FolderTypeRepository folderTypeRepository;
    
    final FolderTypeMapper folderTypeMapper;

    static final String FOLDER_TYPE_NOT_FOUND_MESSAGE = "[FolderType] Not found FolderType {0}";
    
    @Override
    public FolderTypeDTO createFolderType(FolderTypeDTO folderType) {

        var folderTypeEntity = folderTypeMapper.asEntity(folderType);

        /* Creating folderType */
        var createdFolderType = folderTypeMapper.asDto(folderTypeRepository.save(folderTypeEntity));

        log.info("createFolderType end ok - folderTypeId: {}", folderType.getId());
        log.trace("createFolderType end ok - folderType: {}", folderType);

        return createdFolderType;
    }

    @Override
    public FolderTypeDTO updateFolderType(FolderTypeDTO folderType) {

        if (!folderTypeRepository.existsById(folderType.getId())) {
            throw  new ResourceNotFoundException(MessageFormat.format(FOLDER_TYPE_NOT_FOUND_MESSAGE, folderType.getId()));
        }

        var updatedFolderType = folderTypeRepository.save(folderTypeMapper.asEntity(folderType));

        log.info("updateFolderType end ok - folderTypeId: {}", folderType.getId());
        log.trace("updateFolderType end ok - folderType: {}", folderType);

        return folderTypeMapper.asDto(updatedFolderType);
        
    }

    @Override
    public FolderTypeDTO readFolderType(String id) {

        var folderType = folderTypeRepository.findById(id)
                .map(folderTypeMapper::asDto)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(FOLDER_TYPE_NOT_FOUND_MESSAGE, id)));

        log.info("readFolderType end ok - folderTypeId: {}", id);
        log.trace("readFolderType end ok - folderType: {}", folderType);

        return folderType;
    }

    @Override
    public void deleteFolderType(String id) {

        if (!folderTypeRepository.existsById(id)) {
            throw  new ResourceNotFoundException(MessageFormat.format(FOLDER_TYPE_NOT_FOUND_MESSAGE, id));
        }

        folderTypeRepository.deleteById(id);
        
    }

    @Override
    public Page<FolderTypeDTO> readByFilters(String code, String label, List<String> metaDataList, Pageable pageable) {

        var folderTypes = folderTypeRepository.readByFilters(
                code,
                label,
                metaDataList,
                pageable
        ).map(folderTypeMapper::asDto);

        log.trace("list folderType get ok {}", folderTypes);

        return folderTypes;
    }
}
