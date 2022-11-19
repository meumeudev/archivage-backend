package sn.webg.archivage.service.services.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sn.webg.archivage.service.models.Module;
import sn.webg.archivage.service.models.SecurityPermissions;
import sn.webg.archivage.service.services.SecurityService;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class SecurityServiceImpl implements SecurityService {

    @Override
    public List<Module> readAllModules() {
        return Arrays.stream(Module.values()).collect(Collectors.toList());
    }

    @Override
    public Set<SecurityPermissions> readAllPermissionsByModules(Module module) {
        return SecurityPermissions.readSecurityPermissionsByModule(module);
    }

}
