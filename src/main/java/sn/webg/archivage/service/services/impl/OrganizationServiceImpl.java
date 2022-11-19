package sn.webg.archivage.service.services.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sn.webg.archivage.service.exceptions.ResourceNotFoundException;
import sn.webg.archivage.service.mappers.OrganizationMapper;
import sn.webg.archivage.service.models.OrganizationDTO;
import sn.webg.archivage.service.repositories.OrganizationRepository;
import sn.webg.archivage.service.services.OrganizationService;

import java.text.MessageFormat;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class OrganizationServiceImpl implements OrganizationService {

    static final String ORGANIZATION_ID_NOT_FOUND_MESSAGE = "[Organization] Not found Organization {0}";

    final OrganizationRepository organizationRepository;
    final OrganizationMapper organizationMapper;


    @Override
    public OrganizationDTO createOrganization(OrganizationDTO organizationDTO) {

        organizationDTO.setId(null);

        var createdOrganization = organizationRepository.save(organizationMapper.asEntity(organizationDTO));

        log.info("create organization ok id {}", createdOrganization.getId());
        log.trace("create organization ok  {}", createdOrganization);

        return organizationMapper.asDto(createdOrganization);
    }

    @Override
    public OrganizationDTO updateOrganization(OrganizationDTO organizationDTO) {
        if (!organizationRepository.existsById(organizationDTO.getId())) {
            throw new ResourceNotFoundException(MessageFormat.format(ORGANIZATION_ID_NOT_FOUND_MESSAGE, (organizationDTO.getId())));
        }

        var updatedOrganization = organizationRepository.save(organizationMapper.asEntity(organizationDTO));

        log.info("update organization ok Id {}", updatedOrganization.getId());
        log.trace("update organization ok Id {}", updatedOrganization);

        return organizationMapper.asDto(updatedOrganization);
    }

    @Override
    public void deleteOrganization(String id) {

        if (!organizationRepository.existsById(id)) {

            throw new ResourceNotFoundException(MessageFormat.format(ORGANIZATION_ID_NOT_FOUND_MESSAGE, (id)));
        }
        organizationRepository.deleteById(id);

        log.info("delete organization ok id {}", id);
    }

    @Override
    public OrganizationDTO readOrganization(String id) {

        if (!organizationRepository.existsById(id)) {

            throw new ResourceNotFoundException(MessageFormat.format(ORGANIZATION_ID_NOT_FOUND_MESSAGE, (id)));
        }
        var organization = organizationRepository.findById(id)
                .map(organizationMapper::asDto)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(ORGANIZATION_ID_NOT_FOUND_MESSAGE, id)));

        log.info("read organization end ok - Id: {}", id);
        log.trace("read organization end ok - organization: {}", organization);

        return organization;
    }


    @Override
    public Page<OrganizationDTO> readAllOrganizations(Pageable pageable, String name, String phone, String responsible, String organizationTypeId) {

        var organizationDTOS = organizationRepository.readAllByFilters(pageable, name, phone, responsible, organizationTypeId).map(organizationMapper::asDto);

        log.trace("list organizationType get ok {}", organizationDTOS);

        return organizationDTOS;
    }

}
