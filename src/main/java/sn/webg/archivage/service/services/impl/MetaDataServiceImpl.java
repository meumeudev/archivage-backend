package sn.webg.archivage.service.services.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sn.webg.archivage.service.exceptions.MetaDataException;
import sn.webg.archivage.service.exceptions.ResourceNotFoundException;
import sn.webg.archivage.service.mappers.MetaDataMapper;
import sn.webg.archivage.service.models.MetaDataDTO;
import sn.webg.archivage.service.models.MetaDataType;
import sn.webg.archivage.service.repositories.DocumentTypeRepository;
import sn.webg.archivage.service.repositories.FolderTypeRepository;
import sn.webg.archivage.service.repositories.MetaDataRepository;
import sn.webg.archivage.service.services.MetaDataService;

import java.text.MessageFormat;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class MetaDataServiceImpl implements MetaDataService {

    final MetaDataRepository metaDataRepository;

    final MetaDataMapper metaDataMapper;

    static final String METADATA_NOT_FOUND_MESSAGE = "[MetaData] Not found MetaData {0}";

    final DocumentTypeRepository documentTypeRepository;

    final FolderTypeRepository folderTypeRepository;

    @Override
    public MetaDataDTO createMetaData(MetaDataDTO metaData) {

        var metaDataEntity = metaDataMapper.asEntity(metaData);

        /* Creating metaData */
        var createdMetaData = metaDataMapper.asDto(metaDataRepository.save(metaDataEntity));

        log.info("createMetaData end ok - metaDataId: {}", createdMetaData.getId());
        log.trace("createMetaData end ok - metaData: {}", createdMetaData);

        return createdMetaData;
    }

    @Override
    public MetaDataDTO readMetaData(String id) {

        var metaData = metaDataRepository.findById(id)
                .map(metaDataMapper::asDto)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(METADATA_NOT_FOUND_MESSAGE, id)));

        log.info("readMetaData end ok - metaDataId: {}", id);
        log.trace("readMetaData end ok - metaData: {}", metaData);

        return metaData;
    }

    @Override
    public MetaDataDTO readMetaDataByLabel(String label) {

        var metaData = metaDataRepository.findByLabel(label)
                .map(metaDataMapper::asDto)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(METADATA_NOT_FOUND_MESSAGE, label)));

        log.info("readMetaData end ok - metaDataLabel: {}", label);
        log.trace("readMetaData end ok - metaData: {}", metaData);

        return metaData;
    }

    @Override
    public Page<MetaDataDTO> readByFilters(String label, MetaDataType metaDataType, Pageable pageable) {

        var metaDataList = metaDataRepository.readByFilters(
                label,
                metaDataType,
                pageable
        ).map(metaDataMapper::asDto);

        log.trace("list metaData get ok {}", metaDataList);

        return metaDataList;
    }

    @Override
    public MetaDataDTO updateMetaData(MetaDataDTO metaDataDTO) {

        if (!metaDataRepository.existsById(metaDataDTO.getId())) {
            throw  new ResourceNotFoundException(MessageFormat.format(METADATA_NOT_FOUND_MESSAGE, metaDataDTO.getId()));
        }

        if (checkMetaDataUsed(metaDataDTO.getId())) {
            metaDataRepository.findById(metaDataDTO.getId()).ifPresent(metaData -> metaDataDTO.setLabel(metaData.getLabel()));
        }

        var updatedMetaData = metaDataRepository.save(metaDataMapper.asEntity(metaDataDTO));

        log.info("updateMetaData end ok - metaDataId: {}", metaDataDTO.getId());
        log.trace("updateMetaData end ok - metaData: {}", metaDataDTO);

        return metaDataMapper.asDto(updatedMetaData);
    }

    @Override
    public void deleteMetaData(String id) {

        if (!metaDataRepository.existsById(id)) {
            throw  new ResourceNotFoundException(MessageFormat.format(METADATA_NOT_FOUND_MESSAGE, id));
        }

        if (checkMetaDataUsed(id)) {
            throw new MetaDataException(MessageFormat.format("La metadonnees [{}] est utilisé", id));
        }

        metaDataRepository.deleteById(id);

    }

    private boolean checkMetaDataUsed(String metaDataId) {
        return folderTypeRepository.existsByMetaDataForTypeListMetaDataId(metaDataId) || documentTypeRepository.existsByMetaDataForTypeListMetaDataId(metaDataId);
    }
}
