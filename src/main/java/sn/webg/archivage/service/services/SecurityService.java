package sn.webg.archivage.service.services;

import sn.webg.archivage.service.models.Module;
import sn.webg.archivage.service.models.SecurityPermissions;

import java.util.List;
import java.util.Set;

public interface SecurityService {

    List<Module> readAllModules();

    Set<SecurityPermissions> readAllPermissionsByModules(Module module);

}
