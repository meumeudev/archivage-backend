package sn.webg.archivage.service.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import sn.webg.archivage.service.entities.DocumentEntity;
import sn.webg.archivage.service.entities.DocumentTypeEntity;
import sn.webg.archivage.service.entities.FolderEntity;
import sn.webg.archivage.service.models.DocumentDTO;

import java.util.Objects;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {FolderMapper.class})
public interface DocumentMapper extends EntityMapper<DocumentDTO, DocumentEntity>{

    @Override
    @Mapping(target = "documentType", source = "documentDTO", qualifiedByName = "documentType")
    @Mapping(target = "folder", source = "documentDTO", qualifiedByName = "folder")
    DocumentEntity asEntity(DocumentDTO documentDTO);

    @Override
    @Mapping(target = "documentTypeId", source = "documentType.id")
    @Mapping(target = "folderId", source = "folder.id")
    DocumentDTO asDto(DocumentEntity documentEntity);

    @Named("documentType")
    default DocumentTypeEntity getDocumentType(DocumentDTO documentDTO) {
        DocumentTypeEntity documentType = DocumentTypeEntity
                .builder()
                .id(documentDTO.getDocumentTypeId()).build();

        if (Objects.nonNull(documentDTO.getDocumentType())) {
            documentType.setCode(documentDTO.getDocumentType().getCode());
            documentType.setLabel(documentDTO.getDocumentType().getLabel());
        }

        return documentType;
    }

    @Named("folder")
    default FolderEntity getFolder(DocumentDTO documentDTO) {

        return FolderEntity
                .builder()
                .id(documentDTO.getFolderId()).build();
    }
}
