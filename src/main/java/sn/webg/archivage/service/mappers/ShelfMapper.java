package sn.webg.archivage.service.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import sn.webg.archivage.service.entities.ShelfEntity;
import sn.webg.archivage.service.models.ShelfDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ShelfMapper extends EntityMapper<ShelfDTO, ShelfEntity>{
}
