package sn.webg.archivage.service.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import sn.webg.archivage.service.models.DocumentDTO;
import sn.webg.archivage.service.models.DownloadFile;
import sn.webg.archivage.service.models.FolderDTO;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Set;

public interface DocumentService {

    DocumentDTO createDocument(DocumentDTO document);

    DocumentDTO updateDocument(DocumentDTO document);
    
    DocumentDTO readDocument(String documentId);

    DocumentDTO readByIdFilters(@NotNull String documentId, Set<String> accessMetaData, Set<String> idDocumentTypes, boolean isAdmin, Set<String> idDocuments, Set<String> idFolders);

    void deleteDocument(String documentId);

    DocumentDTO addFile(String id, MultipartFile file);

    DownloadFile readFile(String id);

    Page<DocumentDTO> readByFilters(Set<String> accessMetaData, String name, String reference, Set<String> idDocumentTypes, boolean isAdmin, Set<String> idDocuments, Set<String> idFolders, Pageable pageable, Map<String, Object> metaDataList);

}
