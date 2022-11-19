package sn.webg.archivage.service.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.webg.archivage.service.models.FolderDTO;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Set;

public interface FolderService {

    FolderDTO createFolder(FolderDTO folder);

    FolderDTO updateFolder(FolderDTO folder);

    FolderDTO readFolder(String folderId);

    void deleteFolder(String folderId);

    String lastReference();

    FolderDTO readByIdFilters(@NotNull String folderId, Set<String> accessMetaData, Set<String> idFolderTypes, boolean isAdmin, Set<String> idFolders);

    Page<FolderDTO> readByFilters(Map<String, Object> metaDataList, Set<String> accessMetaData, String name, String reference, Set<String> idFolderTypes, boolean isAdmin, Set<String> idFolders, Integer year, Pageable pageable);

}
