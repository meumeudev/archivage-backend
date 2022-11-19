package sn.webg.archivage.service.mappers;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import sn.webg.archivage.service.entities.OrganizationEntity;
import sn.webg.archivage.service.entities.OrganizationTypeEntity;
import sn.webg.archivage.service.models.OrganizationDTO;

import java.util.Objects;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface OrganizationMapper extends EntityMapper<OrganizationDTO, OrganizationEntity>{

    @Override
    @Mapping(target = "organizationTypeId", source = "organizationType.id")
    OrganizationDTO asDto(OrganizationEntity organizationEntity);

    @Override
    @Mapping(target = "organizationType", source = "organizationDTO", qualifiedByName = "organizationTypeId")
    OrganizationEntity asEntity(OrganizationDTO organizationDTO);

    @Named("organizationTypeId")
    default OrganizationTypeEntity getOrganizationTypeId(OrganizationDTO organizationDTO) {
        if(StringUtils.isNotEmpty(organizationDTO.getOrganizationTypeId())) {

            OrganizationTypeEntity organizationTypeEntity = new OrganizationTypeEntity();

            organizationTypeEntity.setId(organizationDTO.getOrganizationTypeId());

            if(Objects.nonNull(organizationDTO.getOrganizationType())) {
                organizationTypeEntity.setCode(organizationDTO.getOrganizationType().getCode());
                organizationTypeEntity.setLabel(organizationDTO.getOrganizationType().getLabel());
            }

            return organizationTypeEntity;
        }
        return null;
    }
}
