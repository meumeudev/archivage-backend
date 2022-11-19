package sn.webg.archivage.service.services.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sn.webg.archivage.service.exceptions.ResourceAlreadyExistException;
import sn.webg.archivage.service.exceptions.ResourceNotFoundException;
import sn.webg.archivage.service.mappers.RoleMapper;
import sn.webg.archivage.service.models.RoleDTO;
import sn.webg.archivage.service.models.SecurityPermissions;
import sn.webg.archivage.service.repositories.RoleRepository;
import sn.webg.archivage.service.services.RoleService;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class RoleServiceImpl implements RoleService {

    static final String ROLE_NAME_NOT_FOUND_MESSAGE = "[Role] Not found Role {0}";

    final RoleRepository roleRepository;

    final RoleMapper roleMapper;

    @Override
    public RoleDTO createRole(RoleDTO roleDTO) {

        /* Check existing role by name */
        if (roleRepository.existsById(roleDTO.getName())) {
            throw new ResourceAlreadyExistException(MessageFormat.format("Duplicate role by {0}", roleDTO.getName()));
        }

        var createdRole = roleRepository.save(roleMapper.asEntity(roleDTO));

        log.info("createRole end ok - roleId: {}", createdRole.getName());
        log.trace("createRole end ok - role: {}", createdRole);

        return roleMapper.asDto(createdRole);
    }

    @Override
    public RoleDTO updateRole(RoleDTO roleDTO) {

        if (!roleRepository.existsById(roleDTO.getName())) {
            throw  new ResourceNotFoundException(MessageFormat.format(ROLE_NAME_NOT_FOUND_MESSAGE, roleDTO.getName()));
        }

        var updatedRole = roleRepository.save(roleMapper.asEntity(roleDTO));

        log.info("updatedRole end ok - roleId: {}", updatedRole.getName());
        log.trace("updatedRole end ok - role: {}", updatedRole);

        return roleMapper.asDto(updatedRole);
    }

    @Override
    public void deleteRole(String name) {

        if (!roleRepository.existsById(name)) {
            throw new ResourceNotFoundException(MessageFormat.format(ROLE_NAME_NOT_FOUND_MESSAGE, name));
        }

        roleRepository.deleteById(name);

        log.info("deletedRole end ok - roleId: {}", name);
    }

    @Override
    public RoleDTO readRole(String name) {

        var role = roleRepository.findById(name)
                .map(roleMapper::asDto)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(ROLE_NAME_NOT_FOUND_MESSAGE, name)));

        log.info("readRole end ok - roleId: {}", name);
        log.trace("readRole end ok - role: {}", role);

        return role;
    }

    @Override
    public Page<RoleDTO> readByFilters(Pageable pageable, String name, List<SecurityPermissions> securityPermissions) {

        var roles = roleRepository.readAllByFilters(
                pageable,
                name,
                securityPermissions
        ).map(roleMapper::asDto);

        log.trace("list roles get ok {}", roles);

        return roles;
    }
}
