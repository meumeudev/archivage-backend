package sn.webg.archivage.service.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.webg.archivage.service.models.RoleDTO;
import sn.webg.archivage.service.models.SecurityPermissions;

import java.util.List;

public interface RoleService {

    RoleDTO createRole(RoleDTO roleDTO);

    RoleDTO updateRole(RoleDTO roleDTO);

    void deleteRole(String name);

    RoleDTO readRole(String name);

    public Page<RoleDTO> readByFilters(Pageable pageable, String name, List<SecurityPermissions> securityPermissions);
}
