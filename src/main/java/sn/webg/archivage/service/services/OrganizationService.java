package sn.webg.archivage.service.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.webg.archivage.service.models.OrganizationDTO;

public interface OrganizationService {

    OrganizationDTO createOrganization(OrganizationDTO organizationDTO);

    OrganizationDTO updateOrganization(OrganizationDTO organizationDTO);

    void deleteOrganization(String id);

    OrganizationDTO readOrganization(String id);


    Page<OrganizationDTO> readAllOrganizations(Pageable pageable, String name, String phone, String responsible, String organizationTypeId);


}

