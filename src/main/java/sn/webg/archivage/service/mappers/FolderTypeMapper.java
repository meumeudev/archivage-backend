package sn.webg.archivage.service.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import sn.webg.archivage.service.entities.FolderTypeEntity;
import sn.webg.archivage.service.models.FolderTypeDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {MetaDataForTypeMapper.class})
public interface FolderTypeMapper extends EntityMapper<FolderTypeDTO, FolderTypeEntity>{
}
