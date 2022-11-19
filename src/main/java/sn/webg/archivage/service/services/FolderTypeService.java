package sn.webg.archivage.service.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.webg.archivage.service.models.FolderTypeDTO;
import sn.webg.archivage.service.models.MetaDataType;

import java.util.List;

public interface FolderTypeService {

    FolderTypeDTO createFolderType(FolderTypeDTO folderType);

    FolderTypeDTO updateFolderType(FolderTypeDTO folderType);

    FolderTypeDTO readFolderType(String id);

    void deleteFolderType(String id);

    Page<FolderTypeDTO> readByFilters(String code, String label, List<String> metaDataList, Pageable pageable);
}
