package sn.webg.archivage.service.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.webg.archivage.service.models.DocumentTypeDTO;
import sn.webg.archivage.service.models.MetaDataType;

import java.util.List;

public interface DocumentTypeService {

    DocumentTypeDTO createDocumentType(DocumentTypeDTO documentType);

    DocumentTypeDTO updateDocumentType(DocumentTypeDTO documentType);

    DocumentTypeDTO readDocumentType(String id);

    void deleteDocumentType(String id);

    Page<DocumentTypeDTO> readByFilters(String code, String label, List<MetaDataType> metaDataTypeList, List<String> metaDataLabelList, Pageable pageable);
}
