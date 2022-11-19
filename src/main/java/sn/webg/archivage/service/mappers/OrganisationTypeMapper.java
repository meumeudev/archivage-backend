package sn.webg.archivage.service.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import sn.webg.archivage.service.entities.OrganizationTypeEntity;
import sn.webg.archivage.service.models.OrganizationTypeDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface OrganisationTypeMapper extends EntityMapper<OrganizationTypeDTO, OrganizationTypeEntity>{
}

