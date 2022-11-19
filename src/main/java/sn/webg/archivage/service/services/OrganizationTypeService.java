package sn.webg.archivage.service.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.webg.archivage.service.models.OrganizationTypeDTO;

public interface OrganizationTypeService {

    OrganizationTypeDTO createOrganizationType(OrganizationTypeDTO organizationTypeDTO);

    OrganizationTypeDTO updateOrganizationType(OrganizationTypeDTO organizationTypeDTO);

    void deleteOrganizationType(String id);

    OrganizationTypeDTO readOrganizationType(String id);

    OrganizationTypeDTO readOrganisationTypeByCode(String code);

    Page<OrganizationTypeDTO> readAllOrganizationTypes(Pageable pageable, String code, String label);


}
