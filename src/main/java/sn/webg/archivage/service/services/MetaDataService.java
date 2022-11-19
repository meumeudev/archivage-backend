package sn.webg.archivage.service.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.webg.archivage.service.models.MetaDataDTO;
import sn.webg.archivage.service.models.MetaDataType;

public interface MetaDataService {

    MetaDataDTO createMetaData(MetaDataDTO metaData);

    MetaDataDTO readMetaData(String id);

    MetaDataDTO readMetaDataByLabel(String label);

    Page<MetaDataDTO> readByFilters(String label, MetaDataType metaDataType, Pageable pageable);

    MetaDataDTO updateMetaData(MetaDataDTO metaDataDTO);

    void deleteMetaData(String id);


}
