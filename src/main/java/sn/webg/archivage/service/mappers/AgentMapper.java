package sn.webg.archivage.service.mappers;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import sn.webg.archivage.service.entities.AgentEntity;
import sn.webg.archivage.service.entities.OrganizationEntity;
import sn.webg.archivage.service.models.AgentDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface AgentMapper extends EntityMapper<AgentDTO, AgentEntity> {

    @Override
    @Mapping(target = "organizationId", source = "organization.id")
    AgentDTO asDto(AgentEntity agentEntity);

    @Override
    @Mapping(target = "organization", source = "agentDTO", qualifiedByName = "organizationId")
    AgentEntity asEntity(AgentDTO agentDTO);

    @Named("organizationId")
    default OrganizationEntity organizationId(AgentDTO agentDTO) {
        if (StringUtils.isNotEmpty(agentDTO.getOrganizationId())) {
            return OrganizationEntity.builder().id(agentDTO.getOrganizationId()).build();
        }
        return null;
    }
}

