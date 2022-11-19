package sn.webg.archivage.service.services.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sn.webg.archivage.service.annotations.FieldDisplayedFilter;
import sn.webg.archivage.service.annotations.FolderFilter;
import sn.webg.archivage.service.annotations.FolderTypeFilter;
import sn.webg.archivage.service.annotations.IsAdmin;
import sn.webg.archivage.service.annotations.MetaDataFilter;
import sn.webg.archivage.service.annotations.MetaDataSearchFilter;
import sn.webg.archivage.service.entities.FolderEntity;
import sn.webg.archivage.service.exceptions.FolderException;
import sn.webg.archivage.service.exceptions.ResourceNotFoundException;
import sn.webg.archivage.service.mappers.FolderMapper;
import sn.webg.archivage.service.models.FolderDTO;
import sn.webg.archivage.service.repositories.DocumentRepository;
import sn.webg.archivage.service.repositories.FolderRepository;
import sn.webg.archivage.service.repositories.MetaDataRepository;
import sn.webg.archivage.service.repositories.UserRepository;
import sn.webg.archivage.service.services.AbstractMetaDataService;
import sn.webg.archivage.service.services.FolderService;
import sn.webg.archivage.service.services.FolderTypeService;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class FolderServiceImpl extends AbstractMetaDataService implements FolderService {

    final FolderRepository folderRepository;

    final FolderTypeService folderTypeService;

    final FolderMapper folderMapper;

    final DocumentRepository documentRepository;

    static final String FOLDER_NOT_FOUND_MESSAGE = "[Folder] Not found Folder {0}";

    static final String REFERENCE_FOUND_MESSAGE = "La référence existe déjà";

    private final UserRepository userRepository;

    private final MetaDataRepository metaDataRepository;

    @Override
    public FolderDTO createFolder(FolderDTO folder) {

        //check reference
        if(folderRepository.readFolderEntityByReference(folder.getReference()).isPresent()){
        throw new ResourceNotFoundException(REFERENCE_FOUND_MESSAGE);
        }

        /* Getting folderType */
        var folderType = folderTypeService.readFolderType(folder.getFolderTypeId());

        /* Validate all metaData */
        validateMetaData(folder.getMetaDatas(), folderType.getMetaDataForTypeList());

        /* Getting related entity */
        var folderEntity = folderMapper.asEntity(folder);

        var createdFolder = folderMapper.asDto(folderRepository.save(folderEntity));

        log.info("createFolder end ok - folderId: {}", createdFolder.getId());
        log.trace("createFolder end ok - folder: {}", createdFolder);

        return createdFolder;
    }

    @Override
    public FolderDTO updateFolder(FolderDTO folder) {

        if (!folderRepository.existsById(folder.getId())) {
            throw  new ResourceNotFoundException(MessageFormat.format(FOLDER_NOT_FOUND_MESSAGE, folder.getId()));
        }

        /* Getting folderType */
        var folderType = folderTypeService.readFolderType(folder.getFolderTypeId());

        /* Validate all metaData */
        validateMetaData(folder.getMetaDatas(), folderType.getMetaDataForTypeList());

        /* Getting related entity */
        var folderEntity = folderMapper.asEntity(folder);

        var updatedFolder = folderMapper.asDto(folderRepository.save(folderEntity));

        log.info("updateFolder end ok - folderId: {}", updatedFolder.getId());
        log.trace("updateFolder end ok - folder: {}", updatedFolder);

        return updatedFolder;
    }

    @Override
    public FolderDTO readFolder(String folderId) {

        var folder = folderRepository.findById(folderId)
                .map(folderMapper::asDto)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(FOLDER_NOT_FOUND_MESSAGE, folderId)));

        log.info("readFolder end ok - folderId: {}", folderId);
        log.trace("readFolder end ok - folder: {}", folder);

        return folder;
    }

    @Override
    public void deleteFolder(String folderId) {

        if (!folderRepository.existsById(folderId)) {
            throw  new ResourceNotFoundException(MessageFormat.format(FOLDER_NOT_FOUND_MESSAGE, folderId));
        }

        if (documentRepository.existsByFolderId(folderId)) {
            throw new FolderException(MessageFormat.format("Le dossier [{0}] contient des documents ", folderId));
        }

        folderRepository.deleteById(folderId);
    }

    @Override
    public String lastReference() {
        return folderRepository.readByFilters(
                null,
                null,
                null,
                null,
                null,
                true,
                null,
                null,
                PageRequest.of(0, 1, Sort.by("reference").descending())
        ).stream().findFirst().map(FolderEntity::getReference).orElse("0");
    }

    @Override
    @FieldDisplayedFilter(type = FolderDTO.class)
    public FolderDTO readByIdFilters(String folderId, @MetaDataFilter Set<String> accessMetaData, @FolderTypeFilter Set<String> idFolderTypes, @IsAdmin boolean isAdmin, @FolderFilter Set<String> idFolders) {

        var folder = folderRepository.readByIdFilters(folderId, accessMetaData, idFolderTypes, isAdmin, idFolders)
                .map(folderMapper::asDto)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(FOLDER_NOT_FOUND_MESSAGE, folderId)));

        log.info("readFolder end ok - folderId: {}", folderId);
        log.trace("readFolder end ok - folder: {}", folder);

        return folder;
    }

    @Override
    @FieldDisplayedFilter(pageable = true, type = FolderDTO.class)
    public Page<FolderDTO> readByFilters(@MetaDataSearchFilter Map<String, Object> metaDataList, @MetaDataFilter Set<String> accessMetaData, String name, String reference, @FolderTypeFilter Set<String> idFolderTypes, @IsAdmin boolean isAdmin, @FolderFilter Set<String> idFolders, Integer year, Pageable pageable) {

        /* Setting valid value type in object*/
        metaDataList = setValueAndConvertValidTYpe(isAdmin, userRepository, metaDataRepository, metaDataList);

        var folders = folderRepository.readByFilters(metaDataList, accessMetaData, name, reference, idFolderTypes, isAdmin, idFolders, year, pageable).map(folderMapper::asDto);

        log.trace("list folder get ok {}", folders);

        return folders;
        
    }
}
