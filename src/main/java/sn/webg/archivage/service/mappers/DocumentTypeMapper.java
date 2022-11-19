package sn.webg.archivage.service.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import sn.webg.archivage.service.entities.DocumentTypeEntity;
import sn.webg.archivage.service.models.DocumentTypeDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {MetaDataForTypeMapper.class})
public interface DocumentTypeMapper extends EntityMapper<DocumentTypeDTO, DocumentTypeEntity>{
}
