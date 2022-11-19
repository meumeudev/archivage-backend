package sn.webg.archivage.service.services.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sn.webg.archivage.service.exceptions.ResourceNotFoundException;
import sn.webg.archivage.service.mappers.OrganisationTypeMapper;
import sn.webg.archivage.service.models.OrganizationTypeDTO;
import sn.webg.archivage.service.repositories.OrganizationTypeRepository;
import sn.webg.archivage.service.services.OrganizationTypeService;

import java.text.MessageFormat;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class OrganizationTypeServiceImpl implements OrganizationTypeService {

    static final String ORGANIZATIONTYPE_ID_NOT_FOUND_MESSAGE = "[OrganizationType] Not found OrganizationType {0}";

    final OrganizationTypeRepository organizationTypeRepository;
    final OrganisationTypeMapper organisationTypeMapper;

    @Override
    public OrganizationTypeDTO createOrganizationType(OrganizationTypeDTO organizationTypeDTO) {

        var createdOrganizationType = organizationTypeRepository.save(organisationTypeMapper.asEntity(organizationTypeDTO));

        log.info("create organizationType ok id {}", createdOrganizationType.getId());
        log.trace("create organizationType ok  {}", createdOrganizationType);

        return organisationTypeMapper.asDto(createdOrganizationType);
    }

    @Override
    public OrganizationTypeDTO updateOrganizationType(OrganizationTypeDTO organizationTypeDTO) {
        if (!organizationTypeRepository.existsById(organizationTypeDTO.getId())) {
            throw new ResourceNotFoundException(MessageFormat.format(ORGANIZATIONTYPE_ID_NOT_FOUND_MESSAGE, (organizationTypeDTO.getId())));
        }

        var organizationTypeEntity = organisationTypeMapper.asEntity(organizationTypeDTO);

        organizationTypeEntity.setId(organizationTypeDTO.getId());

        var updatedOrganizationType = organizationTypeRepository.save(organizationTypeEntity);

        log.info("update organizationType ok Id {}", organizationTypeEntity.getId());
        log.trace("update organizationType ok Id {}", organizationTypeEntity);

        return organisationTypeMapper.asDto(updatedOrganizationType);

    }

    @Override
    public void deleteOrganizationType(String id) {
        if (!organizationTypeRepository.existsById(id)) {
            throw new ResourceNotFoundException(MessageFormat.format(ORGANIZATIONTYPE_ID_NOT_FOUND_MESSAGE, (id)));
        }
        organizationTypeRepository.deleteById(id);

        log.info("delete organization ok id {}", id);
    }

    @Override
    public OrganizationTypeDTO readOrganizationType(String id) {
        if (!organizationTypeRepository.existsById(id)) {
            throw new ResourceNotFoundException(MessageFormat.format(ORGANIZATIONTYPE_ID_NOT_FOUND_MESSAGE, (id)));
        }
        var organizationType = organizationTypeRepository.findById(id)
                .map(organisationTypeMapper::asDto)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(ORGANIZATIONTYPE_ID_NOT_FOUND_MESSAGE, id)));

        log.info("read organizationType end ok - Id: {}", id);
        log.trace("read organizationType end ok - organization: {}", organizationType);

        return organizationType;
    }


    @Override
    public OrganizationTypeDTO readOrganisationTypeByCode(String code) {
        var organizationType = organizationTypeRepository.findByCode(code);

        log.trace("organizationType get ok {}", organizationType);

        return organisationTypeMapper.asDto(organizationType);
    }

    @Override
    public Page<OrganizationTypeDTO> readAllOrganizationTypes(Pageable pageable, String code, String label) {

        var organizationTypeDTOS = organizationTypeRepository.readAllByFilters(pageable, code, label).map(organisationTypeMapper::asDto);

        log.trace("list organizationType get ok {}", organizationTypeDTOS);

        return organizationTypeDTOS;
    }
}
