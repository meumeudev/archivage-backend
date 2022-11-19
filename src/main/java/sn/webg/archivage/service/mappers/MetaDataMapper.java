package sn.webg.archivage.service.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import sn.webg.archivage.service.entities.MetaDataEntity;
import sn.webg.archivage.service.models.MetaDataDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface MetaDataMapper extends EntityMapper<MetaDataDTO, MetaDataEntity>{
}
