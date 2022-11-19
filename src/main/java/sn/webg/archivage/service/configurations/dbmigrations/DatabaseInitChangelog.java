package sn.webg.archivage.service.configurations.dbmigrations;


import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import sn.webg.archivage.service.entities.AgentEntity;
import sn.webg.archivage.service.entities.RoleEntity;
import sn.webg.archivage.service.entities.UserEntity;
import sn.webg.archivage.service.models.SecurityPermissions;
import sn.webg.archivage.service.repositories.RoleRepository;
import sn.webg.archivage.service.repositories.UserRepository;
import sn.webg.archivage.service.security.SecurityCompositeRole;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@ChangeLog(order = "001")
public class DatabaseInitChangelog {

    @ChangeSet(order = "001", id = "init_profiles", author = "csall")
    public void initProfiles(RoleRepository roleRepository) {

        roleRepository.save(RoleEntity
                .builder()
                .name(SecurityCompositeRole.ADMIN)
                .permissions(Set.of())
                .build());


    }

    @ChangeSet(order = "002", id = "init_users", author = "csall")
    public void initUsers(UserRepository userRepository) {

        userRepository.save(UserEntity
                .builder()
                .username("admin")
                .password("$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC")
                .role(RoleEntity
                        .builder()
                        .name(SecurityCompositeRole.ADMIN)
                        .permissions(Arrays.stream(SecurityPermissions.values()).collect(Collectors.toSet()))
                        .build())
                .agent(AgentEntity
                        .builder()
                        .email("admin@admin.com")
                        .firstName("Administrateur")
                        .lastName("Administrateur")
                        .activated(true)
                        .build())
                .build());


    }
}
